package com.ironman.pharmasales.clients.domain.port;

import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.clients.domain.model.client.ClientFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface ClientPort {

    List<ClientDomain> findAll();

    List<ClientDomain> findByState(String state);

    Optional<ClientDomain> findById(Long id);

    ClientDomain save(ClientDomain clientDomain);

    PageResponse<ClientDomain> findAll(ClientFilterDomain filter);

    List<ClientDomain> search(String searchText);
}
