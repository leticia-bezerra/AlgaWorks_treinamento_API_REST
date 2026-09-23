package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.Exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    @Transactional
    public Proprietario salvar(Proprietario proprietario) {

        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent();

        if(emailEmUso) {
            throw new NegocioException("Ja existe um proprietario cadastrado com esse email.");
        }
        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Proprietario proprietario) {
        proprietarioRepository.delete(proprietario);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
