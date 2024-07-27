package br.janioofi.todolist.domain.services;


import br.janioofi.todolist.domain.dtos.Mapper;
import br.janioofi.todolist.domain.dtos.UsuarioRequestDto;
import br.janioofi.todolist.domain.dtos.UsuarioResponseDto;
import br.janioofi.todolist.domain.entities.Usuario;
import br.janioofi.todolist.domain.exceptions.ResourceNotFoundException;
import br.janioofi.todolist.domain.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private static final String NENHUM_USUARIO = "Nenhum usuário encontrado com ID: ";
    private static final String USARIO_ERRADO = "Apenas o próprio usuário pode executar está ação";

    public List<UsuarioResponseDto> findAll(){
        log.info("Listando todos usuários");
        return repository.findAll().stream().map(Mapper::toDto).toList();
    }

    public UsuarioResponseDto findById(Long id){
        log.info("Buscando usuário com ID: {}", id);
        return repository.findById(id).map(Mapper::toDto).orElseThrow(() -> new ResourceNotFoundException(NENHUM_USUARIO + id));
    }

    public void delete(Long id, HttpServletResponse response){
        Optional<Usuario> usuario = repository.findById(id);
        if(usuario.isEmpty()) throw new ResourceNotFoundException(NENHUM_USUARIO + id);
        validaUsuario(response, id);
        log.info("Deletando usuário com ID: {}", id);
        repository.deleteById(id);
    }

    public UsuarioResponseDto update(@Valid Long id, UsuarioRequestDto user, HttpServletResponse response){
        validaCriacaoEdicao(user, id);
        validaUsuario(response, id);
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.senha());
        Usuario usuario = repository.findById(id).map(recordFound -> {
            recordFound.setUsuario(user.usuario());
            recordFound.setSenha(encryptedPassword);
            return repository.save(recordFound);
        }).orElseThrow(() -> new ResourceNotFoundException(NENHUM_USUARIO + id));
        log.info("Realizando uma atualização pro usuário com ID: {}", id);
        return Mapper.toDto(usuario);
    }

    private void validaCriacaoEdicao(UsuarioRequestDto objDTO, Long id) {
        Usuario obj = repository.findByUsuario(objDTO.usuario());
        if (obj != null && !obj.getIdUsuario().equals(id)) {
            throw new DataIntegrityViolationException("Usuário já existe no sistema!");
        }
    }

    private void validaUsuario(HttpServletResponse response, Long id) {
        Usuario usuarioHeader = repository.findByUsuario(response.getHeader("User"));
        Usuario usuario = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NENHUM_USUARIO + id));
        if(!usuario.getIdUsuario().equals(usuarioHeader.getIdUsuario())) throw new ResourceNotFoundException(USARIO_ERRADO);
    }
}
