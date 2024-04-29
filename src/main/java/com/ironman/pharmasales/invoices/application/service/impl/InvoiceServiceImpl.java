package com.ironman.pharmasales.invoices.application.service.impl;

import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceFilterDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceSaveDto;
import com.ironman.pharmasales.invoices.application.mapper.InvoiceMapper;
import com.ironman.pharmasales.invoices.application.service.InvoiceService;
import com.ironman.pharmasales.invoices.domain.model.InvoiceDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceFilterDomain;
import com.ironman.pharmasales.invoices.domain.port.InvoicePort;
import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl extends PageBuild<InvoiceDto> implements InvoiceService {
    private final InvoicePort invoicePort;
    private final InvoiceMapper invoiceMapper;

    @Override
    public List<InvoiceDto> findAll() {
        return invoicePort.findAll()
                .stream()
                .map(invoiceMapper::toDto)
                .toList();
    }

    @Override
    public InvoiceDto findById(Long id) throws DataNotFoundException {
        InvoiceDomain invoice = invoicePort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Venta no encontrado para el id: " + id));

        return invoiceMapper.toDto(invoice);
    }

    @Override
    public InvoiceDto create(InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException {

        InvoiceDomain invoiceSave = invoiceMapper.toDomain(invoiceSaveDto);

        invoiceSave.setState(State.ACTIVE.getValue());
        invoiceSave.setCreatedAt(LocalDateTime.now());

        invoiceSave.getInvoiceDetails().forEach(detail -> {
            detail.setInvoice(invoiceSave);
            detail.setState(State.ACTIVE.getValue());
            detail.setCreatedAt(LocalDateTime.now());
        });

        return invoiceMapper.toDto(invoicePort.save(invoiceSave));
    }


    @Override
    public InvoiceDto edit(Long id, InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException {
        InvoiceDomain invoiceDb = invoicePort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Venta no encontrado para el id: " + id));

        invoiceMapper.updateDomain(invoiceDb, invoiceSaveDto);

        invoiceDb.setUpdatedAt(LocalDateTime.now());

        invoiceDb.getInvoiceDetails().forEach(detail -> {
            detail.setInvoice(invoiceDb);
            detail.setState(State.ACTIVE.getValue());
            detail.setCreatedAt(LocalDateTime.now());
            detail.setUpdatedAt(LocalDateTime.now());
        });

        return invoiceMapper.toDto(invoicePort.save(invoiceDb));
    }

    @Override
    public InvoiceDto disabled(Long id) throws DataNotFoundException {
        InvoiceDomain invoiceDb = invoicePort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Venta no encontrado para el id: " + id));

        invoiceDb.setState(State.DISABLE.getValue());

        invoiceDb.getInvoiceDetails().forEach(detail -> {
            detail.setInvoice(invoiceDb);
            detail.setState(State.DISABLE.getValue());
        });

        return invoiceMapper.toDto(invoicePort.save(invoiceDb));
    }

    @Override
    public PageResponse<InvoiceDto> findAll(InvoiceFilterDto filter) {
        InvoiceFilterDomain filterDomain = invoiceMapper.toFilter(filter);

        var invoicePage = invoicePort.findAll(filterDomain);

        var invoices = invoicePage.getContent()
                .stream()
                .map(invoiceMapper::toDto)
                .toList();

        return getPage(invoicePage, invoices);
    }
}
