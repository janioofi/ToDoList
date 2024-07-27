package br.janioofi.todolist.domain.entities;

import br.janioofi.todolist.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarefa;

    @NotNull
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @Enumerated(EnumType.ORDINAL)
    private Usuario usuario;
}
