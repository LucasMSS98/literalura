package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosBusca;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverterDados;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Year;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    ConsumoApi consumoApi = new ConsumoApi();

    ConverterDados converterDados = new ConverterDados();

    Scanner leitor = new Scanner(System.in);

    private final LivroRepository livroRepositorio;
    private final AutorRepository autorRepositorio;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void Menu() {
        var opcao = -1;
        String menu = """
                    Digite o numero da opção desejada
                    
                    1) Cadastrar um livro
                    2) Buscar um livro pelo título
                    3) Listar todos os livros
                    4) Listar todos os autores
                    5) Listar autores vivos em determinado ano
                    6) Listar livros por idiomas
                    7) Exibir total de downloads
                    8) Listar o top 10 livros mais baixados
                    9) Buscar autor pelo nome
                    10) Calcular idade dos autores
                    
                    0) Sair
                    """;
        System.out.println(menu);
        while (opcao != 0) {
            try {
                opcao = leitor.nextInt();
                leitor.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarLivro();
                        break;
                    case 2:
                        buscarLivroPorTitulo();
                        break;
                    case 3:
                        listarLivros();
                        break;
                    case 4:
                        listarAutores();
                        break;
                    case 5:
                        listarAutoresVivos();
                        break;
                    case 6:
                        listaLivrosPorIdioma();
                        break;
                    case 7:
                        totalDeDownloads();
                        break;
                    case 8:
                        listarTop10Downloads();
                        break;
                    case 9:
                        buscarAutorPorNome();
                        break;
                    case 10:
                        calcularIdadeDoAutor();
                        break;
                    case 0:
                        System.out.println("saindo...");
                        break;
                    default:
                        System.out.println("Opção invalida!");
                }
                if (opcao != 0){
                    System.out.println("Deseja fazer outra operação? 1- sim / 2- não\n");
                    if(leitor.nextInt() == 1){
                        System.out.println(menu);
                    }else{
                        System.out.println("Saindo...");
                        opcao = 0;
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("Digite apenas numeros!\n");
                System.out.println(menu);
                leitor.nextLine();
            }
        }
    }

    private void cadastrarLivro(){
        System.out.println("Digite o nome do livro para cadastro");
        var livroNome = leitor.nextLine();
        var resposta = consumoApi.obterDados(livroNome);
        try {
            DadosBusca dadosBusca = converterDados.obterDados(resposta, DadosBusca.class);
            DadosLivro dadosLivro = dadosBusca.livros().getFirst();
            Livro livro = new Livro(dadosLivro, autorRepositorio);
            System.out.println("Livro adicionado: " + livro.getTitulo());
            livroRepositorio.save(livro);
        }catch (DataIntegrityViolationException e){
            System.out.println("O livro buscado ja esta cadastrado!");
        }
    }

    private void buscarLivroPorTitulo(){
        System.out.println("Digite o nome do livro que deseja buscar\n");
        var livroNome = leitor.nextLine();
        List<Livro> livro = livroRepositorio.findByTituloContainingIgnoreCase(livroNome);
        if(!livro.isEmpty()){
            System.out.println("Dados do livro: " + livro.getFirst());
        }else {
            System.out.println("Nenhum livro encontrado com esse nome");
        }
    }

    private void listarLivros() {
        List<Livro> livros = livroRepositorio.findAll();
        livros.forEach(l ->
                System.out.printf("Titulo: %s - Idioma: %s - Autor: %s\n"
                        ,l.getTitulo(), l.getLingua(), l.getAutor().getNome()));
    }

    private void listarAutores() {
        List<Autor> autores = livroRepositorio.listarTodosAutores();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        var anoAutor = Year.of(leitor.nextInt());
        List<Autor> autores = livroRepositorio.listarAutoresVivosPeloAno(anoAutor);
        System.out.println("\nAutores vivos no ano: " +anoAutor);
        for (Autor a : autores) {
            Year idadeAutor = anoAutor.minusYears(a.getAnoDeNascimento().getValue());
            System.out.println("Autor: " + a.getNome() + " Com " + idadeAutor + " anos");
        }
        System.out.println();
    }

    private void listaLivrosPorIdioma() {
        String menu = """
                Escolha qual idioma deseja buscar:
                1- Inglês / 2- Português / 3- Espanhol / 0- Sair
                """;
        var opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println(menu);
                opcao = leitor.nextInt();
                switch (opcao) {
                    case 1:
                        livroIdioma("en", "Inglês");
                        break;
                    case 2:
                        livroIdioma("pt", "Português");
                        break;
                    case 3:
                        livroIdioma("es", "Espanhol");
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção invalida");
                }
                System.out.println("Deseja fazer nova consulta? 1- sim / 2- não");
                if (leitor.nextInt() != 1){
                    System.out.println("Saindo...");
                    opcao = 0;
                }
                leitor.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas numeros!");
                leitor.nextLine();
            }

        }
    }

    private void livroIdioma(String sigla, String texto){
        List<Livro> livros = livroRepositorio.findByLinguaContaining(sigla);
        System.out.println(texto + ": " + livros.size()+" livro(s)");
    }

    private void totalDeDownloads() {
        List<Livro> livros = livroRepositorio.findAll();
        DoubleSummaryStatistics estatisticas = livros.stream().collect(Collectors.summarizingDouble(Livro::getNumeroDownload));
        System.out.printf("Total de Downloads: %.0f\n", estatisticas.getSum());
    }

    private void listarTop10Downloads() {
        List<Livro> livros = livroRepositorio.findTop10ByOrderByNumeroDownloadDesc();
        livros.forEach(System.out::println);
    }

    private void buscarAutorPorNome() {
        System.out.println("Informe o nome do autor: ");
        var nomeAutor = leitor.nextLine();
        List<Livro> livros = livroRepositorio.buscarAutorPeloNome(nomeAutor);
        System.out.println("Autor: " + livros.getFirst().getAutor().getNome());
        livros.forEach(l -> System.out.println("Livro: " + l.getTitulo() +
                " Idioma: " + l.getLingua() +
                " Numero de Downloads: "+ l.getNumeroDownload()));
    }

    private void calcularIdadeDoAutor() {
        System.out.println("Informe o nome do autor: ");
        var nomeAutor = leitor.nextLine();
        List<Livro> livros = livroRepositorio.buscarAutorPeloNome(nomeAutor);
        System.out.println("Digite o ano que deseja pesquisar: ");
        var anoAutor = Year.of(leitor.nextInt());
        Year idadeAutor = anoAutor.minusYears(livros.getFirst().getAutor().getAnoDeNascimento().getValue());
        int anoInformado = Integer.parseInt(String.valueOf(anoAutor));
        int anoFalecimento = livros.getFirst().getAutor().getAnoDeFalecimento().getValue();
        int anoNascimento = livros.getFirst().getAutor().getAnoDeNascimento().getValue();
        if (anoInformado > anoFalecimento){
            System.out.println("O autor " +livros.getFirst().getAutor().getNome() +
                    " faleceu em: " + livros.getFirst().getAutor().getAnoDeFalecimento() +
                    ", e teria: " + idadeAutor + " em: " +anoAutor);
        }else if (anoInformado < anoNascimento){
            System.out.println("O autor " + livros.getFirst().getAutor().getNome() +
                    " ainda não era nascido em: " + anoAutor);
        }else{
            System.out.println("O autor " + livros.getFirst().getAutor().getNome() +
                    " possuia " + idadeAutor + " no ano de " + anoAutor);
        }
    }
}
