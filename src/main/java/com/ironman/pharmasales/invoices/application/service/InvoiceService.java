package com.ironman.pharmasales.invoices.application.service;

import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceFilterDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceSaveDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface InvoiceService {

    List<InvoiceDto> findAll();

    InvoiceDto findById(Long id) throws DataNotFoundException;

    InvoiceDto create(InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException;

    InvoiceDto edit(Long id, InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException;

    InvoiceDto disabled(Long id) throws DataNotFoundException;

    PageResponse<InvoiceDto> findAll(InvoiceFilterDto filter);
}
