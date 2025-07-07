package com.pt.lfc.usermicroservice.domain.service;

import com.pt.lfc.usermicroservice.domain.model.Role;
import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.model.Mail;
import com.pt.lfc.usermicroservice.domain.port.in.UserServicePort;
import com.pt.lfc.usermicroservice.domain.port.out.PasswordEncoderPort;
import com.pt.lfc.usermicroservice.domain.port.out.UserRepositoryPort;
import com.pt.lfc.usermicroservice.domain.port.out.RoleRepositoryPort;
import com.pt.lfc.usermicroservice.domain.port.out.MailRepositoryPort;
import com.pt.lfc.usermicroservice.domain.port.in.MailServicePort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserServicePort {

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final MailRepositoryPort mailRepository;
    private final MailServicePort mailService;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserServiceImpl(
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            MailRepositoryPort mailRepository,
            MailServicePort mailService,
            PasswordEncoderPort passwordEncoderPort
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mailRepository = mailRepository;
        this.mailService = mailService;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User registerUser(User user, List<Long> roleIds) {
        // Validar correo y username únicos
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con este correo.");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con este nombre de usuario.");
        }

        // Asignar roles
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId));
            roles.add(role);
        }
        user.setRoles(roles);

        // Generar contraseña temporal
        String tempPassword = generateRandomPassword(16);
        user.setPassword(passwordEncoderPort.encode(tempPassword));
        user.setEnabled(true);

        // Guardar usuario
        User savedUser = userRepository.save(user);

        // Enviar correo (opcional, si tienes plantilla)
        Optional<Mail> mailTemplate = mailRepository.findByIdMail(1L);
        if (mailTemplate.isPresent()) {
            Mail template = mailTemplate.get();
            String subject = template.getDescription();
            String body = String.format(template.getBody(), user.getUsername(), tempPassword);
            mailService.sendMail(user.getEmail(), subject, body);
        }

        return savedUser;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User updated, List<Long> roleIds) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        if (updated.getEmail() != null)
            user.setEmail(updated.getEmail());
        if (updated.getEnabled() != null)
            user.setEnabled(updated.getEnabled());

        if (roleIds != null && !roleIds.isEmpty()) {
            List<Role> newRoles = new ArrayList<>();
            for (Long roleId : roleIds) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId));
                newRoles.add(role);
            }
            user.setRoles(newRoles);
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId));
        List<Role> roles = new ArrayList<>(user.getRoles());
        if (!roles.contains(role)) {
            roles.add(role);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleId));
        List<Role> roles = new ArrayList<>(user.getRoles());
        roles.remove(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // ---- Método auxiliar para generar password temporal ----
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
