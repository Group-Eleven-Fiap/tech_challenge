package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;
import br.com.fiap.restaurant_management.mapper.AddressMapper;
import br.com.fiap.restaurant_management.mapper.RestaurantOwnerMapper;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import br.com.fiap.restaurant_management.repository.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOwnerService {

    private final RestaurantOwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;
    private final RestaurantOwnerMapper restaurantOwnerMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public RestaurantOwnerResponse create(RestaurantOwnerRequest request) {

        log.info("Iniciando criação de dono de restaurante | email={}", request.email());

        validateEmail(request.email());

        RestaurantOwner owner = RestaurantOwner.builder()
                .name(request.name())
                .email(request.email())
                .login(request.login())
                .password(passwordEncoder.encode(request.password()))
                .restaurantName(request.restaurantName())
                .cnpj(request.cnpj())
                .address(addressMapper.toModel(request.address()))
                .build();

        RestaurantOwner saved = ownerRepository.save(owner);

        log.info("Dono de restaurante criado | id={}", saved.getId());

        return restaurantOwnerMapper.toRestaurantOwnerResponse(saved);
    }

    public void delete(Long id) {
        log.debug("Verificando existência do usuário ID: {}", id);

        var owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));

        ownerRepository.delete(owner);
        log.info("Dono removido com sucesso | id={}", id);

    }

    public RestaurantOwnerResponse update(Long id, RestaurantOwnerRequest request){
        var owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));

        log.info("Iniciando atualização de dono | id={}", id);

        owner.setName(request.name());
        owner.setRestaurantName(request.restaurantName());
        owner.setAddress(addressMapper.toModel(request.address()));

        RestaurantOwner updated = ownerRepository.save(owner);

        log.info("Usuário atualizado no banco de dados - ID: {}", id);

        return restaurantOwnerMapper.toRestaurantOwnerResponse(updated);
    }

    public List<RestaurantOwnerResponse> findByName(String name){
        log.info("Buscando donos por nome | name={}", name);

        List<RestaurantOwner> owners = ownerRepository.findByNameContainingIgnoreCase(name);

        if (owners.isEmpty()) {
            log.warn("Nenhum dono encontrado de nome = {}", name);
            throw new RuntimeException("Nenhum dono encontrado com o nome: " + name);
        }

        log.info("Donos encontrados | quantidade = {} ", owners.size());

        return owners.stream()
                .map(restaurantOwnerMapper::toRestaurantOwnerResponse)
                .toList();
    }

    public void changePassword(Long id, ChangePasswordRequest request) {

        log.info("Iniciando troca de senha (owner) | id={}", id);

        RestaurantOwner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));

        if (!passwordEncoder.matches(request.oldPassword(), owner.getPassword())) {
            log.warn("Senha inválida | id={}", id);
            throw new RuntimeException("Senha atual inválida");
        }

        owner.setPassword(passwordEncoder.encode(request.newPassword()));
        ownerRepository.save(owner);

        log.info("Senha alterada com sucesso | id={} ", id);
    }

    private void validateEmail(String email) {

        log.debug("Validando email | email={}", email);

        if (ownerRepository.existsByEmail(email) || customerRepository.existsByEmail(email)) {
            log.warn("Email já cadastrado | email={}", email);
            throw new RuntimeException("Email já cadastrado");
        }
    }
}
