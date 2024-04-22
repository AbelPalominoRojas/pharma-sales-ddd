package com.ironman.pharmasales.clients.application.service.impl;

import com.ironman.pharmasales.clients.application.dto.client.*;
import com.ironman.pharmasales.clients.application.mapper.ClientMapper;
import com.ironman.pharmasales.clients.application.service.ClientService;
import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.clients.domain.model.client.ClientFilterDomain;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.domain.port.ClientPort;
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
public class ClientServiceImpl extends PageBuild<ClientDto> implements ClientService {

    private final ClientPort clientPort;
    private final DocumentTypePort documentTypePort;
    private final ClientMapper clientMapper;

    @Override
    public List<ClientDto> findAll() {
        return clientPort.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Override
    public ClientDto findById(Long id) throws DataNotFoundException {
        ClientDomain client = clientPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cliente no se encontro para el id: " + id));

        return clientMapper.toDto(client);
    }

    @Override
    public ClientDto create(ClientSaveDto clientBody) throws DataNotFoundException {
        DocumentTypeDomain documentType = documentTypePort.findById(clientBody.getDocumentTypeId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Tipo de documento no encontrado para el id: " + clientBody.getDocumentTypeId())
                );

        ClientDomain clientSave = clientMapper.toDomain(clientBody);
        clientSave.setDocumentType(documentType);
        clientSave.setState(State.ACTIVE.getValue());
        clientSave.setCreatedAt(LocalDateTime.now());

        return clientMapper.toDto(clientPort.save(clientSave));
    }

    @Override
    public ClientDto edit(Long id, ClientSaveDto clientBody) throws DataNotFoundException {
        DocumentTypeDomain documentType = documentTypePort.findById(clientBody.getDocumentTypeId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Tipo de documento no encontrado para el id: " + clientBody.getDocumentTypeId())
                );

        ClientDomain clientDb = clientPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cliente no se encontro para el id: " + id));

        clientMapper.updateDomain(clientDb, clientBody);
        clientDb.setDocumentType(documentType);
        clientDb.setUpdatedAt(LocalDateTime.now());

        return clientMapper.toDto(clientPort.save(clientDb));
    }

    @Override
    public ClientDto disabled(Long id) throws DataNotFoundException {
        ClientDomain clientDb = clientPort.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cliente no se encontro para el id: " + id));

        clientDb.setState(State.DISABLE.getValue());

        return clientMapper.toDto(clientPort.save(clientDb));
    }

    @Override
    public List<ClientSmallDto> select() {
        return clientPort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(clientMapper::toSmallDto)
                .toList();
    }

    @Override
    public PageResponse<ClientDto> findAll(ClientFilterDto filter) {
        ClientFilterDomain filterDomain = clientMapper.toFilter(filter);

        var clientPage = clientPort.findAll(filterDomain);

        var clients = clientPage.getContent()
                .stream()
                .map(clientMapper::toDto)
                .toList();

        return getPage(clientPage, clients);
    }

    @Override
    public List<ClientMediumDto> search(String searchText) {
        return clientPort.search(searchText)
                .stream()
                .map(clientMapper::toMediumDto)
                .toList();
    }
}
