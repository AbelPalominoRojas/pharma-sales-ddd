package com.ironman.pharmasales.old.persistence.repository;

import com.ironman.pharmasales.old.persistence.entity.InvoiceDetail;
import com.ironman.pharmasales.old.persistence.entity.InvoiceDetailPk;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceDetailRepository extends CrudRepository<InvoiceDetail, InvoiceDetailPk> {
}
