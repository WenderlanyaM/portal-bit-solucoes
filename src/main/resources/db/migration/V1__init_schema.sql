CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tickets (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(150) NOT NULL,
                         description TEXT NOT NULL,
                         status ENUM('ABERTO', 'EM_ATENDIMENTO', 'CONCLUIDO') NOT NULL DEFAULT 'ABERTO',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         category_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (category_id) REFERENCES categories(id),
                         FOREIGN KEY (user_id) REFERENCES users(id)
);