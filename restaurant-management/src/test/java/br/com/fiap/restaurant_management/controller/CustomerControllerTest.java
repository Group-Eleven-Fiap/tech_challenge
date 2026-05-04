package br.com.fiap.restaurant_management.controller;

import br.com.fiap.restaurant_management.Config.GlobalExceptionHandler;
import br.com.fiap.restaurant_management.Controller.CustomerController;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import br.com.fiap.restaurant_management.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.factory.CustomerFactory;
import br.com.fiap.restaurant_management.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private final String URL = "/v1/customers";

    // create

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {

        CustomerRequest request = CustomerFactory.defaultRequest();
        CustomerResponse response = CustomerFactory.defaultResponse();

        when(customerService.create(any())).thenReturn(response);

        MvcResult result = mockMvc
                .perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = asJson(response);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.NON_EXTENSIBLE);

        ArgumentCaptor<CustomerRequest> captor = ArgumentCaptor.forClass(CustomerRequest.class);
        verify(customerService).create(captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);
    }

    @Test
    void shouldReturn409WhenCreateWithDuplicateEmail() throws Exception {

        CustomerRequest request = CustomerFactory.defaultRequest();

        when(customerService.create(any())).thenThrow(new ResourceAlreadyExistsException(""));

        mockMvc
                .perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isConflict());

        verify(customerService).create(any());

    }

    @Test
    void shouldReturn400WhenCreateCustomerWithInvalidData() throws Exception {

        CustomerRequest invalidRequest = CustomerFactory.invalidRequest();

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(customerService);
    }

    // delete

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {

        Long validId = 1L;

        doNothing().when(customerService).delete(validId);

        mockMvc.perform(delete(URL + "/{id}", validId))
                .andExpect(status().isNoContent());

        verify(customerService).delete(validId);
    }

    @Test
    void shouldReturn404WhenDeleteNonExistingCustomer() throws Exception {
        Long invalidId = 1L;

        doThrow(new ResourceNotFoundException("")).when(customerService).delete(invalidId);

        mockMvc.perform(delete(URL + "/{id}", invalidId))
                .andExpect(status().isNotFound());

    }

    //update

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {

        Long validId = 1L;

        CustomerRequest request = CustomerFactory.defaultRequest();
        CustomerResponse response = CustomerFactory.defaultResponse();

        when(customerService.update(validId, request)).thenReturn(response);

        MvcResult result = mockMvc
                .perform(put(URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = asJson(response);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.NON_EXTENSIBLE);

        ArgumentCaptor<CustomerRequest> captor = ArgumentCaptor.forClass(CustomerRequest.class);
        verify(customerService).update(eq(validId), captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);

    }

    @Test
    void shouldReturn404WhenUpdateNonExistingCustomer() throws Exception {
        Long invalidId = 1L;

        CustomerRequest request = CustomerFactory.defaultRequest();

        doThrow(new ResourceNotFoundException("")).when(customerService).update(invalidId, request);

        mockMvc.perform(put(URL + "/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateCustomerWithInvalidData() throws Exception {

        Long validId = 1L;
        CustomerRequest invalidRequest = CustomerFactory.invalidRequest();

        mockMvc.perform(put(URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(customerService);
    }

    // changePassword

    @Test
    void shouldChangePasswordSuccessfully() throws Exception {

        Long validId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        doNothing().when(customerService).changePassword(validId, request);

        mockMvc.perform(patch(URL + "/{id}/password", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<ChangePasswordRequest> captor = ArgumentCaptor.forClass(ChangePasswordRequest.class);

        verify(customerService).changePassword(eq(validId), captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);
    }

    @Test
    void shouldReturn404WhenChangePasswordNonExistingCustomer() throws Exception {
        Long invalidId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        doThrow(new ResourceNotFoundException("")).when(customerService).changePassword(invalidId, request);

        mockMvc.perform(patch(URL + "/{id}/password", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenChangePasswordWithInvalidData() throws Exception {

        Long validId = 1L;
        ChangePasswordRequest invalidRequest = new ChangePasswordRequest(null, "");

        mockMvc.perform(patch(URL + "/{id}/password", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(customerService);
    }

    // findeByName

    @Test
    void shouldReturnCustomerListSuccessfully() throws Exception {

        String searchName = "nome";

        List<CustomerResponse> responseList = List.of(
                CustomerFactory.defaultResponse(),
                CustomerFactory.defaultResponse());

        when(customerService.findByName(searchName)).thenReturn(responseList);

        MvcResult result = mockMvc.perform(get(URL + "/search").param("name", searchName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = asJson(responseList);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.NON_EXTENSIBLE);

        verify(customerService).findByName(searchName);

    }

    @Test
    void shouldReturn404WhenGetCustomerListWithCustomerNameNotFound() throws Exception {

        String searchName = "nome";

        doThrow(new ResourceNotFoundException("")).when(customerService).findByName(searchName);

        mockMvc.perform(get(URL + "/search").param("name", searchName))
                .andExpect(status().isNotFound());
    }

    private String asJson(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
