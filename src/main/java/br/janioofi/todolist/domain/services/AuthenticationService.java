package br.janioofi.todolist.domain.services;

import br.janioofi.todolist.domain.dtos.LoginResponseDto;
import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.entities.Usuario;
import br.janioofi.todolist.domain.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository repository;
    private final TokenService tokenService;

    public LoginResponseDto login(@Valid UsuarioRequestDto user){
        validaRegistro(user);
        var usuarioSenha = new UsernamePasswordAuthenticationToken(user.usuario(), user.senha());
        var auth = authenticationManager.authenticate(usuarioSenha);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return new LoginResponseDto(token);
    }

    public String register(@Valid UsuarioRequestDto user){
        validaRegistro(user);
        if(this.repository.findByUsuario(user.usuario()) != null){
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o mesmo nome");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.senha());
        Usuario data = new Usuario(user.usuario(), encryptedPassword);
        repository.save(data);
        return "Usuario registrado com sucesso";
    }

    private void validaRegistro(UsuarioRequestDto user){
        if(user.usuario().isEmpty() ||  user.senha().isEmpty()){
            throw new DataIntegrityViolationException("Todos os campos precisam ser preenchidos");
        }
    }
}