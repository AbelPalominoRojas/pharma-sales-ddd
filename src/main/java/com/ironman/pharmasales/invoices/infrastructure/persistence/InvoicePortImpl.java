package com.ironman.pharmasales.invoices.infrastructure.persistence;

import com.ironman.pharmasales.invoices.domain.model.InvoiceDomain;
import com.ironman.pharmasales.invoices.domain.model.InvoiceFilterDomain;
import com.ironman.pharmasales.invoices.domain.port.InvoicePort;
import com.ironman.pharmasales.invoices.infrastructure.persistence.entity.Invoice;
import com.ironman.pharmasales.invoices.infrastructure.persistence.enums.InvoiceSortField;
import com.ironman.pharmasales.invoices.infrastructure.persistence.mapper.InvoiceEntityMapper;
import com.ironman.pharmasales.invoices.infrastructure.persistence.repository.InvoiceDetailRepository;
import com.ironman.pharmasales.invoices.infrastructure.persistence.repository.InvoiceRepository;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class InvoicePortImpl extends PageProcessor<InvoiceDomain> implements InvoicePort {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceEntityMapper invoiceMapper;


    @Override
    public List<InvoiceDomain> findAll() {
        return ((List<Invoice>) invoiceRepository.findAll())
                .stream()
                .map(invoiceMapper::toDomain)
                .toList();
    }

    @Override
    public List<InvoiceDomain> findByState(String state) {
        return invoiceRepository.findByState(state)
                .stream()
                .map(invoiceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<InvoiceDomain> findById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDomain);
    }

    @Transactional
    @Override
    public InvoiceDomain save(InvoiceDomain domain) {
        Invoice invoiceSave = invoiceMapper.toEntity(domain);

        invoiceSave.getInvoiceDetails().forEach(detail -> {
            detail.setInvoice(invoiceSave);
        });

        if (invoiceSave.getId() != null) {
            invoiceDetailRepository.saveAll(invoiceSave.getInvoiceDetails());
        }

        return invoiceMapper.toDomain(invoiceRepository.save(invoiceSave));
    }

    @Override
    public PageResponse<InvoiceDomain> findAll(InvoiceFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        Page<Invoice> invoicePage = invoiceRepository.findAllPaginated(
                filter.getInvoiceDateFrom(),
                filter.getInvoiceDateTo(),
                filter.getClientId(),
                filter.getUserId(),
                filter.getState(),
                pageable
        );

        var invoices = invoicePage.getContent()
                .stream()
                .map(invoiceMapper::toDomain)
                .toList();

        return getPageBuild(invoicePage, invoices);
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = InvoiceSortField.getSqlName(sortField);

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }
}
