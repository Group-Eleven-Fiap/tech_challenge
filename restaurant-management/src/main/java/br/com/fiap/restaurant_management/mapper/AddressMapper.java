package br.com.fiap.restaurant_management.mapper;

import br.com.fiap.restaurant_management.entity.Address;
import br.com.fiap.restaurant_management.entity.dtos.AddressRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toModel(AddressRequest request);
}
