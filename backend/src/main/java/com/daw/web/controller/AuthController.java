package com.daw.web.controller;

import com.daw.services.dto.LoginRequest;
import com.daw.services.dto.LoginResponse;
import com.daw.JwtUtils; // Ajusta el import según tu paquete real
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/usuario/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Autentica el usuario con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getNombre(),
                    loginRequest.getContrasenia()
                )
            );

            // 2. Genera el JWT con JwtUtils
            String token = jwtUtils.generateToken(authentication);

            // 3. Prepara la respuesta con el token y el nombre de usuario
            LoginResponse response = new LoginResponse();
            response.setNombre(loginRequest.getNombre());
            response.setToken(token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            // Credenciales incorrectas
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
    }
}
