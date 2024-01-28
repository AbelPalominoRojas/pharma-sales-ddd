package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.invoice.InvoiceDto;
import com.ironman.pharmasales.old.application.dto.invoice.InvoiceFilterDto;
import com.ironman.pharmasales.old.application.dto.invoice.InvoiceSaveDto;
import com.ironman.pharmasales.old.shared.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    List<InvoiceDto> findAll();

    InvoiceDto findById(Long id) throws DataNotFoundException;

    InvoiceDto create(InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException;

    InvoiceDto edit(Long id, InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException;

    InvoiceDto disabled(Long id) throws DataNotFoundException;

    Page<InvoiceDto> paginationFilter(Pageable pageable, Optional<InvoiceFilterDto> filter);
}
