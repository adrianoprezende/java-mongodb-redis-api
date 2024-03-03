package com.rezende.javamongodbredisapi.endpoint.base;

import com.github.fppt.jedismock.RedisServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles("component-test")
public class ComponentBaseTest {

    private static final int MONGO_PORT = 27017;
    private static final int REDIS_PORT = 6379;

    @Autowired
    private Environment env;

    private static RedisServer redis = RedisServer.newRedisServer(REDIS_PORT);


    @Container
    private static final GenericContainer<?> mongoContainer = new GenericContainer<>(DockerImageName.parse("mongo:6.0"))
            .withExposedPorts(MONGO_PORT)
            .withClasspathResourceMapping("data/component-init.js", "/docker-entrypoint-initdb.d/mongo-init.js", BindMode.READ_ONLY)
            .withEnv("MONGO_INITDB_DATABASE", "catalog")
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "admin!")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) throws IOException {
        redis.start();
        mongoContainer.start();
        mongoContainer.withAccessToHost(true);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> String.valueOf(REDIS_PORT));
        registry.add("spring.data.mongodb.uri", () -> String.format("mongodb://admin:admin!@%s:%s/catalog?authSource=admin", mongoContainer.getHost(), mongoContainer.getMappedPort(MONGO_PORT)));
        registry.add("spring.data.mongodb.database", () -> "catalog");
        registry.add("spring.data.mongodb.port", () -> mongoContainer.getMappedPort(MONGO_PORT).toString());
        registry.add("spring.data.mongodb.host", mongoContainer::getHost);
    }


    @AfterAll
    static void afterAll() throws IOException {
        redis.stop();
        mongoContainer.stop();
    }

    @Test
    @DisplayName("Validar se o container está rodando")
    void containerIsRunning() {
        assertTrue(mongoContainer.isRunning());
        assertTrue(mongoContainer.isCreated());
        assertTrue(mongoContainer.isHostAccessible());
    }

}
