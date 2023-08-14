package com.poluhin.ss.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTest {

    @Test
    @DisplayName("success me with admin")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void successMeWithAdmin() throws Exception {
        mockMvc
                .perform(get("/api/users/me"))
                .andDo(print())
                .andExpect(authenticated().withUsername("admin"))
                .andExpect(authenticated().withRoles("ADMIN"));
    }


    @Test
    @DisplayName("success me with user")
    @WithMockUser(username = "user", roles = {"USER"})
    void successMeWithUser() throws Exception {
        mockMvc
                .perform(get("/api/users/me"))
                .andDo(print())
                .andExpect(authenticated().withUsername("user"))
                .andExpect(authenticated().withRoles("USER"));
    }

    @Test
    @DisplayName("throw me with staff user")
    @WithMockUser(username = "staff", roles = {"STAFF"})
    void throwMeWithStuff() throws Exception {
        mockMvc
                .perform(get("/api/users/me"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
