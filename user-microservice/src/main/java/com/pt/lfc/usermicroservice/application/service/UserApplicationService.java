package com.pt.lfc.usermicroservice.application.service;

import com.pt.lfc.usermicroservice.application.dto.*;
import com.pt.lfc.usermicroservice.application.mapper.UserMapper;
import com.pt.lfc.usermicroservice.application.mapper.RoleMapper;
import com.pt.lfc.usermicroservice.application.port.in.UserApplicationServicePort;
import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.model.Role;
import com.pt.lfc.usermicroservice.domain.port.in.UserServicePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationService implements UserApplicationServicePort {

    private final UserServicePort userServicePort;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    public UserApplicationService(UserServicePort userServicePort, UserMapper userMapper, RoleMapper roleMapper) {
        this.userServicePort = userServicePort;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    public ResultDTO register(UserSaveDTO userSaveDTO) {
        User userDomain = userMapper.toDomain(userSaveDTO);
        User userSaved = userServicePort.registerUser(userDomain, userSaveDTO.getRoles());
        UserDTO userDTO = userMapper.toDTO(userSaved);
        return new ResultDTO(userDTO);
    }

    public List<UserDTO> getAllUsers() {
        return userServicePort.getAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOpt = userServicePort.getUserById(id);
        return userOpt.map(userMapper::toDTO).orElse(null);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO updateDTO) {
        User userToUpdate = userMapper.toDomain(updateDTO);
        List<Long> roleIds = updateDTO.getRoles();
        User updated = userServicePort.updateUser(id, userToUpdate, roleIds);
        return userMapper.toDTO(updated);
    }

    public void deleteUser(Long id) {
        userServicePort.deleteUser(id);
    }

    public RoleDTO addRole(RoleDTO roleDTO) {
        Role role = roleMapper.toDomain(roleDTO);
        Role saved = userServicePort.addRole(role);
        return roleMapper.toDTO(saved);
    }

    public UserDTO addRoleToUser(Long userId, Long roleId) {
        User user = userServicePort.addRoleToUser(userId, roleId);
        return userMapper.toDTO(user);
    }

    public UserDTO removeRoleFromUser(Long userId, Long roleId) {
        User user = userServicePort.removeRoleFromUser(userId, roleId);
        return userMapper.toDTO(user);
    }

    public UserMeDTO getMe(String username) {
        Optional<User> userOpt = userServicePort.getUserByUsername(username);
        return userOpt.map(userMapper::toMeDTO).orElse(null);
    }
}
