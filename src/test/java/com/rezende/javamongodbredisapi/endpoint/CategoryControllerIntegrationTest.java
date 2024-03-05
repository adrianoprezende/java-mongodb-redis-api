package com.rezende.javamongodbredisapi.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezende.javamongodbredisapi.Application;
import com.rezende.javamongodbredisapi.ApplicationTests;
import com.rezende.javamongodbredisapi.external.repository.CategoryRepository;
import com.rezende.javamongodbredisapi.external.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class, ApplicationTests.class})
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration",
        "spring.main.allow-bean-definition-overriding=true"
})
@ActiveProfiles("integration-test")
public class CategoryControllerIntegrationTest {

    private static final String ENDPOINT = "/api/categories";
    private static final String ID_ENDPOINT = "/api/categories/{id}";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void whenBodyIsEmptyOnCreate_thenReturnsStatus400() throws Exception {
        // given
        var blankBody = "{}";

        // when
        final MvcResult mvcResult = mvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(blankBody)).andReturn();

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        // then
        assertEquals(400, mvcResult.getResponse().getStatus());

        Assertions.assertTrue(jsonNode.has("errors"));
        Assertions.assertTrue(jsonNode.has("meta"));

        final JsonNode errorNode = jsonNode.get("errors");
        assertEquals(3, errorNode.size());
    }

    @Test
    void whenBodyIsEmptyOnUpdate_thenReturnsStatus400() throws Exception {
        // given
        var blankBody = "{}";

        // when
        final MvcResult mvcResult = mvc.perform(put(ID_ENDPOINT, "123456721")
                .contentType("application/json")
                .content(blankBody)).andReturn();

        final String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);

        // then
        assertEquals(400, mvcResult.getResponse().getStatus());

        Assertions.assertTrue(jsonNode.has("errors"));
        Assertions.assertTrue(jsonNode.has("meta"));

        final JsonNode errorNode = jsonNode.get("errors");
        assertEquals(1, errorNode.size());

        Assertions.assertAll("Validações dos Atributos de Erro",
                () -> assertEquals("Erro de validação", errorNode.get(0).get("title").asText()),
                () -> assertEquals("01", errorNode.get(0).get("code").asText()),
                () -> assertEquals("Pelo menos um campo deve estar presente.", errorNode.get(0).get("detail").asText())
        );
    }
}
