package br.com.senai.cursosBackend.controllers;

import br.com.senai.cursosBackend.curso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos no sistema")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Operation(summary = "Listar todos os cursos", description = "Retorna uma lista de todos os cursos que estão ativos no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cursos listados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<DadosListagemCurso>> listarCursos(){
        List<Curso> cursos = repository.findAllByAtivoTrue();
        List<DadosListagemCurso> cursosDTO = cursos.stream().map(DadosListagemCurso::new).toList();

        return ResponseEntity.ok(cursosDTO);
    }

    @Operation(summary = "Buscar curso por ID", description = "Retorna os detalhes de um curso específico através do seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesCurso> listarCursosPorId(
            @Parameter(description = "ID do curso a ser buscado", example = "1")
            @PathVariable Long id){
        Optional<Curso> curso = Optional.of(repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado esse curso")));
        Curso cursoTrue = curso.get();

        DadosDetalhesCurso cursoDTO = new DadosDetalhesCurso(cursoTrue);

        return ResponseEntity.ok().body(cursoDTO);
    }

    @Operation(summary = "Cadastrar um novo curso", description = "Cria um novo curso no sistema. O nome do curso deve ser único entre os cursos ativos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: Já existe um curso com o mesmo nome", content = @Content)
    })
    @PostMapping
    @Transactional
    protected ResponseEntity<HttpStatus> cadastrarCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados necessários para cadastrar um curso")
            @RequestBody @Valid DadosCadastroCurso dados){

        if(repository.existsByNomeAndPeriodoAndAtivoTrue(dados.nome(), dados.periodo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nome já cadastrado no sistema");
        }
        Curso curso = new Curso(dados);

        repository.save(curso);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Atualizar um curso existente", description = "Atualiza as informações de um curso já cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "ID do curso não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito: Nome de curso já existente", content = @Content)
    })
    @PutMapping("")
    @Transactional
    public ResponseEntity<DadosDetalhesCurso> atualizarCurso(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados para atualização do curso")
            @RequestBody @Valid DadosAtualizarCurso dados){
        var cursoAtualizado = repository.getReferenceById(dados.id());

        if(repository.existsByNomeAndPeriodoAndAtivoTrue(dados.nome(), dados.periodo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Curso já cadastrado no período selecionado");
        }

        cursoAtualizado.atualizarCurso(dados);
        return ResponseEntity.accepted().body(new DadosDetalhesCurso(cursoAtualizado));
    }

    @Operation(summary = "Deletar um curso (Exclusão lógica)", description = "Marca o curso como inativo no sistema através do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID do curso não encontrado", content = @Content)
    })
    @DeleteMapping("{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCurso(
            @Parameter(description = "ID do curso a ser desativado", example = "1")
            @PathVariable Long id){
        var curso = repository.getReferenceById(id);

        curso.excluirCurso();
    }

}