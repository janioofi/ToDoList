package br.janioofi.todolist.domain.dtos;

import br.janioofi.todolist.domain.enums.Status;

public record TarefaRequestDto(
        String descricao,
        Status status
) {
}
