package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.dtos.TarefaRequestDto;
import br.janioofi.todolist.domain.dtos.TarefaResponseDto;
import br.janioofi.todolist.domain.enums.Status;
import br.janioofi.todolist.domain.services.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TarefaControllerTest {

    private static final Long ID = 1L;
    private static final String DESCRICAO = "Tarefa Teste";
    private static final Status STATUS = Status.PENDENTE;
    private static final LocalDateTime DATA_CRIACAO = LocalDateTime.now();
    private static final LocalDateTime DATA_ATUALIZACAO = LocalDateTime.now();
    private static final Long ID_USUARIO = 1L;

    private TarefaResponseDto tarefaResponseDto;

    @InjectMocks
    private TarefaController controller;

    @Mock
    private TarefaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTarefa();
        controller = new TarefaController(service);
    }

    @Test
    void whenFindAllThenReturnListOfTarefa() {
        when(service.findAll()).thenReturn(List.of(tarefaResponseDto));

        ResponseEntity<List<TarefaResponseDto>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TarefaResponseDto.class, response.getBody().get(0).getClass());
        assertEquals(ID, response.getBody().get(0).idTarefa());
        assertEquals(DESCRICAO, response.getBody().get(0).descricao());
        assertEquals(STATUS, response.getBody().get(0).status());
        assertEquals(DATA_CRIACAO, response.getBody().get(0).dataCriacao());
        assertEquals(DATA_ATUALIZACAO, response.getBody().get(0).dataAtualizacao());
        assertEquals(ID_USUARIO, response.getBody().get(0).idUsuario());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyLong())).thenReturn(tarefaResponseDto);

        ResponseEntity<TarefaResponseDto> response = controller.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TarefaResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().idTarefa());
        assertEquals(DESCRICAO, response.getBody().descricao());
        assertEquals(STATUS, response.getBody().status());
        assertEquals(DATA_CRIACAO, response.getBody().dataCriacao());
        assertEquals(DATA_ATUALIZACAO, response.getBody().dataAtualizacao());
        assertEquals(ID_USUARIO, response.getBody().idUsuario());
    }

    @Test
    void whenCreateThenReturnCreated() {
        TarefaRequestDto tarefaRequestDto = new TarefaRequestDto(DESCRICAO, STATUS);
        when(service.create(any(), any())).thenReturn(tarefaResponseDto);

        ResponseEntity<TarefaResponseDto> response = controller.create(tarefaRequestDto, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TarefaResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().idTarefa());
        assertEquals(DESCRICAO, response.getBody().descricao());
        assertEquals(STATUS, response.getBody().status());
        assertEquals(DATA_CRIACAO, response.getBody().dataCriacao());
        assertEquals(DATA_ATUALIZACAO, response.getBody().dataAtualizacao());
        assertEquals(ID_USUARIO, response.getBody().idUsuario());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        TarefaRequestDto tarefaRequestDto = new TarefaRequestDto(DESCRICAO, STATUS);
        when(service.update(anyLong(), any(), any())).thenReturn(tarefaResponseDto);

        ResponseEntity<TarefaResponseDto> response = controller.update(ID, tarefaRequestDto, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TarefaResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().idTarefa());
        assertEquals(DESCRICAO, response.getBody().descricao());
        assertEquals(STATUS, response.getBody().status());
        assertEquals(DATA_CRIACAO, response.getBody().dataCriacao());
        assertEquals(DATA_ATUALIZACAO, response.getBody().dataAtualizacao());
        assertEquals(ID_USUARIO, response.getBody().idUsuario());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyLong(), any());

        ResponseEntity<Void> response = controller.delete(ID, null);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(service, times(1)).delete(anyLong(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void startTarefa() {
        tarefaResponseDto = new TarefaResponseDto(ID, DESCRICAO, STATUS, DATA_CRIACAO, DATA_ATUALIZACAO, ID_USUARIO);
    }
}
