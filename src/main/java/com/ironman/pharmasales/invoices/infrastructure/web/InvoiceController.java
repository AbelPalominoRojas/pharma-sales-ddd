package com.ironman.pharmasales.invoices.infrastructure.web;

import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceFilterDto;
import com.ironman.pharmasales.invoices.application.dto.invoice.InvoiceSaveDto;
import com.ironman.pharmasales.invoices.application.service.InvoiceService;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
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
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    public ResponseEntity<List<InvoiceDto>> findAll() {
        List<InvoiceDto> invoices = invoiceService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(invoices);
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
    public ResponseEntity<InvoiceDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        InvoiceDto invoice = invoiceService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(invoice);
    }

    @ApiResponse(responseCode = StatusCode.CREATED)
    @PostMapping
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody InvoiceSaveDto invoiceSaveDto) throws DataNotFoundException {
        InvoiceDto invoice = invoiceService.create(invoiceSaveDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invoice);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> edit(@PathVariable("id") Long id, @Valid @RequestBody InvoiceSaveDto invoiceSaveDto)
            throws DataNotFoundException {
        InvoiceDto invoice = invoiceService.edit(id, invoiceSaveDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(invoice);
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
    public ResponseEntity<InvoiceDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        InvoiceDto invoice = invoiceService.disabled(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(invoice);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    ResponseEntity<PageResponse<InvoiceDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "El campo invoiceDateFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "invoiceDateFrom", required = false) LocalDate invoiceDateFrom,

            @Parameter(description = "El campo invoiceDateTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "invoiceDateTo", required = false) LocalDate invoiceDateTo,

            @Pattern(regexp = "^\\d*$", message = "El clientId debe contener solo dígitos")
            @RequestParam(name = "clientId", required = false) Long clientId,

            @Pattern(regexp = "^\\d*$", message = "El userId debe contener solo dígitos")
            @RequestParam(name = "userId", required = false) Long userId,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo sort debe ser 'id', 'name', 'clientId', 'userId' o 'invoiceDate'")
            @Pattern(regexp = "^(id|name|clientId|userId|invoiceDate)$", message = "El campo sort debe ser 'id', 'name', 'clientId', 'userId' o 'invoiceDate'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "El campo direction debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        var filter = InvoiceFilterDto.builder()
                .page(page)
                .size(size)
                .invoiceDateFrom(invoiceDateFrom)
                .invoiceDateTo(invoiceDateTo)
                .clientId(clientId)
                .userId(userId)
                .state(state)
                .sort(sort)
                .direction(direction)
                .build();

        var invoicesPage = invoiceService.findAll(filter);

        return ResponseEntity.ok(invoicesPage);
    }


}
