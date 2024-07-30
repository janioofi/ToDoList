package br.janioofi.todolist.controllers;

import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.dtos.UsuarioResponseDto;
import br.janioofi.todolist.domain.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Mock
    private UsuarioService service;

    @Mock
    private HttpServletResponse response;

    private static final Long ID = 1L;
    private static final String USUARIO = "usuarioTeste";
    private static final String SENHA = "senhaTeste";

    private UsuarioRequestDto usuarioRequestDto;
    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsuario();
    }

    @Test
    void whenFindAllThenReturnListOfUsuarioResponseDto() {
        when(service.findAll()).thenReturn(List.of(usuarioResponseDto));

        ResponseEntity<List<UsuarioResponseDto>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsuarioResponseDto.class, response.getBody().get(0).getClass());
        assertEquals(ID, response.getBody().get(0).idUsuario());
        assertEquals(USUARIO, response.getBody().get(0).usuario());
        assertEquals(SENHA, response.getBody().get(0).senha());
    }

    @Test
    void whenFindByIdThenReturnUsuarioResponseDto() {
        when(service.findById(anyLong())).thenReturn(usuarioResponseDto);

        ResponseEntity<UsuarioResponseDto> response = controller.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsuarioResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().idUsuario());
        assertEquals(USUARIO, response.getBody().usuario());
        assertEquals(SENHA, response.getBody().senha());
    }

    @Test
    void whenUpdateThenReturnUsuarioResponseDto() {
        when(service.update(anyLong(), any(UsuarioRequestDto.class), any(HttpServletResponse.class))).thenReturn(usuarioResponseDto);

        ResponseEntity<UsuarioResponseDto> response = controller.update(usuarioRequestDto, ID, this.response);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsuarioResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().idUsuario());
        assertEquals(USUARIO, response.getBody().usuario());
        assertEquals(SENHA, response.getBody().senha());
    }

    @Test
    void whenDeleteThenReturnNoContent() {
        doNothing().when(service).delete(anyLong(), any(HttpServletResponse.class));

        ResponseEntity<Void> response = controller.delete(ID, this.response);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).delete(anyLong(), any(HttpServletResponse.class));
    }

    private void startUsuario() {
        usuarioRequestDto = new UsuarioRequestDto(USUARIO, SENHA);
        usuarioResponseDto = new UsuarioResponseDto(ID, USUARIO, SENHA, null);
    }
}
