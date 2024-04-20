package com.ironman.pharmasales.products.infrastructure.persistence.repository;

import com.ironman.pharmasales.products.infrastructure.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByState(String state);

    @Query(value = "SELECT " +
            " p.id ,p.name ,p.description ,p.presentation ,p.unit_measure " +
            " ,p.prescription ,p.precaution ,p.side_effect ,p.price ,p.stock " +
            " ,p.subcategory_id ,p.state ,p.created_at ,p.updated_at " +
            " ,s.name as subcategory_name ,s.category_id " +
            " ,c.name as category_name " +
            " FROM products p " +
            " INNER JOIN subcategories s ON s.id = p.subcategory_id " +
            " INNER JOIN categories c ON c.id = s.category_id " +
            " WHERE ( :name IS NULL OR UPPER(p.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND ( :description IS NULL OR UPPER(p.description) LIKE UPPER(CONCAT('%',:description,'%')) )" +
            " AND ( :presentation IS NULL OR UPPER(p.presentation) LIKE UPPER(CONCAT('%',:presentation,'%')) )" +
            " AND ( :stock IS NULL OR p.stock <= :stock )" +
            " AND ( :subcategoryId IS NULL OR p.subcategory_id = :subcategoryId )" +
            " AND ( :state IS NULL OR UPPER(p.state) = UPPER(:state) )" +
            " AND ( :createdAtFrom IS NULL OR DATE(p.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND ( :createdAtTo IS NULL OR DATE(p.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true
    )
    Page<Product> findAllPaginated(
            @Param("name") String name,
            @Param("description") String description,
            @Param("presentation") String presentation,
            @Param("stock") Long stock,
            @Param("subcategoryId") Long subcategoryId,
            @Param("state") String state,
            @Param("createdAtFrom") String createdAtFrom,
            @Param("createdAtTo") String createdAtTo,
            Pageable pageable
    );

    @Query(value = "SELECT p FROM Product AS p" +
            " WHERE CONCAT(UPPER(p.name), ' ', UPPER(p.presentation)) LIKE UPPER(CONCAT('%',:searchText,'%'))" +
            " OR p.subcategoryId IN ( SELECT s.id FROM Subcategory AS s WHERE UPPER(s.name) LIKE UPPER(CONCAT('%',:searchText,'%')) )" +
            " AND p.state = 'A'"
    )
    List<Product> search(@Param("searchText") String searchText);
}
