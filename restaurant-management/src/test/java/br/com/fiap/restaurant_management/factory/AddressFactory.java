package br.com.fiap.restaurant_management.factory;

import br.com.fiap.restaurant_management.entity.Address;
import br.com.fiap.restaurant_management.entity.dtos.AddressRequest;
import br.com.fiap.restaurant_management.entity.dtos.AddressResponse;

public class AddressFactory {

    public static AddressRequest defaultRequest() {
        return new AddressRequest(
                "Rua Exemplo",
                "123",
                "Cidade Exemplo",
                "Estado Exemplo",
                "12345-678");
    }

    public static AddressResponse defaultResponse() {
        return new AddressResponse(
                "Rua Exemplo",
                "123",
                "Cidade Exemplo",
                "Estado Exemplo",
                "12345-678");
    }

    public static Address defaultModel() {
        return new Address(
                "Rua Exemplo",
                "123",
                "Cidade Exemplo",
                "Estado Exemplo",
                "12345-678");
    }

}
