package com.ironman.pharmasales.clients.application.service;

import com.ironman.pharmasales.clients.application.dto.client.*;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();

    ClientDto findById(Long id) throws DataNotFoundException;

    ClientDto create(ClientSaveDto clientBody) throws DataNotFoundException;

    ClientDto edit(Long id, ClientSaveDto clientBody) throws DataNotFoundException;

    ClientDto disabled(Long id) throws DataNotFoundException;

    List<ClientSmallDto> select();

    PageResponse<ClientDto> findAll(ClientFilterDto filter);

    List<ClientMediumDto> search(String searchText);
}
