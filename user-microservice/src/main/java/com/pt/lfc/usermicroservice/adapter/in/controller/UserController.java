package com.pt.lfc.usermicroservice.adapter.in.controller;

import com.pt.lfc.usermicroservice.application.dto.*;
import com.pt.lfc.usermicroservice.application.port.in.UserApplicationServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final UserApplicationServicePort userService;

    public UserController(UserApplicationServicePort userService) {
        this.userService = userService;
    }

    @PostMapping("/registro")
    public ResultDTO register(@RequestBody @Validated UserSaveDTO userSaveDTO) {
        return userService.register(userSaveDTO);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @Validated UserUpdateDTO updateDTO) {
        UserDTO user = userService.updateUser(id, updateDTO);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Usa Principal para obtener el username autenticado
    @GetMapping("/me")
    public ResponseEntity<UserMeDTO> me(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();
        UserMeDTO me = userService.getMe(principal.getName());
        if (me == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(me);
    }

    @PostMapping("/roles")
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {
        return userService.addRole(roleDTO);
    }

    @PutMapping("/add-role-to-user")
    public UserDTO addRoleToUser(@RequestParam Long userId, @RequestParam Long roleId) {
        return userService.addRoleToUser(userId, roleId);
    }

    @DeleteMapping("/remove-role-to-user")
    public UserDTO removeRoleFromUser(@RequestParam Long userId, @RequestParam Long roleId) {
        return userService.removeRoleFromUser(userId, roleId);
    }
}
