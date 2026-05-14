package br.com.senai.cursosBackend.controllers;


import br.com.senai.cursosBackend.curso.Curso;
import br.com.senai.cursosBackend.curso.CursoRepository;
import br.com.senai.cursosBackend.curso.DadosListagemCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    CursoRepository repository;

    @GetMapping
    private List<DadosListagemCurso> listarCursos(){
        List<Curso> cursos = repository.findAllByAtivoTrue();
        return cursos.stream().map(DadosListagemCurso::new).toList();

    }
}