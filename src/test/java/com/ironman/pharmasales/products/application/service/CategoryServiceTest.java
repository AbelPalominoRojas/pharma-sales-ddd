package com.ironman.pharmasales.products.application.service;

import com.ironman.pharmasales.products.application.dto.category.CategoryDto;
import com.ironman.pharmasales.products.application.mapper.CategoryMapperImpl;
import com.ironman.pharmasales.products.application.service.impl.CategoryServiceImpl;
import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.port.CategoryPort;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryPort categoryPort;

    @Spy
    private StateMapper stateMapper;

    @Spy
    private CategoryMapperImpl categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void init() {
        List<Field> fields = List.of(CategoryMapperImpl.class.getDeclaredFields());

        fields
                .stream()
                .filter(field -> "stateMapper".equalsIgnoreCase(field.getName()))
                .findFirst()
                .ifPresent(field -> {
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, categoryMapper, stateMapper);
                });

    }


    @Test
    void whenValidId_thenCategoryShouldBeFound() throws DataNotFoundException {
        // given
        Long id = 1L;

        CategoryDomain category = CategoryDomain.builder()
                .id(id)
                .name("Categoria")
                .state(State.ACTIVE.getValue())
                .build();

        when(categoryPort.findById(any(Long.class)))
                .thenReturn(of(category));


        // when
        CategoryDto categoryDto = categoryService.findById(id);

        // then
        assertEquals(category.getName(), categoryDto.getName());
    }

    @Test
    void whenNotValidId_thenExceptionResult() {
        // given
        Long id = 1L;
        String expectedMessage = "Categoria no encontrado para el id: " + id;

        // when
        Exception exception = assertThrows(DataNotFoundException.class, () -> categoryService.findById(id));
        String actualMessage = exception.getMessage();

        // then
        assertEquals(expectedMessage, actualMessage);
    }

}