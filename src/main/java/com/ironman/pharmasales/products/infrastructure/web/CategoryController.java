package com.ironman.pharmasales.products.infrastructure.web;

import com.ironman.pharmasales.products.application.dto.category.CategoryDto;
import com.ironman.pharmasales.products.application.dto.category.CategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySmallDto;
import com.ironman.pharmasales.products.application.service.CategoryService;
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
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    ResponseEntity<List<CategorySmallDto>> findAll() {
        List<CategorySmallDto> categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
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
    ResponseEntity<CategoryDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        CategoryDto category = categoryService.findById(id);

        return ResponseEntity.ok(category);
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
    ResponseEntity<CategoryDto> create(@Valid @RequestBody CategorySaveDto categoryBody) {
        CategoryDto category = categoryService.create(categoryBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(category);
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
    ResponseEntity<CategoryDto> edit(@PathVariable("id") Long id, @Valid @RequestBody CategorySaveDto categoryBody) throws DataNotFoundException {
        CategoryDto category = categoryService.edit(id, categoryBody);

        return ResponseEntity.ok(category);
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
    ResponseEntity<CategoryDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        CategoryDto category = categoryService.disabled(id);

        return ResponseEntity.ok(category);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    ResponseEntity<PageResponse<CategoryDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,

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
            @Pattern(regexp = "^(id|name|createdAt)$", message = "La dirección debe ser 'id', 'name' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        var filter = CategoryFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .description(description)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        PageResponse<CategoryDto> categoryPage = categoryService.findAll(filter);

        return ResponseEntity.ok(categoryPage);
    }

}
