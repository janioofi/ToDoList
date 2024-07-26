package br.janioofi.todolist.domain.dtos;

import br.janioofi.todolist.domain.entities.Tarefa;
import br.janioofi.todolist.domain.entities.Usuario;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static TarefaResponseDto toDto(Tarefa tarefa) {
        return new TarefaResponseDto(
                tarefa.getIdTarefa(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getDataCriacao(),
                tarefa.getDataAtualizacao(),
                tarefa.getUsuario().getIdUsuario());
    }

    public static UsuarioResponseDto toDto(Usuario usuario) {
        Set<TarefaResponseDto> tarefas = usuario.getTarefas().stream()
                .map(Mapper::toDto)
                .collect(Collectors.toSet());
        return new UsuarioResponseDto(usuario.getIdUsuario(), usuario.getUsuario(), usuario.getSenha(), tarefas);
    }
}
