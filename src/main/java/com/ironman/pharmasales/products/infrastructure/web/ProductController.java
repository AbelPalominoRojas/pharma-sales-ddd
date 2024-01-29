package com.ironman.pharmasales.products.infrastructure.web;

import com.ironman.pharmasales.products.application.dto.product.*;
import com.ironman.pharmasales.products.application.service.ProductService;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.exception.model.ArgumentNotValidError;
import com.ironman.pharmasales.shared.domain.exception.model.GeneralError;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.web.constant.StatusCode;
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
@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    public ResponseEntity<List<ProductSmallDto>> findAll() {
        List<ProductSmallDto> products = productService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
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
    public ResponseEntity<ProductDto> findById(@PathVariable("id") Long id)
            throws DataNotFoundException {
        ProductDto product = productService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
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
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductSaveDto productBody)
            throws DataNotFoundException {
        ProductDto product = productService.create(productBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(product);
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
    public ResponseEntity<ProductDto> edit(@PathVariable("id") Long id, @Valid @RequestBody ProductSaveDto productBody)
            throws DataNotFoundException {
        ProductDto product = productService.edit(id, productBody);

        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
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
    public ResponseEntity<ProductDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        ProductDto product = productService.disabled(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(product);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    public ResponseEntity<PageResponse<ProductDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,
            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "presentation", required = false) String presentation,
            @Pattern(regexp = "^\\d*$", message = "El stock debe contener solo dígitos")
            @RequestParam(name = "stock", required = false) Long stock,
            @Pattern(regexp = "^\\d*$", message = "El subcategoryId debe contener solo dígitos")
            @RequestParam(name = "subcategoryId", required = false) Long subcategoryId,
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,
            @Pattern(regexp = "^(name|createdAt)$", message = "La dirección debe ser 'name' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        ProductFilterDto filter = ProductFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .description(description)
                .presentation(presentation)
                .stock(stock)
                .subcategoryId(subcategoryId)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        PageResponse<ProductDto> productDtoPage = productService.findAll(filter);

        return ResponseEntity.status(HttpStatus.OK)
                .body(productDtoPage);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/search/{searchText}")
    public ResponseEntity<List<ProductMediumDto>> search(@PathVariable("searchText") String searchText) {
        List<ProductMediumDto> products = productService.search(searchText);

        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
    }
}
