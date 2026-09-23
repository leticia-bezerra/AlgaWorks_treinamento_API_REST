package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final RegistroProprietarioService registroProprietarioService;
    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioController(RegistroProprietarioService registroProprietarioService, ProprietarioRepository proprietarioRepository) {
        this.registroProprietarioService = registroProprietarioService;
        this.proprietarioRepository = proprietarioRepository;
    }

    @GetMapping("/")
    public List<Proprietario> listar(){
       return proprietarioRepository.findAll();
    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId) {
        return proprietarioRepository.findById(proprietarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario) {
        return proprietarioRepository.save(proprietario);
        //return proprietarioRepository.save(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId,
                                                  @Valid
                                                  @RequestBody Proprietario proprietario) {

        if(!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);
        return ResponseEntity.ok(proprietarioAtualizado);

    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> remover(@PathVariable Long proprietarioId) {

        if(!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        registroProprietarioService.excluir(proprietarioRepository.findById(proprietarioId).get());
        return ResponseEntity.noContent().build();

    }

}
