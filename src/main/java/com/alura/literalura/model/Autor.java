package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Year anoDeNascimento;
    private Year anoDeFalecimento;
    @Column(unique = true)
    private String nome;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DadosAutor autor) {
        try {
            this.anoDeNascimento = Year.parse(autor.anoDeNascimento());
        }catch (NumberFormatException e){
            this.anoDeNascimento = null;
        }
        try {
            this.anoDeFalecimento = Year.parse(autor.anoDeFalecimento());
        }catch (NumberFormatException e){
            this.anoDeFalecimento = null;
        }
        String[] nomeSobrenome = autor.nome().split(", ");
        if(nomeSobrenome.length == 2){
            this.nome = nomeSobrenome[1].trim() + " " + nomeSobrenome[0].trim();
        }else {
            this.nome = autor.nome();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Year getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Year anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Year getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Year anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(e -> e.setAutor(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return  "Nome do Autor = " + nome +
                ", Ano De Nascimento = " + anoDeNascimento +
                ", Ano De Falecimento = " + anoDeFalecimento;

    }
}
