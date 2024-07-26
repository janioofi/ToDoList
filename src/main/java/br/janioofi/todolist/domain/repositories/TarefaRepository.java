package br.janioofi.todolist.domain.repositories;

import br.janioofi.todolist.domain.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
