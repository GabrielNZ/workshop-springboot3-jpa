package com.educandoweb.course.resources;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.dtos.AuthenticationDTO;
import com.educandoweb.course.entities.dtos.RegisterDTO;
import com.educandoweb.course.infra.security.TokenService;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO user) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.name(), user.password()));
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if (this.userRepository.findByName(data.name()) != null) return ResponseEntity.ok().build();

        User user = new User(data.name(), data.email(), data.phone(), new BCryptPasswordEncoder().encode(data.password()), data.role());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
