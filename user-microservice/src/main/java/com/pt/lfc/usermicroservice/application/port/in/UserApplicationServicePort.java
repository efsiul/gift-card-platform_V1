package com.pt.lfc.usermicroservice.application.port.in;

import com.pt.lfc.usermicroservice.application.dto.*;
import java.util.List;

public interface UserApplicationServicePort {
    ResultDTO register(UserSaveDTO userSaveDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserUpdateDTO updateDTO);
    void deleteUser(Long id);
    RoleDTO addRole(RoleDTO roleDTO);
    UserDTO addRoleToUser(Long userId, Long roleId);
    UserDTO removeRoleFromUser(Long userId, Long roleId);
    UserMeDTO getMe(String username);
}