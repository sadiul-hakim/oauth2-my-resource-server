package xyz.sadiulhakim.myresourceserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static List<User> users = new ArrayList<>();

    @Autowired
    private Environment env;

    static {
        users.add(new User("Hakim", "hakim@gmail.com", 21));
        users.add(new User("Ashik", "ashik@gmail.com", 30));
        users.add(new User("Antor", "antor@gmail.com", 24));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok("Working on port: " + env.getProperty("local.server.port"));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> data = new HashMap<>();
        data.put("token", jwt.getTokenValue());
        data.put("claims", jwt.getClaims());
        data.put("headers", jwt.getHeaders());

        return ResponseEntity.ok(data);
    }

    @PreAuthorize("hasRole('developer')")
    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        User filteredUser = users.stream().filter(user -> user.name().equalsIgnoreCase(name)).findFirst().orElse(null);
        return ResponseEntity.ok(filteredUser);
    }

    @PostAuthorize("hasRole('developer')")
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        System.out.println("I am in");
        users = users.stream().filter(user -> !user.name().equalsIgnoreCase(name)).toList();
        return ResponseEntity.ok("OK");
    }
}
