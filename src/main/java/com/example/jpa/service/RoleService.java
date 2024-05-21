package com.example.jpa.service;

import com.example.jpa.entity.Role;
import com.example.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByRole(String name){
         return roleRepository.findByName(name);
    }
}
