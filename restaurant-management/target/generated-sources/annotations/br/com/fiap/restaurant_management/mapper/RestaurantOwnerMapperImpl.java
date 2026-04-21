package br.com.fiap.restaurant_management.mapper;

import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import br.com.fiap.restaurant_management.entity.dtos.AddressResponse;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-18T17:10:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RestaurantOwnerMapperImpl implements RestaurantOwnerMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public RestaurantOwnerResponse toRestaurantOwnerResponse(RestaurantOwner owner) {
        if ( owner == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String login = null;
        AddressResponse address = null;
        String restaurantName = null;
        String cnpj = null;
        LocalDateTime lastModified = null;

        id = owner.getId();
        name = owner.getName();
        email = owner.getEmail();
        login = owner.getLogin();
        address = addressMapper.toResponse( owner.getAddress() );
        restaurantName = owner.getRestaurantName();
        cnpj = owner.getCnpj();
        lastModified = owner.getLastModified();

        RestaurantOwnerResponse restaurantOwnerResponse = new RestaurantOwnerResponse( id, name, email, login, address, restaurantName, cnpj, lastModified );

        return restaurantOwnerResponse;
    }
}
