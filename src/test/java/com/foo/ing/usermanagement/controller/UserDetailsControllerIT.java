package com.foo.ing.usermanagement.controller;


import com.foo.ing.usermanagement.model.Address;
import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.service.UserDetailsService;
import com.foo.ing.usermanagement.util.MockDataGenerator;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserDetailsControllerIT {

    @InjectMocks
    private UserDetailsController mockedController;

    private MockMvc mvc;

    @Mock
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(mockedController).build();
    }


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

        Gson gson = new Gson();
        final UserDetails returnedUserDetails = gson.fromJson(response, UserDetails.class);

        assertNotNull(returnedUserDetails);
        assertEquals("testlast", returnedUserDetails.getLastName());


        final Address returnedAddress = returnedUserDetails.getAddress();
        assertNotNull(returnedAddress);
        assertEquals("12345 holling rd", returnedAddress.getStreet());


    }
}