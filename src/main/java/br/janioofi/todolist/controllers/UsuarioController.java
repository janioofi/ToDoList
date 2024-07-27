package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.dtos.UsuarioResponseDto;
import br.janioofi.todolist.domain.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuário", description = "Usuario API")
public class UsuarioController {

    private final UsuarioService service;
    private static final String ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(ID)
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(ID)
    public ResponseEntity<UsuarioResponseDto> update(@RequestBody @Valid UsuarioRequestDto usuario, @PathVariable Long id, HttpServletResponse response){
        return ResponseEntity.ok().body(service.update(id, usuario, response));
    }

    @DeleteMapping(ID)
    public ResponseEntity<UsuarioResponseDto> delete(@PathVariable Long id, HttpServletResponse response){
        service.delete(id, response);
        return ResponseEntity.ok().build();
    }
}
