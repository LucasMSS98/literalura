package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("languages") List<String> lingua,
                         @JsonAlias("download_count") Integer numeroDownload,
                         @JsonAlias("authors") List<DadosAutor> autores) {
}
