package com.ironman.pharmasales.products.infrastructure.persistence.repository;

import com.ironman.pharmasales.products.infrastructure.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {

    List<Category> findByState(String state);

    @Query(value = "SELECT " +
            " c.id, c.name, c.description, c.keyword, c.state, c.created_at, c.updated_at" +
            " FROM categories c" +
            " WHERE (:name IS NULL OR UPPER(c.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND (:description IS NULL OR UPPER(c.description) LIKE UPPER(CONCAT('%',:description,'%')) )" +
            " AND (:state IS NULL OR UPPER(c.state) = UPPER(:state) )" +
            " AND (:createdAtFrom IS NULL OR DATE(c.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND (:createdAtTo IS NULL OR DATE(c.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true
    )
    Page<Category> findAllPaginated(
            @Param("name") String name,
            @Param("description") String description,
            @Param("state") String state,
            @Param("createdAtFrom") String createdAtFrom,
            @Param("createdAtTo") String createdAtTo,
            Pageable pageable
    );
}
