package com.poluhin.ss.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PremiumControllerTest extends AbstractTest {

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void should_return_ok() throws Exception {
        mockMvc
                .perform(get("/api/premium"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_be_forbidden() throws Exception {
        mockMvc
                .perform(get("/api/premium"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
