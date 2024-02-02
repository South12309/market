package ru.gb.market.authservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.market.authservice.entities.Role;
import ru.gb.market.authservice.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}