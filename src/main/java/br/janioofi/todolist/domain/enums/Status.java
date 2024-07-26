package br.janioofi.todolist.domain.enums;

public enum Status {
    PENDENTE(0, "Pendente"),
    CONCLUIDO(1, "Concluido"),
    CANCELADO(2, "Cancelado");

    private final Integer codigo;
    private final String descricao;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}