package br.com.senai.cursosBackend.curso;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCurso (

        @NotNull
        Long id,

        @Size(min = 3)
        String nome,


        Periodo periodo
){
}
