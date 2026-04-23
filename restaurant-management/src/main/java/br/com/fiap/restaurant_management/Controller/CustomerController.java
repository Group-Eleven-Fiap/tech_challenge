package br.com.fiap.restaurant_management.Controller;

import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import br.com.fiap.restaurant_management.service.CustomerService;
import br.com.fiap.restaurant_management.Config.CustomerControllerApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController implements CustomerControllerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {

        CustomerResponse response = customerService.create(request);

        log.info("Cliente cadastrado com sucesso {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> findByName(@RequestParam String name) {

        log.info("Buscando cliente com nome {}", name);

        List<CustomerResponse> customers = customerService.findByName(name);

        log.info("Cliente encontrado com nome {}", name);

        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Recebendo requisição para deletar cliente com id: {}", id);

        customerService.delete(id);

        log.info("Cliente deletado com sucesso | id={}", id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest request) {

        log.info("Recebendo requisição para atualizar cliente com ID: {}", id);

        CustomerResponse response = customerService.update(id, request);

        log.info("Cliente atualizado com sucesso | id={}", id);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request) {

        log.info("Cliente solicitando troca de senha | id={}", id);

        customerService.changePassword(id, request);

        log.info("Senha alterada com sucesso | id={}", id);

        return ResponseEntity.noContent().build();
    }
}
