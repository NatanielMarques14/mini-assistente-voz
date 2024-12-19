CREATE DATABASE IF NOT EXISTS bancoDados;
USE bancoDados;

-- Tabela para armazenar dados pessoais
CREATE TABLE IF NOT EXISTS pessoas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade VARCHAR(20) NOT NULL,
    tipoSanguineo VARCHAR(5) NOT NULL,
    seDoador ENUM('yes', 'no') NOT NULL
);

-- Tabela para armazenar usernames e senhas
CREATE TABLE IF NOT EXISTS usuarios (
    senha VARCHAR(255) NOT NULL, -- Senha com hash pode exigir mais espaço
    pessoa_id INT,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
