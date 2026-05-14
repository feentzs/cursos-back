package br.com.senai.cursosBackend.curso;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Periodo {
    MATUTINO,
    VESPERTINO,
    NOTURNO,
    INTEGRAL;


    @JsonCreator
    public static Periodo fromString(String value) {
        if (value == null) return null;

        return Periodo.valueOf(value.toUpperCase().trim());
    }
}
