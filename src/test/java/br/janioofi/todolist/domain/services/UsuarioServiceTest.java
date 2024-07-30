package br.janioofi.todolist.domain.services;

import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.dtos.UsuarioResponseDto;
import br.janioofi.todolist.domain.entities.Usuario;
import br.janioofi.todolist.domain.exceptions.ResourceNotFoundException;
import br.janioofi.todolist.domain.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private HttpServletResponse response;

    private static final Long ID = 1L;
    private static final String USUARIO = "usuarioTeste";
    private static final String SENHA = "senhaTeste";
    private static final String NENHUM_USUARIO = "Nenhum usuário encontrado com ID: ";
    private static final String USARIO_ERRADO = "Apenas o próprio usuário pode executar está ação";

    private Usuario usuario;
    private UsuarioRequestDto usuarioRequestDto;
    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsuario();
        when(response.getHeader("User")).thenReturn(USUARIO);
    }

    @Test
    void whenFindAllThenReturnListOfUsuarioResponseDto() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDto> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UsuarioResponseDto.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).idUsuario());
        assertEquals(USUARIO, response.get(0).usuario());
        assertEquals(SENHA, response.get(0).senha());
    }

    @Test
    void whenFindByIdThenReturnUsuarioResponseDto() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(usuario));

        UsuarioResponseDto response = service.findById(ID);

        assertNotNull(response);
        assertEquals(UsuarioResponseDto.class, response.getClass());
        assertEquals(ID, response.idUsuario());
        assertEquals(USUARIO, response.usuario());
        assertEquals(SENHA, response.senha());
    }

    @Test
    void whenFindByIdThenThrowResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(ID));

        assertEquals(NENHUM_USUARIO + ID, exception.getMessage());
    }

    @Test
    void whenUpdateThenReturnUsuarioResponseDto() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(repository.findByUsuario(anyString())).thenReturn(usuario);

        UsuarioResponseDto response = service.update(ID, usuarioRequestDto, this.response);

        assertNotNull(response);
        assertEquals(UsuarioResponseDto.class, response.getClass());
        assertEquals(ID, response.idUsuario());
        assertEquals(USUARIO, response.usuario());
    }

    @Test
    void whenDeleteThenThrowResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(ID, response));

        assertEquals(NENHUM_USUARIO + ID, exception.getMessage());
    }

    private void startUsuario() {
        usuario = new Usuario(ID, USUARIO, SENHA, new HashSet<>());
        usuarioRequestDto = new UsuarioRequestDto(USUARIO, SENHA);
        usuarioResponseDto = new UsuarioResponseDto(ID, USUARIO, SENHA, new HashSet<>());
    }
}
