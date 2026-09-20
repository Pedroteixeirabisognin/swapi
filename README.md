# SWAPI - Backend Challenge

API REST desenvolvida em Java e Spring Boot utilizando dados da [SWAPI](https://swapi.py4e.com/).

A aplicação carrega os filmes da saga Star Wars em memória durante a inicialização e permite consultar os filmes e alterar suas descrições.

Cada filme possui uma versão, que é incrementada sempre que sua descrição é alterada.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Maven
- Lombok
- JUnit 5
- Mockito
- MockMvc

## Executando o projeto

### Pré-requisitos

- Java 21
- Git

Clone o repositório:

```bash
git clone <URL-DO-REPOSITORIO>
```

Entre na pasta do projeto:

```bash
cd swapi
```

Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows também pode ser utilizado:

```cmd
mvnw.cmd spring-boot:run
```

A aplicação será iniciada em:

```text
http://localhost:8080
```

Durante a inicialização, os filmes são carregados da SWAPI e armazenados em memória.

## Endpoints

### Listar filmes

```http
GET /api/films
```

Retorna os filmes carregados em memória.

### Buscar filme

```http
GET /api/films/{episodeId}
```

Exemplo:

```http
GET /api/films/4
```

### Alterar descrição

```http
PATCH /api/films/{episodeId}/description
```

Exemplo de body:

```json
{
  "description": "Nova descrição do filme."
}
```

A cada alteração da descrição, a versão do filme é incrementada.

Exemplo:

```json
{
  "episodeId": 4,
  "title": "A New Hope",
  "description": "Nova descrição do filme.",
  "director": "George Lucas",
  "producer": "Gary Kurtz, Rick McCallum",
  "releaseDate": "1977-05-25",
  "version": 2
}
```

## Validações

A descrição não pode ser vazia e possui limite máximo de 1000 caracteres.

A API também possui tratamento para filmes não encontrados e erros de validação.

## Testes

Foram implementados testes unitários para as camadas de repository e service e testes do controller utilizando MockMvc.

Para executar:

```bash
./mvnw clean test
```

## Estrutura

O projeto está dividido principalmente em:

```text
client      - comunicação com a SWAPI
controller  - endpoints REST
dto         - objetos de entrada e saída
exception   - tratamento de exceções
model       - modelo de filme
repository  - armazenamento em memória
service     - regras da aplicação
```

## SWAPI

Os dados dos filmes são obtidos através de:

```text
https://swapi.py4e.com/api/films/
```

A SWAPI precisa estar disponível durante a inicialização da aplicação para que os filmes sejam carregados.