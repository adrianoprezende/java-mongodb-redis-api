package com.rezende.javamongodbredisapi.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezende.javamongodbredisapi.endpoint.base.ComponentBaseTest;
import com.rezende.javamongodbredisapi.fixtures.CategoryRequestFixture;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CategoryControllerComponentTest extends ComponentBaseTest {

    private static final String ENDPOINT = "/api/categories";
    private static final String ID_ENDPOINT = "/api/categories/{id}";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${gateway.host}")
    private String gatewayHost;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Validar retorno 4xx ao informar URL incorreta")
    void shouldReturn4xxWhenEndpointDoesNotExist() throws Exception {
        // when
        final MvcResult mvcResult = mockMvc.perform(get(ENDPOINT + "/notexist")).andReturn();

        // then
        assertEquals(405, mvcResult.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validar Criacao de Categoria")
    void shouldCreateCategory() throws Exception {
        // given
        final String category = CategoryRequestFixture.getValidChineseCategoryRequest();

        // when
        final MvcResult mvcResult = mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(category))
                .andReturn();

        // then
        assertEquals(200, mvcResult.getResponse().getStatus());

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        Assertions.assertAll("Validações dos Atributos Gravados",
                () -> assertTrue(jsonNode.has("id")),
                () -> assertEquals("Comida Tailandesa", jsonNode.get("title").asText()),
                () -> assertEquals("Categoria de Comida Tailandesa", jsonNode.get("description").asText()),
                () -> assertEquals("987654322", jsonNode.get("ownerId").asText())
        );

    }


    @Test
    @DisplayName("Validar retorno 200 ao Buscar Categorias")
    void shouldReturn200ResponseWhenGetCategories() throws Exception {
        // given
        final String category = CategoryRequestFixture.getValidChineseCategoryRequest();
        final String page = "1";
        final String size = "25";

        final MvcResult mvcPostResult = mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(category))
                .andReturn();

        // when
        final MvcResult mvcResult = mockMvc.perform(get(ENDPOINT)
                        .param("page", page)
                        .param("page-size", size))
                .andReturn();

        // then
        assertEquals(200, mvcPostResult.getResponse().getStatus());
        assertEquals(200, mvcResult.getResponse().getStatus());

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        Assertions.assertTrue(jsonNode.has("data"));
        Assertions.assertTrue(jsonNode.has("meta"));
        Assertions.assertTrue(jsonNode.has("links"));

        final JsonNode dataNode = jsonNode.get("data");
        assertEquals(1, dataNode.size());

        Assertions.assertAll("Validações do Data",
                () -> assertTrue(dataNode.get(0).has("id")),
                () -> assertEquals("Comida Tailandesa", dataNode.get(0).get("title").asText()),
                () -> assertEquals("Categoria de Comida Tailandesa", dataNode.get(0).get("description").asText()),
                () -> assertEquals("987654322", dataNode.get(0).get("ownerId").asText())
        );

        // meta
        final JsonNode metaNode = jsonNode.get("meta");
        Assertions.assertAll("Validações do Meta",
                () -> assertEquals("1", metaNode.get("totalPages").asText()),
                () -> assertEquals("1", metaNode.get("totalRecords").asText())
        );

        // links
        final JsonNode linksNode = jsonNode.get("links");
        var expectedUrl = Strings.concat("http://", gatewayHost).concat(contextPath).concat(ENDPOINT).concat("?page=1&page-size=25");
        Assertions.assertAll("Validações do Links",
                () -> assertEquals(
                        expectedUrl,
                        linksNode.get("self").asText()),
                () -> Assertions.assertFalse(linksNode.has("first")),
                () -> Assertions.assertFalse(linksNode.has("prev")),
                () -> Assertions.assertFalse(linksNode.has("next")),
                () -> Assertions.assertFalse(linksNode.has("last"))
        );
    }

    @Test
    @DisplayName("Validar retorno 200 ao Atualizar Categoria")
    void shouldReturn200ResponseWhenUpdateCategory() throws Exception {
        // given
        final String taiCategory = CategoryRequestFixture.getValidChineseCategoryRequest();
        final String chineseCategory = CategoryRequestFixture.getValidChineseFoodCategoryRequest();

        final MvcResult mvcPostResult = mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(taiCategory))
                .andReturn();

        final String postJson = mvcPostResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode postJsonNode = OBJECT_MAPPER.readTree(postJson);

        final String id = postJsonNode.get("id").asText();

        // when
        final MvcResult mvcResult = mockMvc.perform(put(ID_ENDPOINT, id)
                        .contentType("application/json")
                        .content(chineseCategory))
                .andReturn();

        // then
        assertEquals(200, mvcResult.getResponse().getStatus());

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        Assertions.assertAll("Validações dos Atributos Gravados",
                () -> assertTrue(jsonNode.has("id")),
                () -> assertEquals("Comida Chinesa", jsonNode.get("title").asText()),
                () -> assertEquals("Categoria de Comida Chinesa", jsonNode.get("description").asText()),
                () -> assertEquals("123456", jsonNode.get("ownerId").asText())
        );
    }

    @Test
    @DisplayName("Validar retorno 204 ao Deletar Categoria")
    void shouldReturn204ResponseWhenDeleteCategory() throws Exception {
        // given
        final String taiCategory = CategoryRequestFixture.getValidChineseCategoryRequest();
        final String chineseCategory = CategoryRequestFixture.getValidChineseFoodCategoryRequest();

        final MvcResult mvcPostResult = mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(taiCategory))
                .andReturn();

        final String postJson = mvcPostResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode postJsonNode = OBJECT_MAPPER.readTree(postJson);

        final String id = postJsonNode.get("id").asText();

        // when
        final MvcResult mvcResult = mockMvc.perform(delete(ID_ENDPOINT, id)
                        .contentType("application/json")
                        .content(chineseCategory))
                .andReturn();

        // then
        assertEquals(204, mvcResult.getResponse().getStatus());

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals("", json);
    }
}





