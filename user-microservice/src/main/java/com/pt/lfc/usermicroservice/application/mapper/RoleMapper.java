package com.pt.lfc.usermicroservice.application.mapper;

import com.pt.lfc.usermicroservice.domain.model.Role;
import com.pt.lfc.usermicroservice.application.dto.RoleDTO;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role role);

    Role toDomain(RoleDTO dto);

    List<RoleDTO> toDTOs(List<Role> roles);

    List<Role> toDomainList(List<RoleDTO> dtos);
}