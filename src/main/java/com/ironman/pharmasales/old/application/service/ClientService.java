package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.client.ClientDto;
import com.ironman.pharmasales.old.application.dto.client.ClientFilterDto;
import com.ironman.pharmasales.old.application.dto.client.ClientMediumDto;
import com.ironman.pharmasales.old.application.dto.client.ClientSaveDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDto> findAll();

    ClientDto findById(Long id) throws DataNotFoundException;

    ClientDto create(ClientSaveDto clientBody) throws DataNotFoundException;

    ClientDto edit(Long id, ClientSaveDto clientBody) throws DataNotFoundException;

    ClientDto disabled(Long id) throws DataNotFoundException;

    Page<ClientDto> paginationFilter(Pageable pageable, Optional<ClientFilterDto> filter);
    List<ClientMediumDto> search(String searchText);
}
