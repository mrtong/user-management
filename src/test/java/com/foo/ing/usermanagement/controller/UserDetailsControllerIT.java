package com.foo.ing.usermanagement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.foo.ing.usermanagement.exception.UserDetailsNotFoundException;
import com.foo.ing.usermanagement.model.Address;
import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.service.UserDetailsService;
import com.foo.ing.usermanagement.util.MockDataGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserDetailsControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void givenUserDetailsID_whenFound_thenReturnTheFoundUserDetailsWithAddress() throws Exception {

        final UserDetails userDetails = MockDataGenerator.generateOneUserDetails();
        when(userDetailsService.findUserDetailsById(anyInt())).thenReturn(userDetails);

        final MvcResult mvcResult = mvc.perform(get("/api/userdetails/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        final String response = mvcResult.getResponse().getContentAsString();
        final String expectedResponse
                = "{\"title\":\"mr\",\"address\":{\"street\":\"12345 holling rd\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},\"firstn\":\"test\",\"lastName\":\"testlast\",\"gender\":\"male\"}";

        assertEquals(expectedResponse, response);

        final ObjectMapper om = new ObjectMapper();
        final UserDetails returnedUserDetails = om.readValue(response, UserDetails.class);

        assertNotNull(returnedUserDetails);
        assertEquals("test", returnedUserDetails.getFirstName());
        assertEquals("testlast", returnedUserDetails.getLastName());


        final Address returnedAddress = returnedUserDetails.getAddress();
        assertNotNull(returnedAddress);
        assertEquals("12345 holling rd", returnedAddress.getStreet());

    }

    @Test
    public void givenUserDetailsID_whenNotFound_thenShouldRaiseException() throws Exception {
        final String content
                = "{\"title\":\"mr\",\"address\":{\"street\":\"12345 holling rd\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},\"firstn\":\"updatedTest\",\"lastName\":\"testlast\",\"gender\":\"male\"}";

        when(userDetailsService.updateUserDetails(anyInt(), any())).thenThrow(UserDetailsNotFoundException.class);

        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/api/userdetails/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content);

        mvc.perform(builder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenUserDetailsID_whenFound_And_HaveAdminRole_thenShouldUpdate() throws Exception {

        final UserDetails userDetails = MockDataGenerator.generateUpdatedUserDetails();
        final String content
                = "{\"title\":\"mr\",\"address\":{\"street\":\"12345 holling rd\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},\"firstn\":\"updatedTest\",\"lastName\":\"testlast\",\"gender\":\"male\"}";

        when(userDetailsService.updateUserDetails(anyInt(), any())).thenReturn(userDetails);

        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/api/userdetails/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content);

        final MvcResult mvcResult = mvc.perform(builder)
                .andExpect(status().isOk())
                .andReturn();

        final String response = mvcResult.getResponse().getContentAsString();
        final String expectedResponse
                = "{\"title\":\"mr\",\"address\":{\"street\":\"12345 holling rd\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},\"firstn\":\"updatedTest\",\"lastName\":\"testlast\",\"gender\":\"male\"}";

        assertEquals(expectedResponse, response);

        final ObjectMapper om = new ObjectMapper();
        final UserDetails returnedUserDetails = om.readValue(response, UserDetails.class);

        assertNotNull(returnedUserDetails);
        assertEquals("updatedTest", returnedUserDetails.getFirstName());
    }

    @Test
    @WithMockUser(username = "user")
    public void givenUserDetailsID_whenFound_And_HaveUserRole_thenReturnStatusForbidden() throws Exception {

        final UserDetails userDetails = MockDataGenerator.generateUpdatedUserDetails();
        final String content
                = "{\"title\":\"mr\",\"address\":{\"street\":\"12345 holling rd\",\"city\":\"Sydney\",\"state\":\"NSW\",\"postcode\":\"2000\"},\"firstn\":\"updatedTest\",\"lastName\":\"testlast\",\"gender\":\"male\"}";

        when(userDetailsService.updateUserDetails(anyInt(), any())).thenReturn(userDetails);

        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/api/userdetails/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content);

        final MvcResult mvcResult = mvc.perform(builder)
                .andExpect(status().isForbidden())
                .andReturn();

    }

}