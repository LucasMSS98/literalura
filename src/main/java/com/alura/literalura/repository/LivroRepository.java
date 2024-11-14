package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {


    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query("Select a from Autor a")
    List<Autor> listarTodosAutores();

    @Query("Select a from Autor a WHERE a.anoDeNascimento <= :anoAutor AND a.anoDeFalecimento >= :anoAutor")
    List<Autor> listarAutoresVivosPeloAno(Year anoAutor);

    List<Livro> findByLinguaContaining(String idioma);

    List<Livro> findTop10ByOrderByNumeroDownloadDesc();

    @Query("Select l from Livro l JOIN l.autor a WHERE a.nome ILIKE %:nomeAutor%")
    List<Livro> buscarAutorPeloNome(String nomeAutor);
}
