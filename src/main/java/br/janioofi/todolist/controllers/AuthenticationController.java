package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.dtos.LoginResponseDto;
import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UsuarioRequestDto user){
        return ResponseEntity.ok().body(service.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioRequestDto user){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(user));
    }
}