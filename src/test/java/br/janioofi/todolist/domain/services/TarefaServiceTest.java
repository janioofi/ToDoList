package br.janioofi.todolist.domain.services;

import br.janioofi.todolist.domain.dtos.Mapper;
import br.janioofi.todolist.domain.dtos.TarefaRequestDto;
import br.janioofi.todolist.domain.dtos.TarefaResponseDto;
import br.janioofi.todolist.domain.entities.Tarefa;
import br.janioofi.todolist.domain.entities.Usuario;
import br.janioofi.todolist.domain.enums.Status;
import br.janioofi.todolist.domain.exceptions.ResourceNotFoundException;
import br.janioofi.todolist.domain.repositories.TarefaRepository;
import br.janioofi.todolist.domain.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TarefaServiceTest {

    @InjectMocks
    private TarefaService service;

    @Mock
    private TarefaRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HttpServletResponse response;

    private static final Long ID = 1L;
    private static final String DESCRICAO = "Tarefa Teste";
    private static final LocalDateTime DATA_CRIACAO = LocalDateTime.now();
    private static final Status STATUS = Status.PENDENTE;
    private static final String USUARIO_TESTE = "usuarioTeste";

    private Tarefa tarefa;
    private TarefaRequestDto tarefaRequestDto;
    private TarefaResponseDto tarefaResponseDto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTarefa();
    }

    @Test
    void whenFindAllThenReturnListOfTarefaResponseDto() {
        when(repository.findAll()).thenReturn(List.of(tarefa));

        List<TarefaResponseDto> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(TarefaResponseDto.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).idTarefa());
        assertEquals(DESCRICAO, response.get(0).descricao());
    }

    @Test
    void whenFindByIdThenReturnTarefaResponseDto() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tarefa));

        TarefaResponseDto response = service.findById(ID);

        assertNotNull(response);
        assertEquals(TarefaResponseDto.class, response.getClass());
        assertEquals(ID, response.idTarefa());
        assertEquals(DESCRICAO, response.descricao());
    }

    @Test
    void whenFindByIdThenThrowResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(ID));
        assertEquals("Nenhuma tarefa encontrada com ID: " + ID, exception.getMessage());
    }

    @Test
    void whenCreateThenReturnTarefaResponseDto() {
        when(response.getHeader("User")).thenReturn(USUARIO_TESTE);
        when(usuarioRepository.findByUsuario(anyString())).thenReturn(usuario);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponseDto response = service.create(tarefaRequestDto, this.response);

        assertNotNull(response);
        assertEquals(TarefaResponseDto.class, response.getClass());
        assertEquals(ID, response.idTarefa());
        assertEquals(DESCRICAO, response.descricao());
    }

    @Test
    void whenUpdateThenReturnTarefaResponseDto() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(response.getHeader("User")).thenReturn(USUARIO_TESTE);
        when(usuarioRepository.findByUsuario(anyString())).thenReturn(usuario);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponseDto response = service.update(ID, tarefaRequestDto, this.response);

        assertNotNull(response);
        assertEquals(TarefaResponseDto.class, response.getClass());
        assertEquals(ID, response.idTarefa());
        assertEquals(DESCRICAO, response.descricao());
    }

    @Test
    void whenUpdateThenThrowResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.update(ID, tarefaRequestDto, response));
        assertEquals("Nenhuma tarefa encontrada com ID: " + ID, exception.getMessage());
    }

    @Test
    void whenDeleteThenSuccess() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(response.getHeader("User")).thenReturn(USUARIO_TESTE);
        when(usuarioRepository.findByUsuario(anyString())).thenReturn(usuario);
        doNothing().when(repository).deleteById(anyLong());

        service.delete(ID, response);

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(ID, response));
        assertEquals("Nenhuma tarefa encontrada com ID: " + ID, exception.getMessage());
    }

    private void startTarefa() {
        usuario = new Usuario(ID, USUARIO_TESTE, "senhaTeste", new HashSet<>());

        tarefa = new Tarefa();
        tarefa.setIdTarefa(ID);
        tarefa.setDescricao(DESCRICAO);
        tarefa.setStatus(STATUS);
        tarefa.setDataCriacao(DATA_CRIACAO);
        tarefa.setUsuario(usuario);

        tarefaRequestDto = new TarefaRequestDto(DESCRICAO, STATUS);

        tarefaResponseDto = Mapper.toDto(tarefa);
    }
}
