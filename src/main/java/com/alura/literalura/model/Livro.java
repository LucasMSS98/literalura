package com.alura.literalura.model;


import com.alura.literalura.repository.AutorRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "livros", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "lingua"})
})//definindo o titulo e a lingua como unicos permite adicionar o mesmo livro em outro idioma
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;
    private String titulo;
    private String lingua;
    private Integer numeroDownload;
    @ManyToOne
    private Autor autor;

    public Livro(){}

    //O repositorio do autor e responsavel por verificar se ja possui um autor cadastrado no banco
    //se nao existir ele salva um novo autor
    public Livro(DadosLivro dadosLivro, AutorRepository autorRepositorio) {
        this.titulo = dadosLivro.titulo();
        this.lingua = dadosLivro.lingua().getFirst();
        this.numeroDownload = dadosLivro.numeroDownload();
        if (!dadosLivro.autores().isEmpty()){
            Optional<Autor> autorOptional;
            String autorNome= dadosLivro.autores().getFirst().nome();
            //Verifica se o nome do autor recebido esta formatado como "sobrenome, nome",
            // e vira eles para consulta no banco
            if(autorNome.contains(", ")) {
                String[] nomeSobrenome = autorNome.split(", ");
                String nomeFormatado = nomeSobrenome[1] + " " + nomeSobrenome[0];
                autorOptional = autorRepositorio.findByNome(nomeFormatado);
            }else {
                autorOptional = autorRepositorio.findByNome(autorNome);
            }
            if (autorOptional.isPresent()){
                this.autor = autorOptional.get();
            } else {
                System.out.println("chegou aqui");
                Autor autor = new Autor(dadosLivro.autores().getFirst());
                autorRepositorio.save(autor);
                this.autor = autor;
            }
        }
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public Integer getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Integer numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "Titulo = '" + titulo + '\'' +
                ", Lingua = " + lingua +
                ", Numero De Downloads = " + numeroDownload +
                " [" + autor + " ]";
    }
}
