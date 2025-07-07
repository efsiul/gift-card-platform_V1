package com.pt.lfc.usermicroservice;

import com.pt.lfc.usermicroservice.domain.model.Role;
import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.service.LoadDataServiceImpl;
import com.pt.lfc.usermicroservice.domain.port.in.UserServicePort;
import com.pt.lfc.usermicroservice.domain.port.out.RoleRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.pt.lfc.usermicroservice")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            UserServicePort userServicePort,
            RoleRepositoryPort roleRepositoryPort,
            LoadDataServiceImpl loadData // O tu puerto si tienes un Port
    ) {
        return args -> {
            List<Role> roleList = roleRepositoryPort.findAll();
            List<User> userList = userServicePort.getAllUsers();
            if (roleList.isEmpty() && userList.isEmpty()) {
                Role adminRole = new Role(null, "ADMIN");
                Role userRole = new Role(null, "USER");
                // Guarda roles
                adminRole = roleRepositoryPort.save(adminRole);
                userRole = roleRepositoryPort.save(userRole);

                User adminUser = User.builder()
                        .enabled(true)
                        .username("admin")
                        .password("pt2025-lfc") // Recuerda: codificar luego
                        .email("luisfelipe.cadavidchica@gmail.com")
                        .roles(List.of(adminRole, userRole))
                        .build();

                userServicePort.registerUser(adminUser, List.of(adminRole.getId(), userRole.getId()));
            }
            loadData.seedMailData("dataSeeder/mailData.json");
        };
    }
}
