package br.com.fiap.restaurant_management.factory;

import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;

public class CustomerFactory {

    public static CustomerRequest defaultRequest() {
        return new CustomerRequest(
                "João da Silva",
                "joao.silva@teste.com",
                "joao.silva",
                "semcriptografia",
                AddressFactory.defaultRequest());
    }

    public static CustomerRequest invalidRequest() {
        return new CustomerRequest(
                "",
                "",
                "",
                "",
                AddressFactory.defaultRequest());
    }

    public static CustomerResponse defaultResponse() {

        //        LocalDateTime now = LocalDateTime.of(2026, 4, 26, 12, 00);


        return new CustomerResponse(
                1L,
                "João da Silva",
                "joao.silva@teste.com",
                "joao.silva",
                AddressFactory.defaultResponse(),
                null
        );
    }

    public static Customer defaultModel() {
        return Customer.builder()
                .id(1L)
                .name("João da Silva")
                .email("joao.silva@teste.com")
                .login("joao.silva")
                .password("semcriptografia")
                .address(AddressFactory.defaultModel())
                .build();
    }

    public static Customer withPassword(String password) {
        Customer customer = defaultModel();
        customer.setPassword(password);
        return customer;
    }

    public static Customer withName(String name) {

        return Customer.builder()
//                .id(1L)
                .name(name)
                .email("teste.login@test.com")
                .login("teste.login")
                .password("senhasemupdate")
                .address(AddressFactory.defaultModel())
                .build();

    }

}
