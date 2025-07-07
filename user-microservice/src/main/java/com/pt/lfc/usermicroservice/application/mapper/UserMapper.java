package com.pt.lfc.usermicroservice.application.mapper;

import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.model.Role;
import com.pt.lfc.usermicroservice.application.dto.*;

import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    // Dominio → DTO
    UserDTO toDTO(User user);

    // Dominio → DTO para /me
    UserMeDTO toMeDTO(User user);

    // Lista de dominio → lista de DTO
    List<UserDTO> toDTOs(List<User> users);

    // DTO de creación a Dominio
    @Mapping(target = "roles", ignore = true) // Los roles se asignan después
    User toDomain(UserSaveDTO dto);

    // DTO de actualización a Dominio (solo cambia email, enabled, roles)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toDomain(UserUpdateDTO dto);

    // Dominio → DTO para update
    UserUpdateDTO toUpdateDTO(User user);

    // Extra: para mapear roles en listas desde id (útil si lo necesitas)
    default List<Long> mapRolesToIds(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getId).toList();
    }
}