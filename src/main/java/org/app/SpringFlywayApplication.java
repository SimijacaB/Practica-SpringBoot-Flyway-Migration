package org.app;

import org.app.entity.User;
import org.app.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class SpringFlywayApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringFlywayApplication.class, args);
    }

    private final UserRepository userRepository;

    public SpringFlywayApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

}
