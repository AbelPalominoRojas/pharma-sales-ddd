package com.ironman.pharmasales.clients.infrastructure.persistence;

import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.clients.domain.model.client.ClientFilterDomain;
import com.ironman.pharmasales.clients.domain.port.ClientPort;
import com.ironman.pharmasales.clients.infrastructure.persistence.entity.Client;
import com.ironman.pharmasales.clients.infrastructure.persistence.enums.ClientSortField;
import com.ironman.pharmasales.clients.infrastructure.persistence.mapper.ClientEntityMapper;
import com.ironman.pharmasales.clients.infrastructure.persistence.repository.ClientRepository;
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
public class ClientPortImpl extends PageProcessor<ClientDomain> implements ClientPort {
    private final ClientRepository clientRepository;
    private final ClientEntityMapper clientMapper;


    @Override
    public List<ClientDomain> findAll() {
        return ((List<Client>) clientRepository.findAll())
                .stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    @Override
    public List<ClientDomain> findByState(String state) {
        return clientRepository.findByState(state)
                .stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ClientDomain> findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDomain);
    }

    @Override
    public ClientDomain save(ClientDomain clientDomain) {
        var savedClient = clientRepository.save(
                clientMapper.toEntity(clientDomain)
        );

        return clientMapper.toDomain(savedClient);
    }

    @Override
    public PageResponse<ClientDomain> findAll(ClientFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        var clientPage = clientRepository.findAllPaginated(
                filter.getName(),
                filter.getLastName(),
                filter.getDocumentTypeId(),
                filter.getDocumentNumber(),
                filter.getState(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                pageable
        );

        var clients = clientPage
                .stream()
                .map(clientMapper::toDomain)
                .toList();

        return getPageBuild(clientPage, clients);
    }

    @Override
    public List<ClientDomain> search(String searchText) {
        return clientRepository.search(searchText)
                .stream()
                .map(clientMapper::toDomain)
                .toList();
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = ClientSortField.getSqlName(sortField);

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }
}
