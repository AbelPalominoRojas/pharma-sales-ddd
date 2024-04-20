package com.ironman.pharmasales.products.infrastructure.persistence.repository;

import com.ironman.pharmasales.products.infrastructure.persistence.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {

    List<Subcategory> findByState(String state);

    @Query(value = "SELECT " +
            " s.id, s.name, s.description, s.keyword, s.category_id, s.state, s.created_at, s.updated_at, " +
            " c.name as category_name" +
            " FROM subcategories s" +
            " INNER JOIN categories c ON s.category_id = c.id " +
            " WHERE (:name IS NULL OR UPPER(s.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND (:description IS NULL OR UPPER(s.description) LIKE UPPER(CONCAT('%',:description,'%')) )" +
            " AND (:categoryId IS NULL OR s.category_id = :categoryId)" +
            " AND (:state IS NULL OR UPPER(s.state) = UPPER(:state) )" +
            " AND (:createdAtFrom IS NULL OR DATE(s.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND (:createdAtTo IS NULL OR DATE(s.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true
    )
    Page<Subcategory> findAllPaginated(
            @Param("name") String name,
            @Param("description") String description,
            @Param("categoryId") Long categoryId,
            @Param("state") String state,
            @Param("createdAtFrom") String createdAtFrom,
            @Param("createdAtTo") String createdAtTo,
            Pageable pageable
    );
}
