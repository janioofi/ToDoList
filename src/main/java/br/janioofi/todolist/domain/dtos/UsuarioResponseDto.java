package br.janioofi.todolist.domain.dtos;

import java.util.Set;

public record UsuarioResponseDto(
        Long idUsuario,
        String usuario,
        String senha,
        Set<TarefaResponseDto> tarefas
) {
}
