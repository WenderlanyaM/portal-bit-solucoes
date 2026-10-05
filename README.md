# README - Portal de Solicitações Internas (bit Soluções)

Aplicação Web Full Stack desenvolvida para o desafio técnico do Processo Seletivo para **Desenvolvedor(a) de Sistemas Júnior** da **bit Soluções**. O sistema consiste em uma plataforma corporativa para registro, acompanhamento, filtragem, ordenação dinâmica e gestão do ciclo de vida de solicitações internas de colaboradores, contemplando tanto interface web (SSR) quanto endpoints dedicados para API REST.

---

## 1. Pré-requisitos

Para compilar e executar o projeto corretamente, certifique-se de ter os seguintes componentes instalados em sua máquina:

### Linguagem utilizada
* **Java 21 LTS** (JDK versão `21` ou superior).

### Banco de dados
* **MariaDB Server** (executado de forma containerizada e isolada através do **Docker Engine** e **Docker Compose**).

### Dependências
O gerenciamento das dependências do projeto é realizado pelo **Apache Maven**. As principais bibliotecas configuradas no arquivo `pom.xml` são:

* **Backend e Persistência:**
   * `Spring Boot` (v4.1.1): Framework base para injeção de dependências e controle MVC.
   * `Spring Security`: Autenticação customizada, criptografia com BCrypt e gerenciamento de sessão HTTP.
   * `Spring Data JPA / Hibernate ORM`: Abstração de dados, mapeamento objeto-relacional (ORM) e otimização via `@EntityGraph`.
   * `Flyway Core`: Ferramenta de migração automatizada para versionamento de esquemas DDL e carga de dados iniciais.
   * `Jakarta Bean Validation`: Validação declarativa dos formulários.
   * `MariaDB Connector/J`: Driver JDBC para comunicação com o banco de dados.

* **API REST & Tratamento de Erros:**
   * `Spring MVC / ProblemDetail`: Suporte nativo ao padrão RFC 9457 para respostas de erro em JSON padronizado (`/api/tickets`).

* **Frontend:**
   * `Thymeleaf`: Motor de renderização dinâmico de templates (Server-Side Rendering - SSR).
   * `Thymeleaf Extras Spring Security`: Integração de permissões de segurança diretamente nas views.
   * `Bootstrap 5` e `Bootstrap Icons`: Framework CSS para layout responsivo, limpo em tons de azul/branco e com suporte a Tema Escuro.

* **Testes e DevOps:**
   * `JUnit 5` e `Mockito`: Suporte a testes unitários e comportamentais isolados.
   * `GitHub Actions`: Pipeline de Integração Contínua (CI/CD) para verificação automática de build e testes a cada *push*.

---

## 2. Instalação

Siga o passo a passo detalhado abaixo para realizar a preparação completa do ambiente em sua máquina local.

### Passo a passo completo para: Banco de dados
A infraestrutura de dados foi projetada para rodar de maneira isolada em containers através do Docker Compose.

1. Certifique-se de que o **Docker Desktop** (ou o daemon do Docker) esteja ativo e em execução em sua máquina.
2. Na raiz da pasta do projeto (onde se encontra o arquivo `compose.yaml`), execute o seguinte comando no terminal para inicializar o container do banco de dados em segundo plano:
   ```bash
   docker compose up -d



3. O Docker subirá o banco de dados configurado, que ficará aguardando conexões na porta local `3306`.

### Passo a passo completo para: Backend

A instalação do backend não requer passos manuais de compilação prévia, pois o Maven fará isso durante a inicialização.
Além disso, a criação das tabelas no banco de dados é automatizada: ao iniciar o backend pela primeira vez, a ferramenta **Flyway Migration** detectará o banco de dados e executará os scripts versionados (`V1__init_schema.sql` e `V2__seed_expanded_data.sql`).

### Passo a passo completo para: Frontend

Esta aplicação adota o padrão de **Server-Side Rendering (SSR)** via Thymeleaf, o que significa que o frontend (arquivos HTML, CSS e scripts) está **totalmente integrado e embarcado no próprio projeto Spring Boot**.

* Não é necessário instalar ferramentas externas como Node.js ou NPM. O Maven empacota tudo nativamente.

---

## 3. Configuração

### Variáveis de ambiente

A aplicação utiliza um arquivo **`.env`** localizado na raiz do projeto para desacoplar e proteger credenciais sensíveis, alinhando-se às boas práticas de segurança. Um template seguro (**`.env.example`**) é fornecido no repositório.

Certifique-se de criar ou ajustar o arquivo `.env` na raiz com as seguintes variáveis:

```env
DB_USER=root
DB_PASSWORD=usuario123

```

> **Nota de Segurança:** O arquivo `.env` real encontra-se listado no `.gitignore` para impedir a exposição de credenciais em repositórios remotos.

### Credenciais de demonstração

Para facilitar os testes imediatos, os usuários de demonstração foram populados na base de dados por meio da migração automatizada do Flyway.

Todos os utilizadores abaixo compartilham a mesma **senha padrão de acesso:** `12345`

| Utilizador | Setor / Perfil de Acesso |
| --- | --- |
| **wenderlanya** | Perfil Padrão / TI |
| **joao.silva** | Perfil Padrão / RH |
| **maria.souza** | Perfil Padrão / Compras |
| **carlos.lima** | Perfil Padrão / Financeiro |
| **ana.costa** | Perfil Padrão / Infraestrutura |
| **pedro.santos** | Perfil Padrão / Geral |

---

## 4. Execução e Testes

### Como executar o Backend (Via IntelliJ IDEA)

1. Abra a IDE **IntelliJ IDEA**.
2. Clique em **File > Open...** e selecione a pasta raiz do projeto.
3. Aguarde o IntelliJ carregar as dependências do `pom.xml`.
4. Certifique-se de que o SDK do projeto está configurado para o **Java 21**.
5. Navegue até a classe principal:
   `src/main/java/info/bitsolucoes/portal/PortalApplication.java`
6. Clique no ícone de Play verde ao lado da classe e selecione **Run 'PortalApplication'**.

### Como executar os Testes Automatizados (Via IntelliJ IDEA)

Para validar todos os testes unitários do projeto (serviços, repositórios e controladores) utilizando a interface gráfica do IntelliJ:

1. No painel lateral esquerdo (**Project Explorer**), navegue até a pasta de testes:
   `src/test/java/info/bitsolucoes/portal`
2. Para rodar todos os testes de uma só vez, clique com o botão direito em cima do pacote `portal` (ou da pasta `test`), e selecione a opção **Run 'All Tests'** (ou *Run Tests in portal*).
3. Caso prefira rodar um teste específico isoladamente (por exemplo, o `TicketServiceTest`), abra a classe correspondente e clique no ícone de Play verde localizado ao lado da declaração da classe ou de métodos individuais.
4. O painel inferior **Run** exibirá o resultado da execução com os indicadores em verde indicando sucesso absoluto.

---

## 5. Acesso e Endpoints

### Acesso à Interface Web

1. Abra o navegador web de sua preferência.
2. Acesse a URL local:
```text
http://localhost:8080

```


3. Faça login utilizando um dos utilizadores de demonstração (ex: utilizador `wenderlanya` e senha `12345`).

### Acesso à API REST

Caso deseje consumir os dados da aplicação de forma desacoplada em formato JSON, a API encontra-se disponível no endpoint:

* `GET /api/tickets` (Protegido por autenticação / tratado com padrão RFC 9457 em caso de exceções).

