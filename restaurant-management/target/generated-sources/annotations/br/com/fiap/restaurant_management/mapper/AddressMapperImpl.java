package br.com.fiap.restaurant_management.mapper;

import br.com.fiap.restaurant_management.entity.Address;
import br.com.fiap.restaurant_management.entity.dtos.AddressRequest;
import br.com.fiap.restaurant_management.entity.dtos.AddressResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T21:48:01-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toModel(AddressRequest request) {
        if ( request == null ) {
            return null;
        }

        Address address = new Address();

        address.setStreet( request.street() );
        address.setNumber( request.number() );
        address.setCity( request.city() );
        address.setState( request.state() );
        address.setZipCode( request.zipCode() );

        return address;
    }

    @Override
    public AddressResponse toResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        String street = null;
        String number = null;
        String city = null;
        String state = null;
        String zipCode = null;

        street = address.getStreet();
        number = address.getNumber();
        city = address.getCity();
        state = address.getState();
        zipCode = address.getZipCode();

        AddressResponse addressResponse = new AddressResponse( street, number, city, state, zipCode );

        return addressResponse;
    }
}
