package com.ironman.pharmasales.invoices.infrastructure.persistence.repository;

import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.InvoiceDetail;
import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.InvoiceDetailPk;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceDetailRepository extends CrudRepository<InvoiceDetail, InvoiceDetailPk> {
}
