package com.gmail.buckartz.roomreservation.config;

import lombok.Getter;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
                .alwaysDo(documentHandler)
                .build();
    }
}
