package br.com.fiap.restaurant_management.controller;

import br.com.fiap.restaurant_management.entity.dtos.*;
import br.com.fiap.restaurant_management.service.RestaurantOwnerService;
import br.com.fiap.restaurant_management.config.RestaurantOwnerControllerApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/owners")
@Slf4j
public class RestaurantOwnerController implements RestaurantOwnerControllerApi {

    private final RestaurantOwnerService restaurantOwnerService;

    @Override
    public ResponseEntity<RestaurantOwnerResponse> createOwner(@RequestBody @Valid RestaurantOwnerRequest request) {

        log.info("Recebendo requisição para cadastrar novo proprietário: {}", request.email());

        RestaurantOwnerResponse response = restaurantOwnerService.create(request);

        log.info("Proprietário cadastrado com sucesso | ID: {}", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<RestaurantOwnerResponse>> findByName(@RequestParam String name) {

        log.info("Buscando proprietário(s) com nome: {}", name);

        List<RestaurantOwnerResponse> owners = restaurantOwnerService.findByName(name);

        log.info("Proprietário(s) encontrado(s) com sucesso para o nome: {}", name);

        return ResponseEntity.ok(owners);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Recebendo requisição para deletar proprietário | ID: {}", id);

        restaurantOwnerService.delete(id);

        log.info("Proprietário deletado com sucesso | ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RestaurantOwnerResponse> updateOwner(@PathVariable Long id, @RequestBody @Valid RestaurantOwnerRequest request) {

        log.info("Recebendo requisição para atualizar proprietário | ID: {}", id);

        RestaurantOwnerResponse response = restaurantOwnerService.update(id, request);

        log.info("Proprietário atualizado com sucesso | ID: {}", id);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request) {

        log.info("Proprietário solicitando troca de senha | ID: {}", id);

        restaurantOwnerService.changePassword(id, request);

        log.info("Senha alterada com sucesso | ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LoginResponse> validateLogin(@RequestBody @Valid LoginRequest request) {
        restaurantOwnerService.validateLogin(request);
        return ResponseEntity.ok(new LoginResponse("Usuário validado com sucesso"));
    }
}
