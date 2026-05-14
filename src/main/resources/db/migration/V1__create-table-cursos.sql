CREATE TABLE curso (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL UNIQUE,
                       periodo VARCHAR(20) NOT NULL,
                       ativo BOOLEAN DEFAULT TRUE CHECK (ativo IN (0, 1))
);
