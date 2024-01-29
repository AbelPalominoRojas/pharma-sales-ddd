package com.ironman.pharmasales.products.infrastructure.persistence.repository;

import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {

    List<Subcategory> findByState(String state);


    @Query(value = "SELECT s FROM Subcategory AS s" +
            " WHERE (:#{#subcategory.name} IS NULL OR UPPER(s.name) LIKE UPPER(CONCAT('%',:#{#subcategory.name},'%')) )" +
            " AND (:#{#subcategory.description} IS NULL OR UPPER(s.description) LIKE UPPER(CONCAT('%',:#{#subcategory.description},'%')) )" +
            " AND (:#{#subcategory.categoryId} IS NULL OR s.categoryId = :#{#subcategory.categoryId})" +
            " AND (:#{#subcategory.state} IS NULL OR UPPER(s.state) = UPPER(:#{#subcategory.state}) )"
    )
    Page<Subcategory> paginationFilter(Pageable pageable, @Param("subcategory") SubcategoryFilterDomain subcategory);
}
