package com.ironman.pharmasales.clients.infrastructure.web;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeFilterDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSaveDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.clients.application.service.DocumentTypeService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("document-type")
public class DocumentTypeController {
    private final DocumentTypeService documentTypeService;


    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    public ResponseEntity<List<DocumentTypeDto>> findAll() {
        List<DocumentTypeDto> documentTypes = documentTypeService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentTypes);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        DocumentTypeDto documentType = documentTypeService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentType);
    }

    @ApiResponse(responseCode = StatusCode.CREATED)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<DocumentTypeDto> create(@Valid @RequestBody DocumentTypeSaveDto documentTypeSaveDto) {
        DocumentTypeDto documentType = documentTypeService.create(documentTypeSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentType);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> edit(@PathVariable("id") Long id, @Valid @RequestBody DocumentTypeSaveDto documentTypeSaveDto)
            throws DataNotFoundException {
        DocumentTypeDto documentType = documentTypeService.edit(id, documentTypeSaveDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentType);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<DocumentTypeDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        DocumentTypeDto documentType = documentTypeService.disabled(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentType);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/select")
    public ResponseEntity<List<DocumentTypeSmallDto>> select() {
        List<DocumentTypeSmallDto> documentTypes = documentTypeService.select();

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentTypes);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    ResponseEntity<PageResponse<DocumentTypeDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "sunatCode", required = false) String sunatCode,
            @RequestParam(name = "sizeDocument", required = false) Integer sizeDocument,
            @RequestParam(name = "isSizeExact", required = false) Boolean isSizeExact,
            @RequestParam(name = "isNumeric", required = false) Boolean isNumeric,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo createdAtFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,

            @Parameter(description = "El campo createdAtTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,

            @Parameter(description = "El campo sort debe ser 'id', 'name' o 'createdAt'")
            @Pattern(regexp = "^(id|name|createdAt)$", message = "La dirección debe ser 'name' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        var filter = DocumentTypeFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .description(description)
                .sunatCode(sunatCode)
                .sizeDocument(sizeDocument)
                .isSizeExact(isSizeExact)
                .isNumeric(isNumeric)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        PageResponse<DocumentTypeDto> documentTypePage = documentTypeService.findAll(filter);

        return ResponseEntity.status(HttpStatus.OK)
                .body(documentTypePage);
    }
}
