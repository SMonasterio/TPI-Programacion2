-- ============================================
-- Script de inserción de datos de prueba
-- Sistema de Gestión de Propiedades
-- ============================================

USE inmobiliaria;

-- Limpiar datos existentes (opcional, comentar si no se desea)
-- DELETE FROM propiedad;
-- DELETE FROM escritura_notarial;
-- ALTER TABLE escritura_notarial AUTO_INCREMENT = 1;
-- ALTER TABLE propiedad AUTO_INCREMENT = 1;

-- ============================================
-- Insertar Escrituras Notariales
-- ============================================

INSERT INTO escritura_notarial (eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones) VALUES
(FALSE, 'ESC-001-2024', '2024-01-15', 'Notaría N° 1 - Dr. Juan Pérez', 'Tomo 123', 'Folio 456', 'Escritura de compraventa'),
(FALSE, 'ESC-002-2024', '2024-02-20', 'Notaría N° 2 - Dra. María González', 'Tomo 124', 'Folio 457', 'Escritura de donación'),
(FALSE, 'ESC-003-2024', '2024-03-10', 'Notaría N° 3 - Dr. Carlos Rodríguez', 'Tomo 125', 'Folio 458', 'Escritura de sucesión'),
(FALSE, 'ESC-004-2024', '2024-04-05', 'Notaría N° 1 - Dr. Juan Pérez', 'Tomo 126', 'Folio 459', 'Escritura de permuta'),
(FALSE, 'ESC-005-2024', '2024-05-12', 'Notaría N° 4 - Dra. Ana Martínez', 'Tomo 127', 'Folio 460', NULL);

-- ============================================
-- Insertar Propiedades
-- ============================================

INSERT INTO propiedad (eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id) VALUES
(FALSE, 'PC-001-2024', 'Av. Libertador 1234, CABA', 150.50, 'RES', 10, 1),
(FALSE, 'PC-002-2024', 'Calle San Martín 567, La Plata', 200.00, 'COM', 5, 2),
(FALSE, 'PC-003-2024', 'Av. Corrientes 890, CABA', 85.75, 'RES', 15, 3),
(FALSE, 'PC-004-2024', 'Calle Mitre 234, Rosario', 300.25, 'COM', 8, 4),
(FALSE, 'PC-005-2024', 'Av. 9 de Julio 456, CABA', 120.00, 'RES', 20, NULL),
(FALSE, 'PC-006-2024', 'Calle Belgrano 789, Córdoba', 180.50, 'RES', 12, NULL);

-- ============================================
-- Verificar datos insertados
-- ============================================

-- Mostrar todas las escrituras
SELECT 'Escrituras Notariales' AS tabla;
SELECT * FROM escritura_notarial WHERE eliminado = FALSE;

-- Mostrar todas las propiedades
SELECT 'Propiedades' AS tabla;
SELECT 
    p.id,
    p.padron_catastral,
    p.direccion,
    p.superficie_m2,
    p.destino,
    p.antiguedad,
    p.escritura_id,
    e.nro_escritura,
    e.notaria
FROM propiedad p
LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
WHERE p.eliminado = FALSE;

-- ============================================
-- Notas sobre los datos de prueba
-- ============================================
-- 1. Se insertaron 5 escrituras notariales
-- 2. Se insertaron 6 propiedades:
--    - 4 propiedades tienen escritura asociada (1→1)
--    - 2 propiedades NO tienen escritura asociada (escritura_id = NULL)
-- 3. La relación 1→1 se cumple: cada propiedad tiene como máximo una escritura
-- 4. La restricción UNIQUE en escritura_id garantiza que cada escritura
--    solo puede estar asociada a una propiedad

