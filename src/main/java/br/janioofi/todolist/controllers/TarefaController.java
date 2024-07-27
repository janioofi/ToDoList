package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.dtos.TarefaRequestDto;
import br.janioofi.todolist.domain.dtos.TarefaResponseDto;
import br.janioofi.todolist.domain.services.TarefaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tarefas")
@Tag(name = "Tarefa", description = "Tarefa API")
public class TarefaController {

    private final TarefaService service;
    private static final String ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<TarefaResponseDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(ID)
    public ResponseEntity<TarefaResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDto> create(@RequestBody TarefaRequestDto tarefaRequestDto, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tarefaRequestDto, response));
    }

    @PutMapping(ID)
    public ResponseEntity<TarefaResponseDto> create(@PathVariable Long id, @RequestBody TarefaRequestDto tarefaRequestDto, HttpServletResponse response){
        return ResponseEntity.ok().body(service.update(id, tarefaRequestDto, response));
    }

    @DeleteMapping(ID)
    public ResponseEntity<TarefaResponseDto> delete(@PathVariable Long id, HttpServletResponse response){
        service.delete(id, response);
        return ResponseEntity.ok().build();
    }
}
