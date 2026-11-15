-- =============================================
-- Base de datos: Gestión de Usuarios y Películas
-- Arquitectura Hexagonal - JDBC / MySQL
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

-- =============================================
-- Tabla de usuarios
-- =============================================

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED')
        NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Tabla de películas
-- =============================================

CREATE TABLE IF NOT EXISTS movies (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    distribution_title VARCHAR(200) NOT NULL,
    original_title VARCHAR(200) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    original_language VARCHAR(100) NOT NULL,
    spanish_subtitles BOOLEAN NOT NULL,
    production_year INT NOT NULL,
    official_website VARCHAR(500) NOT NULL,
    duration_minutes INT NOT NULL,
    age_rating VARCHAR(20) NOT NULL,
    release_date_santiago DATE NOT NULL,
    summary TEXT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- Países de origen de las películas
-- =============================================

CREATE TABLE IF NOT EXISTS movie_countries (
    movie_id VARCHAR(36) NOT NULL,
    country VARCHAR(100) NOT NULL,
    PRIMARY KEY (movie_id, country),
    CONSTRAINT fk_movie_countries_movie
        FOREIGN KEY (movie_id)
        REFERENCES movies(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;