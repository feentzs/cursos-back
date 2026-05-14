package br.com.senai.cursosBackend.curso;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCurso (

        @Size(min = )3
        String nome,
        Periodo periodo
){
}
