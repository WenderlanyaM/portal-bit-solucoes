-- Inserção das Categorias sugeridas no edital
INSERT INTO categories (name) VALUES ('TI'), ('RH'), ('Compras'), ('Financeiro'), ('Infraestrutura');

-- Inserção de 6 Usuários comuns (todos com a senha em BCrypt)
INSERT INTO users (username, password) VALUES
                                           ('wenderlanya', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG'),
                                           ('joao.silva', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG'),
                                           ('maria.souza', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG'),
                                           ('carlos.lima', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG'),
                                           ('ana.costa', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG'),
                                           ('pedro.santos', '$2a$12$Ka2FVPUbhJpHpsQNAUarSeeyUlbDoHwTzVJehXVTqzQowqWl97TYG');

-- Inserção de 10 Solicitações com datas personalizadas para testar os filtros por período
INSERT INTO tickets (title, description, status, created_at, category_id, user_id) VALUES
                                                                                       ('Falha na VPN', 'Não consigo conectar na VPN da empresa de casa.', 'ABERTO', '2026-10-01 09:30:00', 1, 1),
                                                                                       ('Troca de Cadeira de Escritório', 'A minha cadeira está com o braço quebrando.', 'EM_ATENDIMENTO', '2026-10-01 10:15:00', 5, 2),
                                                                                       ('Compra de Licença IntelliJ', 'Necessário renovar a licença do IDE para o projeto atual.', 'ABERTO', '2026-10-02 14:00:00', 3, 3),
                                                                                       ('Erro no Holerite', 'O desconto do VT veio incorreto no contracheque deste mês.', 'CONCLUIDO', '2026-10-02 16:45:00', 2, 4),
                                                                                       ('Reembolso de Viagem', 'Solicitação de reembolso referente à visita ao cliente em Campina Grande.', 'ABERTO', '2026-10-03 08:20:00', 4, 5),
                                                                                       ('Lentidão no Computador', 'A máquina está travando muito ao abrir o Docker.', 'EM_ATENDIMENTO', '2026-10-03 11:10:00', 1, 6),
                                                                                       ('Solicitação de Novo Teclado', 'Teclado atual com teclas falhando.', 'ABERTO', '2026-10-04 09:00:00', 5, 1),
                                                                                       ('Atualização Cadastral', 'Preciso atualizar meu endereço e dependentes no sistema.', 'CONCLUIDO', '2026-10-04 10:30:00', 2, 2),
                                                                                       ('Aquisição de Monitores Extras', 'Pedido de segundo monitor para melhoria de produtividade no desenvolvimento.', 'ABERTO', '2026-10-04 13:40:00', 3, 3),
                                                                                       ('Aprovação de Orçamento de Servidor Cloud', 'Valores para o ambiente de homologação na AWS.', 'EM_ATENDIMENTO', '2026-10-04 15:10:00', 4, 4);