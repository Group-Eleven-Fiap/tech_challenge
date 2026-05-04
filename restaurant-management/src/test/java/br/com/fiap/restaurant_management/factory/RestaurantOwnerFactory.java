package br.com.fiap.restaurant_management.factory;

import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;

public class RestaurantOwnerFactory {

    public static RestaurantOwnerRequest defaultRequest() {
        return new RestaurantOwnerRequest(
                "João da Silva",
                "joao.silva@teste.com",
                "joao.silva",
                "semcriptografia",
                AddressFactory.defaultRequest(),
                "Restaurante Teste",
                "54.445.847/0001-09");
    }

    public static RestaurantOwnerRequest invalidRequest() {
        return new RestaurantOwnerRequest(
                "",
                "",
                "",
                "",
                AddressFactory.defaultRequest(),
                "",
                "");
    }

    public static RestaurantOwnerResponse defaultResponse() {

        return new RestaurantOwnerResponse(
                1L,
                "João da Silva",
                "joao.silva@teste.com",
                "joao.silva",
                AddressFactory.defaultResponse(),
                "Restaurante Teste",
                "54.445.847/0001-09",
                null

        );
    }

    public static RestaurantOwner defaultModel() {
        return RestaurantOwner.builder()
                .id(1L)
                .name("João da Silva")
                .email("joao.silva@teste.com")
                .login("joao.silva")
                .password("semcriptografia")
                .address(AddressFactory.defaultModel())
                .restaurantName("Restaurante Teste")
                .cnpj("54.445.847/0001-09")
                .build();
    }

    public static RestaurantOwner withPassword(String password) {
        RestaurantOwner restaurantOwner = defaultModel();
        restaurantOwner.setPassword(password);
        return restaurantOwner;
    }

    public static RestaurantOwner withName(String name) {

        return RestaurantOwner.builder()
//                .id(1L)
                .name(name)
                .email("teste.login@test.com")
                .login("teste.login")
                .password("senhasemupdate")
                .address(AddressFactory.defaultModel())
                .restaurantName("Nome diferente")
                .cnpj("17.279.181/0001-30")
                .build();

    }

}
