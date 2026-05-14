package br.com.senai.cursosBackend.curso;

import io.swagger.v3.oas.annotations.media.Schema;

public record DadosDetalhesCurso(
        @Schema(name = "id", description = "ID do curso", example = "1")
        Long id,
        @Schema(name = "nome", description = "Nome do curso", example = "CursoExemplo")
        String nome,
        @Schema(name = "periodo", description = "Periodo do curso", example = "Integral")
        Periodo periodo
) {
}
