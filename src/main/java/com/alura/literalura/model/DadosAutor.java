package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias("birth_year") String anoDeNascimento,
                         @JsonAlias("death_year")String anoDeFalecimento,
                         @JsonAlias("name")String nome) {
}
