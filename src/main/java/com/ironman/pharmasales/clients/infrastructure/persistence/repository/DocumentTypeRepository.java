package com.ironman.pharmasales.clients.infrastructure.persistence.repository;

import com.ironman.pharmasales.clients.infrastructure.persistence.entity.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {
    List<DocumentType> findByState(String state);

    @Query(value = "SELECT d FROM DocumentType AS d" +
            " WHERE ( :#{#documentType.name} IS NULL OR UPPER(d.name) LIKE UPPER(CONCAT('%',:#{#documentType.name},'%')) )" +
            " AND ( :#{#documentType.description} IS NULL OR UPPER(d.description) LIKE UPPER(CONCAT('%',:#{#documentType.description},'%')) )" +
            " AND ( :#{#documentType.sunatCode} IS NULL OR UPPER(d.sunatCode) LIKE UPPER(CONCAT('%',:#{#documentType.sunatCode},'%')) )" +
            " AND ( :#{#documentType.size} IS NULL OR d.size = :#{#documentType.size} )" +
            " AND ( :#{#documentType.isSizeExact} IS NULL OR d.isSizeExact = :#{#documentType.isSizeExact} )" +
            " AND ( :#{#documentType.isNumeric} IS NULL OR d.isNumeric = :#{#documentType.isNumeric} )" +
            " AND ( :#{#documentType.state} IS NULL OR UPPER(d.state) = UPPER(:#{#documentType.state}) )"
    )
    Page<DocumentType> paginationFilter(Pageable pageable, @Param("documentType") DocumentType documentType);

    @Query(value = "SELECT " +
            " d.id, d.name, d.description, d.sunat_code, d.size, d.is_size_exact, d.is_numeric, d.state, d.created_at, d.updated_at" +
            " FROM document_types d" +
            " WHERE (:name IS NULL OR UPPER(d.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND (:description IS NULL OR UPPER(d.description) LIKE UPPER(CONCAT('%',:description,'%')) )" +
            " AND (:sunatCode IS NULL OR UPPER(d.sunat_code) LIKE UPPER(CONCAT('%',:sunatCode,'%')) )" +
            " AND (:size IS NULL OR d.size = :size)" +
            " AND (:isSizeExact IS NULL OR d.is_size_exact = :isSizeExact)" +
            " AND (:isNumeric IS NULL OR d.is_numeric = :isNumeric)" +
            " AND (:state IS NULL OR UPPER(d.state) = UPPER(:state) )" +
            " AND (:createdAtFrom IS NULL OR DATE(d.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND (:createdAtTo IS NULL OR DATE(d.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true)
    Page<DocumentType> findAllPaginated(
            @Param("name") String name,
            @Param("description") String description,
            @Param("sunatCode") String sunatCode,
            @Param("size") Integer size,
            @Param("isSizeExact") Integer isSizeExact,
            @Param("isNumeric") Integer isNumeric,
            @Param("state") String state,
            @Param("createdAtFrom") String createdAtFrom,
            @Param("createdAtTo") String createdAtTo,
            Pageable pageable
    );
}
