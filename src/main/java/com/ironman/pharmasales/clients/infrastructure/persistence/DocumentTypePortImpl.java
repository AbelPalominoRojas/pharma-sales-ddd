package com.ironman.pharmasales.clients.infrastructure.persistence;

import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeFilterDomain;
import com.ironman.pharmasales.clients.domain.port.DocumentTypePort;
import com.ironman.pharmasales.clients.infrastructure.persistence.entity.DocumentType;
import com.ironman.pharmasales.clients.infrastructure.persistence.enums.DocumentTypeSortField;
import com.ironman.pharmasales.clients.infrastructure.persistence.mapper.DocumentTypeEntityMapper;
import com.ironman.pharmasales.clients.infrastructure.persistence.repository.DocumentTypeRepository;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DocumentTypePortImpl extends PageProcessor<DocumentTypeDomain> implements DocumentTypePort {
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeEntityMapper documentTypeMapper;

    @Override
    public List<DocumentTypeDomain> findAll() {
        return ((List<DocumentType>) documentTypeRepository.findAll())
                .stream()
                .map(documentTypeMapper::toDomain)
                .toList();
    }

    @Override
    public List<DocumentTypeDomain> findByState(String state) {
        return documentTypeRepository.findByState(state)
                .stream()
                .map(documentTypeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<DocumentTypeDomain> findById(Long id) {
        return documentTypeRepository.findById(id)
                .map(documentTypeMapper::toDomain);
    }

    @Override
    public DocumentTypeDomain save(DocumentTypeDomain domain) {
        var savedDocumentType = documentTypeRepository.save(
                documentTypeMapper.toEntity(domain)
        );

        return documentTypeMapper.toDomain(savedDocumentType);
    }

    @Override
    public PageResponse<DocumentTypeDomain> findAll(DocumentTypeFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        var documentTypePage = documentTypeRepository.findAllPaginated(
                filter.getName(),
                filter.getDescription(),
                filter.getSunatCode(),
                filter.getSizeDocument(),
                filter.getIsSizeExact(),
                filter.getIsNumeric(),
                filter.getState(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                pageable
        );

        var documentTypes = documentTypePage.getContent()
                .stream()
                .map(documentTypeMapper::toDomain)
                .toList();

        return getPageBuild(documentTypePage, documentTypes);
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = DocumentTypeSortField.getSqlName(sortField);

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }
}
