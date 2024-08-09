package xyz.sadiulhakim.myresourceserver.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("Hakim", "hakim@gmail.com", 21));
        users.add(new User("Ashik", "ashik@gmail.com", 30));
        users.add(new User("Antor", "antor@gmail.com", 24));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> authenticated(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> data = new HashMap<>();
        data.put("token",jwt.getTokenValue());
        data.put("claims",jwt.getClaims());
        data.put("headers",jwt.getHeaders());

        return ResponseEntity.ok(data);
    }
}
