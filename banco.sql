CREATE DATABASE IF NOT EXISTS bancoDados;
USE bancoDados;

-- Tabela para armazenar dados pessoais
CREATE TABLE IF NOT EXISTS pessoas (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    tipoSanguineo VARCHAR(5) NOT NULL,
    seDoador VARCHAR(3) NOT NULL
    );

-- Tabela para armazenar usernames e senhas
CREATE TABLE IF NOT EXISTS usuarios (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        username VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL
    )