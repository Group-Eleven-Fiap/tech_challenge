package br.com.fiap.restaurant_management.mapper;

import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface RestaurantOwnerMapper {

    RestaurantOwnerResponse toRestaurantOwnerResponse(RestaurantOwner owner);
}
