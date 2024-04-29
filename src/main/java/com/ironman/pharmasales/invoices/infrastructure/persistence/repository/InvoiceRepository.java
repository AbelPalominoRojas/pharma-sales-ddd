package com.ironman.pharmasales.invoices.infrastructure.persistence.repository;

import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findByState(String state);

    @Query(value = "SELECT i FROM Invoice AS i" +
//            " WHERE ( :#{#invoice.invoiceDate} IS NULL OR i.invoiceDate <= :#{#invoice.invoiceDate} )" +
            " WHERE ( :#{#invoice.clientId} IS NULL OR i.clientId = :#{#invoice.clientId} )"
//            " AND ( :#{#invoice.state} IS NULL OR UPPER(i.state) = UPPER(:#{#invoice.state}) )"
    )
    Page<Invoice> paginationFilter(Pageable pageable, @Param("invoice") Invoice invoice);

    @Query(value = "SELECT * FROM invoices AS i" +
            " WHERE ( :invoiceDateFrom IS NULL OR DATE(i.invoice_date) >= TO_DATE(:invoiceDateFrom, 'YYYY-MM-DD') )" +
            " AND ( :invoiceDateTo IS NULL OR DATE(i.invoice_date) <= TO_DATE(:invoiceDateTo, 'YYYY-MM-DD') )" +
            " AND ( :clientId IS NULL OR i.client_id = :clientId )" +
            " AND ( :userId IS NULL OR i.user_id = :userId )" +
            " AND ( :state IS NULL OR UPPER(i.state) = UPPER(:state) )",
            nativeQuery = true
    )
    Page<Invoice> findAllPaginated(
            @Param("invoiceDateFrom") String invoiceDateFrom,
            @Param("invoiceDateTo") String invoiceDateTo,
            @Param("clientId") Long clientId,
            @Param("userId") Long userId,
            @Param("state") String state,
            Pageable pageable
    );
}
