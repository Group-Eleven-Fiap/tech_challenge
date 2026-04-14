package br.com.fiap.restaurant_management.mapper;


import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

    CustomerResponse toCustomerResponse(Customer customer);
}
