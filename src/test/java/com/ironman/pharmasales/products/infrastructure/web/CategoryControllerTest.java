package com.ironman.pharmasales.products.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironman.pharmasales.products.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Category;
import com.ironman.pharmasales.products.infrastructure.persistence.repository.CategoryRepository;
import com.ironman.pharmasales.shared.application.state.enums.State;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void canFindCategoryById() throws Exception {
        // given
        Long id = 2L;

        Category category = Category.builder()
                .id(id)
                .name("Categoria 1")
                .state(State.ACTIVE.getValue())
                .build();

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        // when
        var request = MockMvcRequestBuilders
                .get("/categories/{id}", id);

        ResultActions response = mockMvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(category.getId().intValue())))
                .andExpect(jsonPath("$.name", is(category.getName())));

    }

    @Test
    void canFindAllCategories() throws Exception {
        // given
        Category category1 = Category.builder()
                .id(1L)
                .name("Categoria 1")
                .state(State.ACTIVE.getValue())
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("Categoria 2")
                .state(State.ACTIVE.getValue())
                .build();

        Category category3 = Category.builder()
                .id(3L)
                .name("Categoria 3")
                .state(State.ACTIVE.getValue())
                .build();

        when(categoryRepository.findByState(State.ACTIVE.getValue()))
                .thenReturn(List.of(category1, category2, category3));

        // when
        var request = MockMvcRequestBuilders
                .get("/categories");

        ResultActions response = mockMvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", is(category1.getName())))
                .andExpect(jsonPath("$[1].name", is(category2.getName())))
                .andExpect(jsonPath("$[2].name", is(category3.getName())))
                .andExpect(jsonPath("$.length()", is(3)))
        ;
    }

    @Test
    void canCreateCategory() throws Exception {
        // given
        Category category = Category.builder()
                .id(1L)
                .name("Categoria")
                .description("Detalle")
                .state(State.ACTIVE.getValue())
                .build();

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        // when
        CategorySaveDto categoryDto = CategorySaveDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();

        var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto));

        ResultActions response = mockMvc.perform(request);

        // then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(category.getName())));

    }

    @Test
    void canEditCategory() throws Exception {
        // given
        Long id = 1L;
        Category category = Category.builder()
                .id(id)
                .name("Categoria")
                .description("Detalle")
                .state(State.ACTIVE.getValue())
                .build();

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        // when
        CategorySaveDto categoryDto = CategorySaveDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();

        var request = MockMvcRequestBuilders
                .put("/categories/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto));

        ResultActions response = mockMvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(category.getName())));

    }

    @Test
    void canDisableCategory() throws Exception {
        // given
        Long id = 1L;
        Category category = Category.builder()
                .id(id)
                .name("Categoria")
                .description("Detalle")
                .state(State.DISABLE.getValue())
                .build();

        when(categoryRepository.findById(id))
                .thenReturn(of(category));

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/categories/{id}", category.getId());

        ResultActions response = mockMvc.perform(request);

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.state.value", is(State.DISABLE.getValue())));
    }
}