-- ============================================
-- Script de creación de base de datos
-- Sistema de Gestión de Propiedades
-- ============================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS inmobiliaria
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE inmobiliaria;

-- ============================================
-- Tabla: escritura_notarial
-- ============================================
CREATE TABLE IF NOT EXISTS escritura_notarial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nro_escritura VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    notaria VARCHAR(100) NOT NULL,
    tomo VARCHAR(20),
    folio VARCHAR(20),
    observaciones TEXT,
    INDEX idx_nro_escritura (nro_escritura),
    INDEX idx_eliminado (eliminado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Tabla: propiedad
-- ============================================
CREATE TABLE IF NOT EXISTS propiedad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    padron_catastral VARCHAR(50) NOT NULL UNIQUE,
    direccion VARCHAR(200) NOT NULL,
    superficie_m2 DECIMAL(10,2) NOT NULL,
    destino ENUM('RES', 'COM') NOT NULL,
    antiguedad INT NOT NULL,
    escritura_id BIGINT,
    INDEX idx_padron_catastral (padron_catastral),
    INDEX idx_eliminado (eliminado),
    INDEX idx_escritura_id (escritura_id),
    -- Relación 1→1: una propiedad puede tener una escritura
    -- La restricción UNIQUE en escritura_id garantiza que cada escritura
    -- solo puede estar asociada a una propiedad
    UNIQUE KEY uk_escritura_id (escritura_id),
    FOREIGN KEY (escritura_id) 
        REFERENCES escritura_notarial(id) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Comentarios sobre la relación 1→1
-- ============================================
-- La relación 1→1 se implementa mediante:
-- 1. Clave foránea escritura_id en la tabla propiedad
-- 2. Restricción UNIQUE en escritura_id para garantizar que
--    cada escritura solo puede estar asociada a una propiedad
-- 3. ON DELETE SET NULL: si se elimina la escritura, 
--    se establece NULL en propiedad.escritura_id
-- 4. ON UPDATE CASCADE: si se actualiza el ID de la escritura,
--    se actualiza automáticamente en propiedad

