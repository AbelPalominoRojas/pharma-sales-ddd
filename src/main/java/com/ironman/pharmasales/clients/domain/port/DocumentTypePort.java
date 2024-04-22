package com.ironman.pharmasales.clients.domain.port;

import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface DocumentTypePort {

    List<DocumentTypeDomain> findAll();

    List<DocumentTypeDomain> findByState(String state);

    Optional<DocumentTypeDomain> findById(Long id);

    DocumentTypeDomain save(DocumentTypeDomain documentTypeDomain);

    PageResponse<DocumentTypeDomain> findAll(DocumentTypeFilterDomain filter);

}
