package br.janioofi.todolist.domain.services;

import br.janioofi.todolist.domain.dtos.Mapper;
import br.janioofi.todolist.domain.dtos.TarefaRequestDto;
import br.janioofi.todolist.domain.dtos.TarefaResponseDto;
import br.janioofi.todolist.domain.entities.Tarefa;
import br.janioofi.todolist.domain.entities.Usuario;
import br.janioofi.todolist.domain.exceptions.ResourceNotFoundException;
import br.janioofi.todolist.domain.repositories.TarefaRepository;
import br.janioofi.todolist.domain.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private static final String NENHUMA_TAREFA = "Nenhuma tarefa encontrada com ID: ";
    private static final String USARIO_ERRADO = "Tarefa de outro usuário";

    public List<TarefaResponseDto> findAll(){
        log.info("Listando todas as tarefas");
        return repository.findAll().stream().map(Mapper::toDto).toList();
    }

    public TarefaResponseDto findById(Long id){
        log.info("Buscando tarefa com ID: {}", id);
        return repository.findById(id).map(Mapper::toDto).orElseThrow(() -> new ResourceNotFoundException(NENHUMA_TAREFA + id));
    }

    public TarefaResponseDto create(TarefaRequestDto tarefaRequestDto, HttpServletResponse response) {
        validaRegistro(tarefaRequestDto);
        Tarefa tarefa = new Tarefa();
        String user = response.getHeader("User");
        Usuario usuario = usuarioRepository.findByUsuario(user);
        tarefa.setDescricao(tarefaRequestDto.descricao());
        tarefa.setStatus(tarefaRequestDto.status());
        tarefa.setDataCriacao(LocalDateTime.now().minusHours(3)); //Time zone brasileiro para servidor do deploy
        tarefa.setUsuario(usuario);
        log.info("Criando nova tarefa");
        return Mapper.toDto(repository.save(tarefa));
    }

    public TarefaResponseDto update(Long id, TarefaRequestDto tarefaRequestDto, HttpServletResponse response) {
        validaRegistro(tarefaRequestDto);
        Tarefa tarefa = repository.findById(id).map(data -> {
            validaUsuario(response, data);
            data.setDescricao(tarefaRequestDto.descricao());
            data.setStatus(tarefaRequestDto.status());
            data.setDataAtualizacao(LocalDateTime.now().minusHours(3));//Time zone brasileiro para servidor do deploy
            return repository.save(data);
        }).orElseThrow(() -> new ResourceNotFoundException(NENHUMA_TAREFA + id));
        log.info("Realizando uma atualização para tarefa com ID: {}", id);
        return Mapper.toDto(tarefa);
    }

    public void delete(Long id, HttpServletResponse response){
        Tarefa tarefa = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NENHUMA_TAREFA + id));
        validaUsuario(response, tarefa);
        log.info("Deletando tarefa com ID: {}", id);
        repository.deleteById(id);
    }

    private void validaUsuario(HttpServletResponse response, Tarefa tarefa) {
        Usuario usuario = usuarioRepository.findByUsuario(response.getHeader("User"));
        if(!usuario.getIdUsuario().equals(tarefa.getUsuario().getIdUsuario())) throw new ResourceNotFoundException(USARIO_ERRADO);
    }

    private void validaRegistro(TarefaRequestDto tarefa){
        if(tarefa.descricao().isEmpty()) {
            throw new DataIntegrityViolationException("Todos os campos precisam ser preenchidos");
        }
    }
}
