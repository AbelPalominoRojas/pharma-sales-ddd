package com.ironman.pharmasales.invoices.domain.port;

import com.ironman.pharmasales.invoices.domain.model.InvoiceDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface InvoicePort {
    List<InvoiceDomain> findAll();

    List<InvoiceDomain> findByState(String state);

    Optional<InvoiceDomain> findById(Long id);

    InvoiceDomain save(InvoiceDomain domain);

    PageResponse<InvoiceDomain> findAll(InvoiceFilterDomain filter);
}
