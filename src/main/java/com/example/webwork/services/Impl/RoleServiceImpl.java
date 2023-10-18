package com.example.webwork.services.Impl;

import com.example.webwork.exceptions.RoleConflictException;
import com.example.webwork.exceptions.RoleNotFoundException;
import com.example.webwork.dtos.RoleDto;
import com.example.webwork.models.Role;
import com.example.webwork.repositories.RoleRepository;
import com.example.webwork.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto register(RoleDto role) {
        Role r = modelMapper.map(role, Role.class);
        UUID roleId = r.getId();
        if (roleId == null || roleRepository.findById(roleId).isEmpty()) {
            return modelMapper.map(roleRepository.save(r), RoleDto.class);
        } else {
            throw new RoleConflictException("A role with this id already exists");
        }
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map((s) -> modelMapper.map(s, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> get(UUID id) {
        return Optional.ofNullable(modelMapper.map(roleRepository.findById(id), RoleDto.class));
    }

    @Override
    public void delete(UUID id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException(id);
        }
    }

    @Override
    public RoleDto update(RoleDto role) {
        if (roleRepository.findById(role.getId()).isPresent()) {
            return modelMapper.map(roleRepository.save(modelMapper.map(role, Role.class)), RoleDto.class);
        } else {
            throw new RoleNotFoundException(role.getId());
        }
    }
}
