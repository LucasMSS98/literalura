# Sistema de Gerenciamento de Livros e Autores

Este projeto é um sistema para gerenciamento de livros e autores, oferecendo funcionalidades como cadastro, busca, listagem e estatísticas relacionadas a livros e autores. Ele utiliza repositórios para persistência dos dados e uma API para obtenção de informações sobre os livros.

## Funcionalidades

O sistema apresenta as seguintes funcionalidades, acessíveis por meio de um menu interativo:
1. **Cadastrar um livro**: Realiza o cadastro de um livro, obtendo dados de uma API externa e salvando no banco de dados.
2. **Buscar um livro pelo título**: Permite localizar livros a partir de parte ou todo o título informado.
3. **Listar todos os livros**: Exibe todos os livros cadastrados no banco.
4. **Listar todos os autores**: Mostra uma lista de todos os autores presentes no banco.
5. **Listar autores vivos em determinado ano**: Filtra os autores que estavam vivos em um ano específico.
6. **Listar livros por idiomas**: Lista os livros disponíveis em idiomas específicos (Inglês, Português e Espanhol).
7. **Exibir o total de downloads**: Mostra o total de downloads de todos os livros.
8. **Listar os 10 livros mais baixados**: Exibe os dez livros com o maior número de downloads.
9. **Buscar autor pelo nome**: Permite localizar um autor pelo nome e listar seus livros.
10. **Calcular idade dos autores**: Calcula a idade de um autor em um ano específico, considerando seu nascimento e, se aplicável, falecimento.

## Estrutura do Projeto

- **`Principal`**: Classe principal responsável por gerenciar o menu e interações do usuário.
- **`ConsumoApi`**: Classe para consumir a API externa que fornece dados dos livros.
- **`ConverterDados`**: Classe para converter os dados recebidos da API em objetos utilizáveis pelo sistema.
- **Repositórios**:
  - **`LivroRepository`**: Interface para persistência e consultas relacionadas aos livros.
  - **`AutorRepository`**: Interface para persistência e consultas relacionadas aos autores.

## Requisitos

- **Java 8 ou superior**.
- Maven: versão 4 em diante
- Spring Boot (versão 3.2.3)
- Postgres: versão16 em diante
- <a target="_blank" href="https://gutendex.com/">API Gutendex</a>
- **Dependencias**:
  - Spring Data JPA.
  - Postgres Driver
  - Jackson Core

## Execução
![literalura](https://github.com/user-attachments/assets/18c8cded-61f8-4f4f-a7c3-b1a30c73a596)

Desenvolvido por <a target="_blank" href="https://github.com/LucasMSS98">Lucas menezes</a>
