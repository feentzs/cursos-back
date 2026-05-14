package br.com.senai.cursosBackend.curso;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroCurso(

        @Schema(name = "nome", description = "Nome do curso", example = "CursoExemplo")
        @NotBlank
        @Size(min = 3, message = "Deve ter no mínimo 3 letras")
        String nome,

        @NotNull
        @Schema(name = "periodo", description = "Periodo do curso", example = "Noturno")
        Periodo periodo
) {
}
