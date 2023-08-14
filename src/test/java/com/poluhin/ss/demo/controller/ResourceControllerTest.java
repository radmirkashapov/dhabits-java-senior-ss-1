package com.poluhin.ss.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractTest {

    @Test
    @DisplayName("createResourceObject")
    void createResourceObject() throws Exception {
        // language=JSON5
        String requestBody = """
                {
                    "id": 1,
                    "value": "value1",
                    "path": "path1"
                }
                """;

        mockMvc
                .perform(
                        post("/resource")
                                .content(requestBody)
                                .contentType(APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("getResourceObject existingOk")
    void getResourceObject_existingOk() throws Exception {
        // language=JSON5
        String requestBody = """
                {
                    "id": 1,
                    "value": "value1",
                    "path": "path1"
                }
                """;

        mockMvc
                .perform(
                        post("/resource")
                                .content(requestBody)
                                .contentType(APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        mockMvc
                .perform(get("/resource/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(requestBody));

    }

    @Test
    @DisplayName("getResourceObject throwsNotFound")
    void getResourceObject_throwsNotFound() throws Exception {
        mockMvc
                .perform(get("/resource/100000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }

}
