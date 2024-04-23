package com.ironman.pharmasales.clients.application.service;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeFilterDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSaveDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeSmallDto> findAll();

    DocumentTypeDto findById(Long id) throws DataNotFoundException;

    DocumentTypeDto create(DocumentTypeSaveDto documentTypeBody);

    DocumentTypeDto edit(Long id, DocumentTypeSaveDto documentTypeBody) throws DataNotFoundException;

    DocumentTypeDto disabled(Long id) throws DataNotFoundException;

    PageResponse<DocumentTypeDto> findAll(DocumentTypeFilterDto filter);
}
