package com.ironman.pharmasales.clients.application.service;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeFilterDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSaveDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeDto> findAll();

    DocumentTypeDto findById(Long id) throws DataNotFoundException;

    DocumentTypeDto create(DocumentTypeSaveDto documentTypeSaveDto);

    DocumentTypeDto edit(Long id, DocumentTypeSaveDto documentTypeSaveDto) throws DataNotFoundException;

    DocumentTypeDto disabled(Long id) throws DataNotFoundException;

    List<DocumentTypeSmallDto> select();

    PageResponse<DocumentTypeDto> findAll(DocumentTypeFilterDto filter);
}
