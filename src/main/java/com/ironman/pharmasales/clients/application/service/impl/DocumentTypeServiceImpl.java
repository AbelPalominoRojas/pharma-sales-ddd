package com.ironman.pharmasales.clients.application.service.impl;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeFilterDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSaveDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.clients.application.mapper.DocumentTypeMapper;
import com.ironman.pharmasales.clients.application.service.DocumentTypeService;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeFilterDomain;
import com.ironman.pharmasales.clients.domain.port.DocumentTypePort;
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
public class DocumentTypeServiceImpl extends PageBuild<DocumentTypeDto> implements DocumentTypeService {
    private final DocumentTypePort documentTypePort;
    private final DocumentTypeMapper documentTypeMapper;

    @Override
    public List<DocumentTypeSmallDto> findAll() {
        return documentTypePort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(documentTypeMapper::toSmallDto)
                .toList();
    }

    @Override
    public DocumentTypeDto findById(Long id) throws DataNotFoundException {
        return documentTypePort.findById(id)
                .map(documentTypeMapper::toDto)
                .orElseThrow(() -> documentTypeNotFoundException(id));
    }

    @Override
    public DocumentTypeDto create(DocumentTypeSaveDto documentTypeSaveDto) {
        DocumentTypeDomain documentTypeSave = documentTypeMapper.toDomain(documentTypeSaveDto);
        documentTypeSave.setState(State.ACTIVE.getValue());
        documentTypeSave.setCreatedAt(LocalDateTime.now());

        return documentTypeMapper.toDto(documentTypePort.save(documentTypeSave));
    }

    @Override
    public DocumentTypeDto edit(Long id, DocumentTypeSaveDto documentTypeSaveDto) throws DataNotFoundException {
        DocumentTypeDomain documentTypeDb = documentTypePort.findById(id)
                .orElseThrow(() -> documentTypeNotFoundException(id));

        documentTypeMapper.updateDomain(documentTypeDb, documentTypeSaveDto);
        documentTypeDb.setUpdatedAt(LocalDateTime.now());

        return documentTypeMapper.toDto(documentTypePort.save(documentTypeDb));
    }

    @Override
    public DocumentTypeDto disabled(Long id) throws DataNotFoundException {
        DocumentTypeDomain documentTypeDb = documentTypePort.findById(id)
                .orElseThrow(() -> documentTypeNotFoundException(id));

        documentTypeDb.setState(State.DISABLE.getValue());

        return documentTypeMapper.toDto(documentTypePort.save(documentTypeDb));
    }

    @Override
    public PageResponse<DocumentTypeDto> findAll(DocumentTypeFilterDto filter) {
        DocumentTypeFilterDomain filterDomain = documentTypeMapper.toFilter(filter);

        var documentTypePage = documentTypePort.findAll(filterDomain);

        var documentTypes = documentTypePage.getContent()
                .stream()
                .map(documentTypeMapper::toDto)
                .toList();

        return getPage(documentTypePage, documentTypes);
    }


    private static DataNotFoundException documentTypeNotFoundException(Long id) {
        return new DataNotFoundException("Tipo de documento no encontrado para el id: " + id);
    }
}
