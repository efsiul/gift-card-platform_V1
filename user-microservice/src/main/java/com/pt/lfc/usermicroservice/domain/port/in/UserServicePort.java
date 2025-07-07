package com.pt.lfc.usermicroservice.domain.port.in;

import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {
    User registerUser(User user, List<Long> roleIds);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, User user, List<Long> roleIds);
    void deleteUser(Long id);

    Role addRole(Role role);
    User addRoleToUser(Long userId, Long roleId);
    User removeRoleFromUser(Long userId, Long roleId);
}
