package br.com.senai.cursosBackend.controllers;


import br.com.senai.cursosBackend.curso.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @GetMapping
    public ResponseEntity<List<DadosListagemCurso>> listarCursos(){
        List<Curso> cursos = repository.findAllByAtivoTrue();
        List<DadosListagemCurso> cursosDTO = cursos.stream().map(DadosListagemCurso::new).toList();

        return ResponseEntity.ok(cursosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesCurso> listarCursosPorId(@PathVariable Long id){
        Optional<Curso> curso = Optional.of(repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado esse curso")));
        Curso cursoTrue = curso.get();

        DadosDetalhesCurso cursoDTO = new DadosDetalhesCurso(cursoTrue);



        return ResponseEntity.ok().body(cursoDTO);
    }
    @PostMapping
    @Transactional
    protected ResponseEntity<HttpStatus> cadastrarCurso(@RequestBody @Valid DadosCadastroCurso dados){

        if(repository.existsByNomeAndAtivoTrue(dados.nome())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado no sistema");
        }
        Curso curso = new Curso(dados);

        repository.save(curso);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("")
    @Transactional
    public  ResponseEntity<DadosDetalhesCurso> atualizarCurso(@RequestBody @Valid DadosAtualizarCurso dados){
        var cursoAtualizado = repository.getReferenceById(dados.id());

        if(repository.existsByNomeAndAtivoTrue(dados.nome())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado no sistema");
        }

        cursoAtualizado.atualizarCurso(dados);
        return ResponseEntity.accepted().body(new DadosDetalhesCurso(cursoAtualizado));
    }

    @DeleteMapping("{id}")
    @Transactional
    public void deletarCurso(@PathVariable Long id){
        var curso = repository.getReferenceById(id);

        curso.excluirCurso();
    }

}