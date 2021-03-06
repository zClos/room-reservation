package com.gmail.buckartz.roomreservation.config;

import lombok.Getter;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public abstract class ControllerTestContext {
    @Rule
    public final JUnitRestDocumentation documentationDestination = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Getter
    private MockMvc mockMvc;

    @Getter
    private RestDocumentationResultHandler documentHandler;

    @Before
    public void setup() {
        documentHandler = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(documentationConfiguration(documentationDestination))
                .addFilter(springSecurityFilterChain)
                .alwaysDo(documentHandler)
                .build();
    }

    protected HttpHeaders authHeaders(String login, String password) {
        String credentials = String.format("%s:%s", login, password);
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");
        headers.add("Authorization", "Basic " + encodedCredentials);
        return headers;
    }

    protected LocalDateTime getTestLocalDateTime() {
        return Optional.of(now()).map(now -> {
            if (now.plusHours(2).getHour() > 18 ||
                    now.getHour() < 9 ||
                    now.getDayOfMonth() < now.plusHours(2).getDayOfMonth()) {
                if (now.getDayOfWeek().getValue() == 5 ||
                        now.getDayOfWeek().getValue() == 6 ||
                        now.getDayOfWeek().getValue() == 7) {
                    return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 3,
                            12, 0);
                }
                return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 1,
                        12, 0);
            } else if (now.getDayOfWeek().getValue() == 6 || now.getDayOfWeek().getValue() == 7) {
                return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth() + 3,
                        12, 0);
            }
            return now.minusSeconds(now.getSecond()).minusNanos(now.getNano());
        }).orElse(null);
    }
}
