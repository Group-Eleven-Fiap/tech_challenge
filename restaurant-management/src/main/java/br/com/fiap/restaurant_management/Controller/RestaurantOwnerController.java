package br.com.fiap.restaurant_management.Controller;

import br.com.fiap.restaurant_management.entity.dtos.*;
import br.com.fiap.restaurant_management.service.RestaurantOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOwnerController {

    private final RestaurantOwnerService restaurantOwnerService;

    @PostMapping
    public ResponseEntity<RestaurantOwnerResponse> createOwner(
            @RequestBody @Valid RestaurantOwnerRequest request) {

        RestaurantOwnerResponse response = restaurantOwnerService.create(request);

        log.info("Cliente cadastrado com sucesso {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantOwnerResponse>> findByName(
            @RequestParam String name) {

        log.info("Buscando cliente com nome {}", name);

        List<RestaurantOwnerResponse> owners = restaurantOwnerService.findByName(name);

        log.info("Cliente encontrado com nome {}", name);

        return ResponseEntity.ok(owners);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Recebendo requisição para deletar cliente com id: {}", id);

        restaurantOwnerService.delete(id);

        log.info("Cliente deletado com sucesso | id={}", id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantOwnerResponse> updateOwner(
            @PathVariable Long id,
            @RequestBody @Valid RestaurantOwnerRequest request) {

        log.info("Recebendo requisição para atualizar cliente com ID: {}", id);

        RestaurantOwnerResponse response = restaurantOwnerService.update(id, request);

        log.info("Cliente atualizado com sucesso | id={}", id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody @Valid ChangePasswordRequest request) {

        log.info("Cliente solicitando troca de senha | id={}", id);

        restaurantOwnerService.changePassword(id, request);

        log.info("Senha alterada com sucesso | id={}", id);

        return ResponseEntity.noContent().build();
    }
}
