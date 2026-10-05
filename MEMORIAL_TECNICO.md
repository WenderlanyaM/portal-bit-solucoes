# MEMORIAL TÉCNICO DE DESENVOLVIMENTO

**Candidato(a):** Wenderlânya Medeiros  
**Processo Seletivo:** Desenvolvedor(a) de Sistemas Júnior (09/2026) — bit Soluções  
**Projeto:** Portal de Solicitações Internas  
**Data de Entrega:** Outubro de 2026

---

## 1. Tecnologias Utilizadas

A stack tecnológica do projeto foi selecionada considerando maturidade de mercado, robustez corporativa e adequação direta ao escopo do desafio:

* **Linguagem de Programação:** Java 21 LTS (JDK 21) — Suporte a tipagem estática forte, concorrência nativa (*Virtual Threads*) e Records.
* **Framework Backend:** Spring Boot (v4.1.1) — Inversão de controle, injeção de dependências e gestão automática de infraestrutura (*Convention over Configuration*).
* **Framework Frontend:** Thymeleaf (v3.1.5.RELEASE) — Motor de renderização dinâmica em Server-Side Rendering (SSR).
* **Biblioteca de Estilização & UI:** Bootstrap 5.3.2 e Bootstrap Icons 1.11.1 — Layout responsivo, identidade visual em azul/branco e suporte nativo a Dark Mode via `localStorage`.
* **Banco de Dados Relacional:** MariaDB Server (Driver JDBC) — SGBD de alta performance compatível com MySQL.
* **Ferramenta de Migração e DDL:** Flyway DB Migration (v12.4.0) — Versionamento e execução automatizada de scripts SQL de esquema e carga inicial (*seed*).
* **Ferramenta de Autenticação e Segurança:** Spring Security (v7.1.1) — Gestão de sessão HTTP, autenticação baseada em `UserDetailsService`, criptografia BCrypt e proteção CSRF.
* **Biblioteca de Validação:** Jakarta Bean Validation (v3.1.1) — Validação declarativa em tempo de entrada de formulários (`@NotBlank`, `@NotNull`).
* **Ferramenta de Containerização:** Docker e Docker Compose (Docker Engine v29.7.2) — Isolamento do ambiente de dados e paridade de execução.
* **Soluções de Testes:** JUnit 5 e Mockito — Testes unitários isolados para serviços, repositórios e controladores.
* **Serviços de CI/CD (Cloud/DevOps):** GitHub Actions — Pipeline de integração contínua executando `./mvnw verify` a cada *push*.

---

## 2. Justificativa Técnica

Para cada tecnologia eleita, destacam-se os motivos, benefícios frente a alternativas e impactos diretos na manutenibilidade, escalabilidade e produtividade:

### 2.1 Java 21 LTS & Spring Boot 4.1.1 (Framework Backend)
* **Motivo da Escolha:** Adoção da stack corporativa padrão para garantir estabilidade, segurança e suporte de longo prazo (*Long-Term Support*).
* **Benefícios para o Cenário:** O Spring Boot assume a gestão transparente de recursos críticos como o **pool de conexões HikariCP**, o controle transacional declarativo (`@Transactional`) e o ciclo de vida das sessões HTTP. Além disso, aproveitou-se o recurso de *Virtual Threads* do Java 21 (`spring.threads.virtual.enabled=true`) para otimizar o modelo padrão *thread-per-request* do Tomcat, superando as desvantagens do modelo bloqueante tradicional sem a complexidade arriscada de uma stack reativa (WebFlux + R2DBC).
* **Vantagens em Relação a Alternativas:** Comparado a linguagens dinâmicas, o Java previne erros em tempo de execução via tipagem estática e elimina código repetitivo (*boilerplate*) com o uso de DTOs em *Records*.
* **Impacto na Manutenção e Escalabilidade:** Facilita a evolução modular do sistema e garante alta performance concorrente.

### 2.2 MariaDB Server & Flyway DB Migration (Banco de Dados e Persistência)
* **Motivo da Escolha:** Uso do MariaDB gerenciado por migrações versionadas do Flyway, substituindo o DDL automático do Hibernate.
* **Justificativa Literária de Referência:** Conforme respaldado pela literatura especializada do ecossistema Spring (*Pro Spring* de Iuliana Cosmina), o MariaDB foi escolhido por apresentar melhorias expressivas de velocidade, gerenciamento de visões e armazenamento comparado ao MySQL tradicional. Adotou-se o tipo nativo `ENUM` no banco alinhado ao `enum` do Java (`StatusSolicitacao`) e mapeado via `@Enumerated(EnumType.STRING)`, o que impede a gravação de lixo corporativo (como `?status=XPTO`) e garante alta performance de indexação.
* **Benefícios para o Cenário:** As migrações `V1__init_schema.sql` e `V2__seed_expanded_data.sql` garantem que o banco suba de forma idêntica e automatizada em qualquer ambiente.
* **Impacto na Produtividade:** Torna as alterações estruturais auditáveis e livres de divergências entre homologação e produção.

### 2.3 Spring Security 7.1.1 (Autenticação e Segurança)
* **Motivo da Escolha:** Implementação de segurança baseada em sessões autenticadas, criptografia irreversível por **BCrypt** e proteção anti-CSRF.
* **Benefícios para o Cenário:** Nenhuma senha circula em texto plano. O Spring Security intercepta rotas não autorizadas e gerencia o identificador de sessão com segurança.
* **Vantagens em Relação a Alternativas:** Proteção nativa integrada contra ataques comuns da Web sem necessidade de implementações manuais propensas a falhas.
* **Impacto na Escalabilidade:** Oferece um perímetro de segurança robusto e pronto para expansão de perfis de acesso corporativo.

### 2.4 Spring Data JPA e `@EntityGraph` (Otimização de Consultas)
* **Motivo da Escolha:** Abstração de persistência relacional com otimização explícita contra o problema de desempenho **N+1**.
* **Benefícios para o Cenário:** Através da anotação `@EntityGraph(attributePaths = {"user", "category"})` no `TicketRepository`, o Hibernate é forçado a buscar os relacionamentos em um único *JOIN* otimizado. No Dashboard, agregou-se a contagem por status via JPQL (`GROUP BY t.status`), delegando o esforço ao SGBD e processando o resultado via `Streams` e `EnumMap`.
* **Vantagens:** Elimina consultas redundantes à base de dados e reduz o tráfego de rede.
* **Impacto na Manutenção:** Mantém o código limpo, declarativo e altamente performático sob grandes volumes de dados.

### 2.5 Thymeleaf & Bootstrap 5.3.2 (Frontend e UI/UX)
* **Motivo da Escolha:** Renderização Server-Side (SSR) acoplada ao ecossistema Spring, estilizada com Bootstrap 5.
* **Benefícios para o Cenário:** Entrega uma interface responsiva, limpa e padronizada em azul e branco, contando com suporte dinâmico a Tema Escuro persistido no `localStorage`.
* **Vantagens em Relação a Alternativas:** Evita a complexidade desnecessária de gerenciar uma arquitetura SPA separada para o escopo do desafio.
* **Impacto na Produtividade:** Agiliza a entrega visual com alto padrão de usabilidade e modais interativos.

---

## 3. Justificativa Conceitual

* **Estrutura Geral da Aplicação:** O sistema foi concebido como uma aplicação monolítica estruturada de forma modular, permitindo fácil navegação e manutenção.
* **Organização das Camadas:** Segue estritamente o padrão corporativo por pacotes: `config` (segurança/infraestrutura), `controller` (controladores web SSR e API REST), `dto` (transferência segura de dados via *Records* para evitar *mass assignment*), `service` (regras de negócio e transacionalidade explícita via `@Transactional`), `repository` (acesso a dados) e `model` (domínio e mapeamento relacional).
* **Estratégia de Modelagem de Dados:** Relacionamentos relacionais normalizados com restrições de integridade referencial ACID e chaves estrangeiras explícitas, garantindo consistência estrutural entre usuários, categorias e solicitações.
* **Padrões de Projeto Utilizados:** Padrão **MVC** (Model-View-Controller) no fluxo web, **DAO/Repository** na persistência, **DTO** (*Data Transfer Object*) para proteção de borda e **Injeção de Dependências por Construtor** (eliminando o uso de `@Autowired` em campos, considerado um anti-padrão).
* **Estratégia de Autenticação:** Autenticação baseada em formulário do Spring Security, validando utilizadores cadastrados na base de dados com senhas protegidas por hash BCrypt e gerenciamento ativo de sessões HTTP.
* **Estratégia de Comunicação entre Frontend e Backend:** Na interface principal, utiliza-se o modelo tradicional e eficiente de Server-Side Rendering (SSR) via Thymeleaf. Adicionalmente, a aplicação expõe uma camada de API REST em `/api/tickets` retornando JSON puro padronizado sob a norma RFC 9457 (`ProblemDetail`) no `ApiExceptionHandler`.
* **Organização do Código-Fonte:** Nomenclaturas padronizadas em inglês para classes/métodos e português para as visões de negócio, com separação estrita de arquivos de configuração, testes unitários isolados e scripts de migração versionados.

---

## 4. Análise Crítica

Como limitações da solução atual, destaca-se que qualquer usuário autenticado com acesso a um chamado em status ABERTO pode exclui-lo ou alterar livremente seu fluxo, além de não haver validações restritivas que impeçam a regressão de um chamado já CONCLUIDO de voltar indevidamente para o status ABERTO. Em um ambiente corporativo produtivo de maior escala, seria ideal aprimorar a arquitetura aplicando regras granulares de segurança — como restringir a exclusão apenas ao autor original do chamado, limitar as transições de status aos colaboradores do setor responsável pela categoria correspondente e implementar uma chave de alternância no Dashboard para que o utilizador possa alternar dinamicamente entre uma visão gerencial corporativa e uma visão individual focada apenas nas suas próprias solicitações. Como melhorias futuras, a introdução de paginação avançada baseada em Pageable e a adoção de um cache distribuído permitiriam escalar a aplicação de forma robusta e eficiente para atender a milhares de acessos simultâneos sem perda de desempenho.