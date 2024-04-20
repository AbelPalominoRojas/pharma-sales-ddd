package com.ironman.pharmasales.products.infrastructure.web;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySaveDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySmallDto;
import com.ironman.pharmasales.products.application.service.SubcategoryService;
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
@RequestMapping("/subcategories")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    public ResponseEntity<List<SubcategorySmallDto>> findAll() {
        List<SubcategorySmallDto> subcategories = subcategoryService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(subcategories);
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
    public ResponseEntity<SubcategoryDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        SubcategoryDto subcategory = subcategoryService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(subcategory);
    }

    @ApiResponse(responseCode = StatusCode.CREATED)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @PostMapping
    public ResponseEntity<SubcategoryDto> create(@Valid @RequestBody SubcategorySaveDto subcategoryBody)
            throws DataNotFoundException {
        SubcategoryDto subcategory = subcategoryService.create(subcategoryBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subcategory);
    }


    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDto> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody SubcategorySaveDto subcategoryBody
    ) throws DataNotFoundException {
        SubcategoryDto subcategory = subcategoryService.edit(id, subcategoryBody);

        return ResponseEntity.status(HttpStatus.OK)
                .body(subcategory);
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
    public ResponseEntity<SubcategoryDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        SubcategoryDto subcategory = subcategoryService.disabled(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(subcategory);
    }


    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    public ResponseEntity<PageResponse<SubcategoryDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,
            @Pattern(regexp = "^\\d*$", message = "El categoryId debe contener solo dígitos")
            @RequestParam(name = "categoryId", required = false) Long categoryId,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo createdAtFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,

            @Parameter(description = "El campo createdAtTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,

            @Parameter(description = "El campo sort debe ser 'id', 'name', 'categoryId' o 'createdAt'")
            @Pattern(regexp = "^(id|name|categoryId|createdAt)$", message = "El campo sort debe ser 'id', 'name', 'categoryId' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "El campo direction debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        SubcategoryFilterDto filter = SubcategoryFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .description(description)
                .categoryId(categoryId)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();


        PageResponse<SubcategoryDto> subcategoryDtoPage = subcategoryService.findAll(filter);

        return ResponseEntity.status(HttpStatus.OK)
                .body(subcategoryDtoPage);
    }

}
