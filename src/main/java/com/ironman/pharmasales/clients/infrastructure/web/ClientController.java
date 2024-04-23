package com.ironman.pharmasales.clients.infrastructure.web;

import com.ironman.pharmasales.clients.application.dto.client.*;
import com.ironman.pharmasales.clients.application.service.ClientService;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.exception.model.ArgumentNotValidError;
import com.ironman.pharmasales.shared.domain.exception.model.GeneralError;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.web.constant.StatusCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService clientService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    public ResponseEntity<List<ClientSmallDto>> findAll() {
        List<ClientSmallDto> clients = clientService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(clients);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable("id") Long id)
            throws DataNotFoundException {
        ClientDto client = clientService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(client);
    }

    @ApiResponse(responseCode = StatusCode.CREATED)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientSaveDto clientBody)
            throws DataNotFoundException {
        ClientDto client = clientService.create(clientBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(client);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> edit(@PathVariable("id") Long id, @Valid @RequestBody ClientSaveDto clientBody)
            throws DataNotFoundException {
        ClientDto client = clientService.edit(id, clientBody);

        return ResponseEntity.status(HttpStatus.OK)
                .body(client);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        ClientDto client = clientService.disabled(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(client);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    public ResponseEntity<PageResponse<ClientDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "lastName", required = false) String lastName,

            @Pattern(regexp = "^\\d*$", message = "El documentTypeId debe contener solo dígitos")
            @RequestParam(name = "documentTypeId", required = false) Long documentTypeId,

            @RequestParam(name = "documentNumber", required = false) String documentNumber,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo createdAtFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,

            @Parameter(description = "El campo createdAtTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,

            @Parameter(description = "El campo sort debe ser 'id', 'name', lastName, documentTypeId o 'createdAt'")
            @Pattern(regexp = "^(id|name|lastName|documentTypeId|createdAt)$", message = "La dirección debe ser id, 'name', lastName, documentTypeId o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        ClientFilterDto filter = ClientFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .lastName(lastName)
                .documentTypeId(documentTypeId)
                .documentNumber(documentNumber)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        PageResponse<ClientDto> clients = clientService.findAll(filter);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clients);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/search/{searchText}")
    public ResponseEntity<List<ClientMediumDto>> search(@PathVariable("searchText") String searchText) {
        List<ClientMediumDto> clients = clientService.search(searchText);

        return ResponseEntity.status(HttpStatus.OK)
                .body(clients);
    }
}
