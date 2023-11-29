package com.bobocode.bring.web;

import com.bobocode.bring.core.context.impl.BringApplicationContext;
import com.bobocode.bring.web.server.properties.ServerProperties;
import com.bobocode.bring.web.servlet.error.ErrorResponse;
import com.bobocode.bring.web.servlet.http.HttpHeaders.Name;
import com.bobocode.bring.web.servlet.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.bobocode.bring.web.RestControllerTest.URL;
import static com.bobocode.bring.web.servlet.http.MediaType.TEXT_HTML_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaticResourceControllerIntegrationTest {

    private static final String STATIC_FILE_NOT_FOUND_MESSAGE = "Can't find the File: %s.";
    public static final String PACKAGE = "testdata.generalintegrationtest";

    private static ObjectMapper objectMapper;

    private static ServerProperties serverProperties;

    private HttpClient httpClient;

    @BeforeAll
    static void beforeAll() {
        BringApplicationContext bringApplicationContext = BringWebApplication.run(PACKAGE);
        objectMapper = bringApplicationContext.getBean(ObjectMapper.class);
        serverProperties = bringApplicationContext.getBean(ServerProperties.class);
    }

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    @DisplayName("Should successfully return a static file")
    @SneakyThrows
    void shouldReturnStaticFile() {
        //given
        String url = "/static/index.html";
        HttpRequest request = getHttpGetRequest(getHost() + url);

        //when
        HttpResponse<String> actualResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //then
        assertEquals(actualResponse.statusCode(), HttpStatus.OK.getValue());
        assertTrue(actualResponse.headers().map().get(Name.CONTENT_TYPE.getName()).contains(TEXT_HTML_VALUE));
    }

    @Test
    @DisplayName("Should not return a static file when the file is not found")
    @SneakyThrows
    void shouldNotReturnStaticFile() {
        //given
        String url = "/static/wrongFolder/index.html";
        String errorMessage = String.format(STATIC_FILE_NOT_FOUND_MESSAGE, url);
        HttpRequest request = getHttpGetRequest(getHost() + url);

        //when
        String actualResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        ErrorResponse errorResponse = objectMapper.readValue(actualResponse, ErrorResponse.class);

        //then
        assertEquals(HttpStatus.BAD_REQUEST.getValue(), errorResponse.getCode());
        assertEquals(errorMessage, errorResponse.getMessage());
    }

    private String getHost() {
        return String.format(URL, serverProperties.getPort(), serverProperties.getContextPath());
    }

    @SneakyThrows
    private HttpRequest getHttpGetRequest(String url) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(new URI(url))
                .build();
    }
}
