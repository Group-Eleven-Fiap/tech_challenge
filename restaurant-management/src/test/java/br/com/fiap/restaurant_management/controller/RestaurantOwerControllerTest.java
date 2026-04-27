package br.com.fiap.restaurant_management.controller;

import br.com.fiap.restaurant_management.Config.GlobalExceptionHandler;
import br.com.fiap.restaurant_management.Controller.RestaurantOwnerController;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;
import br.com.fiap.restaurant_management.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.factory.RestaurantOwnerFactory;
import br.com.fiap.restaurant_management.service.RestaurantOwnerService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantOwnerController.class)
@Import(GlobalExceptionHandler.class)
public class RestaurantOwerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantOwnerService restaurantOwnerService;

    private final String URL = "/v1/owners";

    // create

    @Test
    void shouldCreateRestaurantOwnerSuccessfully() throws Exception {

        RestaurantOwnerRequest request = RestaurantOwnerFactory.defaultRequest();
        RestaurantOwnerResponse response = RestaurantOwnerFactory.defaultResponse();

        when(restaurantOwnerService.create(any())).thenReturn(response);

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

        ArgumentCaptor<RestaurantOwnerRequest> captor = ArgumentCaptor.forClass(RestaurantOwnerRequest.class);
        verify(restaurantOwnerService).create(captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);
    }

    @Test
    void shouldReturn409WhenCreateWithDuplicateEmail() throws Exception {

        RestaurantOwnerRequest request = RestaurantOwnerFactory.defaultRequest();

        when(restaurantOwnerService.create(any())).thenThrow(new ResourceAlreadyExistsException(""));

        mockMvc
                .perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isConflict());

        verify(restaurantOwnerService).create(any());

    }

    @Test
    void shouldReturn400WhenCreateRestaurantOwnerWithInvalidData() throws Exception {

        RestaurantOwnerRequest invalidRequest = RestaurantOwnerFactory.invalidRequest();

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(restaurantOwnerService);
    }

    // delete

    @Test
    void shouldDeleteRestaurantOwnerSuccessfully() throws Exception {

        Long validId = 1L;

        doNothing().when(restaurantOwnerService).delete(validId);

        mockMvc.perform(delete(URL + "/{id}", validId))
                .andExpect(status().isNoContent());

        verify(restaurantOwnerService).delete(validId);
    }

    @Test
    void shouldReturn404WhenDeleteNonExistingRestaurantOwner() throws Exception {
        Long invalidId = 1L;

        doThrow(new ResourceNotFoundException("")).when(restaurantOwnerService).delete(invalidId);

        mockMvc.perform(delete(URL + "/{id}", invalidId))
                .andExpect(status().isNotFound());

    }

    //update

    @Test
    void shouldUpdateRestaurantOwnerSuccessfully() throws Exception {

        Long validId = 1L;

        RestaurantOwnerRequest request = RestaurantOwnerFactory.defaultRequest();
        RestaurantOwnerResponse response = RestaurantOwnerFactory.defaultResponse();

        when(restaurantOwnerService.update(validId, request)).thenReturn(response);

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

        ArgumentCaptor<RestaurantOwnerRequest> captor = ArgumentCaptor.forClass(RestaurantOwnerRequest.class);
        verify(restaurantOwnerService).update(eq(validId), captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);

    }

    @Test
    void shouldReturn404WhenUpdateNonExistingRestaurantOwner() throws Exception {
        Long invalidId = 1L;

        RestaurantOwnerRequest request = RestaurantOwnerFactory.defaultRequest();

        doThrow(new ResourceNotFoundException("")).when(restaurantOwnerService).update(invalidId, request);

        mockMvc.perform(put(URL + "/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenUpdateRestaurantOwnerWithInvalidData() throws Exception {

        Long validId = 1L;
        RestaurantOwnerRequest invalidRequest = RestaurantOwnerFactory.invalidRequest();

        mockMvc.perform(put(URL + "/{id}", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(restaurantOwnerService);
    }

    // changePassword

    @Test
    void shouldChangePasswordSuccessfully() throws Exception {

        Long validId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        doNothing().when(restaurantOwnerService).changePassword(validId, request);

        mockMvc.perform(patch(URL + "/{id}/password", validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(request)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<ChangePasswordRequest> captor = ArgumentCaptor.forClass(ChangePasswordRequest.class);

        verify(restaurantOwnerService).changePassword(eq(validId), captor.capture());

        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(request);
    }

    @Test
    void shouldReturn404WhenChangePasswordNonExistingRestaurantOwner() throws Exception {
        Long invalidId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        doThrow(new ResourceNotFoundException("")).when(restaurantOwnerService).changePassword(invalidId, request);

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

        verifyNoInteractions(restaurantOwnerService);
    }

    // findeByName

    @Test
    void shouldReturnRestaurantOwnerListSuccessfully() throws Exception {

        String searchName = "nome";

        List<RestaurantOwnerResponse> responseList = List.of(
                RestaurantOwnerFactory.defaultResponse(),
                RestaurantOwnerFactory.defaultResponse());

        when(restaurantOwnerService.findByName(searchName)).thenReturn(responseList);

        MvcResult result = mockMvc.perform(get(URL + "/search").param("name", searchName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = asJson(responseList);

        JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.NON_EXTENSIBLE);

        verify(restaurantOwnerService).findByName(searchName);

    }

    @Test
    void shouldReturn404WhenGetRestaurantOwnerListWithRestaurantOwnerNameNotFound() throws Exception {

        String searchName = "nome";

        doThrow(new ResourceNotFoundException("")).when(restaurantOwnerService).findByName(searchName);

        mockMvc.perform(get(URL + "/search").param("name", searchName))
                .andExpect(status().isNotFound());
    }

    private String asJson(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
    
}
