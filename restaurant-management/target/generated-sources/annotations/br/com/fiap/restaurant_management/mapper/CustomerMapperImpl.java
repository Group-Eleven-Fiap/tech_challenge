package br.com.fiap.restaurant_management.mapper;

import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.AddressResponse;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-22T21:35:00-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public CustomerResponse toCustomerResponse(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String login = null;
        AddressResponse address = null;
        LocalDateTime lastModified = null;

        id = customer.getId();
        name = customer.getName();
        email = customer.getEmail();
        login = customer.getLogin();
        address = addressMapper.toResponse( customer.getAddress() );
        lastModified = customer.getLastModified();

        CustomerResponse customerResponse = new CustomerResponse( id, name, email, login, address, lastModified );

        return customerResponse;
    }
}
