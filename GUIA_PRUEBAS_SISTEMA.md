# Guía de Pruebas del Sistema

Esta guía te ayudará a probar todas las funcionalidades del sistema de gestión de propiedades.

---

## 📋 Checklist de Pruebas

### ✅ Pruebas de Conexión y Configuración

- [ ] La aplicación inicia sin errores
- [ ] Se muestra el menú principal correctamente
- [ ] No hay errores de conexión a la base de datos
- [ ] Los datos de prueba se cargaron correctamente

---

## 🧪 PRUEBA 1: Verificar Datos de Prueba

### Objetivo: Confirmar que los datos iniciales están cargados

**Pasos:**
1. Ejecuta la aplicación
2. Selecciona **"2. Gestionar Escrituras Notariales"**
3. Selecciona **"4. Listar Todas las Escrituras"**
4. **Resultado esperado:** Deberías ver 5 escrituras notariales

**Verificar:**
- [ ] Se muestran 5 escrituras
- [ ] Los datos se muestran correctamente (ID, número, fecha, notaría, etc.)

**Pasos:**
1. Selecciona **"V. Volver al menú principal"**
2. Selecciona **"1. Gestionar Propiedades"**
3. Selecciona **"4. Listar Todas las Propiedades"**
4. **Resultado esperado:** Deberías ver 6 propiedades

**Verificar:**
- [ ] Se muestran 6 propiedades
- [ ] 4 propiedades tienen escritura asociada
- [ ] 2 propiedades NO tienen escritura asociada (escritura_id = null)

---

## 🧪 PRUEBA 2: Búsquedas

### 2.1. Buscar Propiedad por ID

**Pasos:**
1. Menú Propiedades → **"2. Buscar Propiedad por ID"**
2. Ingresa: `1`
3. **Resultado esperado:** Muestra la propiedad con ID 1

**Verificar:**
- [ ] Se muestra la propiedad correcta
- [ ] Los datos son correctos
- [ ] Si tiene escritura asociada, se muestra

**Prueba de error:**
1. Busca ID: `999`
2. **Resultado esperado:** Mensaje "No se encontró la propiedad con ID: 999"

### 2.2. Buscar Propiedad por Padrón Catastral

**Pasos:**
1. Menú Propiedades → **"3. Buscar Propiedad por Padrón Catastral"**
2. Ingresa: `PC-001-2024`
3. **Resultado esperado:** Muestra la propiedad con ese padrón

**Verificar:**
- [ ] Se encuentra la propiedad
- [ ] Funciona con mayúsculas/minúsculas (debería convertir a mayúsculas)

**Prueba de error:**
1. Busca padrón: `PC-999-2024`
2. **Resultado esperado:** "No se encontró la propiedad con padrón: PC-999-2024"

### 2.3. Buscar Escritura por ID

**Pasos:**
1. Menú Escrituras → **"2. Buscar Escritura por ID"**
2. Ingresa: `1`
3. **Resultado esperado:** Muestra la escritura con ID 1

### 2.4. Buscar Escritura por Número

**Pasos:**
1. Menú Escrituras → **"3. Buscar Escritura por Número"**
2. Ingresa: `ESC-001-2024`
3. **Resultado esperado:** Muestra la escritura con ese número

---

## 🧪 PRUEBA 3: Crear Nueva Escritura Notarial

### Objetivo: Verificar creación de escritura con validaciones

**Pasos:**
1. Menú Escrituras → **"1. Crear Escritura Notarial"**
2. Completa los datos:
   - Número de Escritura: `ESC-TEST-001`
   - Fecha: `2024-12-15`
   - Notaría: `Notaría de Prueba`
   - Tomo: `Tomo 999`
   - Folio: `Folio 999`
   - Observaciones: `Escritura de prueba`
3. **Resultado esperado:** "✅ Escritura creada exitosamente con ID: X"

**Verificar:**
- [ ] Se crea correctamente
- [ ] Se asigna un ID automáticamente
- [ ] Aparece en el listado

**Pruebas de validación (deben fallar):**
1. **Sin número de escritura:** Debe mostrar error
2. **Sin fecha:** Debe mostrar error
3. **Sin notaría:** Debe mostrar error
4. **Fecha inválida:** Debe mostrar error de formato

---

## 🧪 PRUEBA 4: Crear Nueva Propiedad

### 4.1. Propiedad SIN Escritura

**Pasos:**
1. Menú Propiedades → **"1. Crear Propiedad"**
2. Completa los datos:
   - Padrón Catastral: `PC-TEST-001`
   - Dirección: `Calle de Prueba 123`
   - Superficie: `100.50`
   - Destino: `RES`
   - Antigüedad: `5`
   - ¿Asociar escritura? `N`
3. **Resultado esperado:** "✅ Propiedad creada exitosamente"

**Verificar:**
- [ ] Se crea correctamente
- [ ] No tiene escritura asociada
- [ ] Aparece en el listado

### 4.2. Propiedad CON Escritura Nueva

**Pasos:**
1. Menú Propiedades → **"1. Crear Propiedad"**
2. Completa los datos básicos:
   - Padrón Catastral: `PC-TEST-002`
   - Dirección: `Calle de Prueba 456`
   - Superficie: `200.00`
   - Destino: `COM`
   - Antigüedad: `10`
3. ¿Asociar escritura? `S`
4. ID de escritura: `0` (crear nueva)
5. Completa datos de escritura:
   - Número: `ESC-TEST-002`
   - Fecha: `2024-12-16`
   - Notaría: `Notaría Test 2`
   - (resto opcional)
6. **Resultado esperado:** Se crean tanto la propiedad como la escritura

**Verificar:**
- [ ] Se crea la propiedad
- [ ] Se crea la escritura automáticamente
- [ ] La propiedad tiene la escritura asociada
- [ ] La relación 1→1 se cumple

### 4.3. Propiedad CON Escritura Existente

**Pasos:**
1. Primero, crea una escritura (Prueba 3)
2. Anota el ID de la escritura creada
3. Menú Propiedades → **"1. Crear Propiedad"**
4. Completa datos básicos:
   - Padrón Catastral: `PC-TEST-003`
   - (resto de datos)
5. ¿Asociar escritura? `S`
6. ID de escritura: `[ID de la escritura creada]`
7. **Resultado esperado:** Propiedad creada con escritura existente asociada

**Verificar:**
- [ ] Se asocia correctamente la escritura existente
- [ ] La relación 1→1 se mantiene

**Prueba de validación:**
1. **Sin padrón catastral:** Debe mostrar error
2. **Sin dirección:** Debe mostrar error
3. **Superficie <= 0:** Debe mostrar error
4. **Sin destino:** Debe mostrar error
5. **Antigüedad negativa:** Debe mostrar error
6. **Destino inválido:** Debe mostrar error (solo RES o COM)

---

## 🧪 PRUEBA 5: Actualizar Propiedad

### Objetivo: Verificar actualización con validaciones

**Pasos:**
1. Menú Propiedades → **"5. Actualizar Propiedad"**
2. Ingresa ID: `1`
3. Modifica algunos campos (presiona Enter para mantener otros):
   - Dirección: `Nueva Dirección 789`
   - Superficie: `175.25`
4. **Resultado esperado:** "✅ Propiedad actualizada exitosamente"

**Verificar:**
- [ ] Se actualiza correctamente
- [ ] Los campos modificados cambian
- [ ] Los campos no modificados se mantienen
- [ ] La escritura asociada se mantiene (si tenía)

**Prueba de error:**
1. Actualiza ID: `999`
2. **Resultado esperado:** "No se encontró la propiedad con ID: 999"

---

## 🧪 PRUEBA 6: Actualizar Escritura

**Pasos:**
1. Menú Escrituras → **"5. Actualizar Escritura"**
2. Ingresa ID: `1`
3. Modifica algunos campos
4. **Resultado esperado:** "✅ Escritura actualizada exitosamente"

**Verificar:**
- [ ] Se actualiza correctamente
- [ ] Los cambios se reflejan

---

## 🧪 PRUEBA 7: Eliminación Lógica (Baja Lógica)

### 7.1. Eliminar Propiedad

**Pasos:**
1. Menú Propiedades → **"6. Eliminar Propiedad (lógico)"**
2. Ingresa ID: `1`
3. Confirma: `S`
4. **Resultado esperado:** "✅ Propiedad eliminada exitosamente"

**Verificar:**
- [ ] La propiedad NO aparece en el listado
- [ ] La propiedad NO se encuentra por búsqueda
- [ ] En la base de datos, `eliminado = true` (verificar en DBeaver)

**Prueba de error:**
1. Intenta eliminar ID: `999`
2. **Resultado esperado:** "No se encontró la propiedad con ID: 999"

### 7.2. Eliminar Escritura

**Pasos:**
1. Menú Escrituras → **"6. Eliminar Escritura (lógico)"**
2. Ingresa ID: `1`
3. Confirma: `S`
4. **Resultado esperado:** "✅ Escritura eliminada exitosamente"

**Verificar:**
- [ ] La escritura NO aparece en el listado
- [ ] Si estaba asociada a una propiedad, la propiedad ahora muestra `escritura_id = null` (o se mantiene según diseño)

---

## 🧪 PRUEBA 8: Transacciones

### Objetivo: Verificar que las transacciones funcionan (commit/rollback)

### 8.1. Transacción Exitosa

**Pasos:**
1. Crea una propiedad con escritura nueva (Prueba 4.2)
2. **Resultado esperado:** Ambas se crean correctamente

**Verificar en DBeaver:**
- [ ] La propiedad existe en la BD
- [ ] La escritura existe en la BD
- [ ] La relación está correcta

### 8.2. Transacción con Rollback (Simular Error)

Para probar rollback, necesitarías modificar temporalmente el código para forzar un error, pero puedes verificar que:

- [ ] Si falla la creación de la escritura, la propiedad NO se crea
- [ ] Si falla la creación de la propiedad, la escritura NO se crea
- [ ] No quedan datos parciales en la BD

---

## 🧪 PRUEBA 9: Relación 1→1

### Objetivo: Verificar que la relación 1→1 se cumple

### 9.1. Una Propiedad, Una Escritura

**Pasos:**
1. Crea una propiedad con escritura asociada
2. Intenta asociar esa misma escritura a otra propiedad
3. **Resultado esperado:** Error de violación de unicidad (UNIQUE constraint)

**Verificar:**
- [ ] La restricción UNIQUE en `escritura_id` funciona
- [ ] No se puede asociar la misma escritura a dos propiedades

### 9.2. Verificar en Base de Datos

**En DBeaver, ejecuta:**
```sql
SELECT p.id, p.padron_catastral, p.escritura_id, e.nro_escritura
FROM propiedad p
LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
WHERE p.eliminado = false;
```

**Verificar:**
- [ ] Cada propiedad tiene máximo una escritura
- [ ] Cada escritura está asociada a máximo una propiedad
- [ ] Algunas propiedades tienen `escritura_id = NULL` (correcto)

---

## 🧪 PRUEBA 10: Manejo de Errores

### Objetivos: Verificar que los errores se manejan correctamente

**Pruebas:**
1. **ID inválido (texto):** Debe mostrar "ID inválido"
2. **ID inexistente:** Debe mostrar mensaje claro
3. **Campos obligatorios vacíos:** Debe mostrar error específico
4. **Formatos incorrectos:** Debe mostrar error de formato
5. **Violación de unicidad:** Debe mostrar error de BD
6. **Conexión a BD perdida:** Debe mostrar error de conexión

**Verificar:**
- [ ] Los mensajes de error son claros
- [ ] La aplicación no se cierra inesperadamente
- [ ] Se puede continuar usando la aplicación después de un error

---

## 🧪 PRUEBA 11: Casos Especiales

### 11.1. Propiedad con Escritura Eliminada

**Pasos:**
1. Crea una propiedad con escritura asociada
2. Elimina la escritura (baja lógica)
3. Busca la propiedad
4. **Resultado esperado:** La propiedad se muestra, pero la escritura no (porque está eliminada)

### 11.2. Actualizar Propiedad para Agregar Escritura

**Pasos:**
1. Crea una propiedad SIN escritura
2. Actualiza la propiedad
3. Asocia una escritura existente
4. **Resultado esperado:** La propiedad ahora tiene escritura asociada

### 11.3. Actualizar Propiedad para Cambiar Escritura

**Pasos:**
1. Crea una propiedad con escritura A
2. Actualiza la propiedad
3. Cambia a escritura B
4. **Resultado esperado:** La propiedad ahora tiene escritura B asociada

---

## 📊 Resumen de Pruebas

### Funcionalidades Core

- [x] CRUD completo de Propiedades
- [x] CRUD completo de Escrituras
- [x] Búsquedas por ID y campos específicos
- [x] Baja lógica (eliminado)
- [x] Relación 1→1 unidireccional
- [x] Transacciones (commit/rollback)
- [x] Validaciones de datos
- [x] Manejo de errores

### Validaciones

- [x] Campos obligatorios
- [x] Formatos de datos
- [x] Valores numéricos válidos
- [x] Enums (RES/COM)
- [x] Fechas válidas

### Integridad de Datos

- [x] Relación 1→1 se cumple
- [x] Baja lógica funciona
- [x] Transacciones mantienen consistencia
- [x] No se pierden datos en errores

---

## 🎯 Pruebas Adicionales Recomendadas

### Rendimiento (Opcional)

- Crear 100 propiedades y verificar tiempos
- Listar todas las propiedades con muchas escrituras
- Búsquedas con muchos registros

### Integración Completa

- Flujo completo: Crear escritura → Crear propiedad con esa escritura → Actualizar → Eliminar
- Verificar en DBeaver que todos los cambios se reflejan correctamente

---

## ✅ Checklist Final

Antes de considerar el sistema completamente probado:

- [ ] Todas las pruebas básicas pasan
- [ ] No hay errores en consola
- [ ] Los datos se persisten correctamente
- [ ] Las transacciones funcionan
- [ ] Las validaciones funcionan
- [ ] El manejo de errores es adecuado
- [ ] La relación 1→1 se cumple
- [ ] La baja lógica funciona correctamente

---

## 📝 Notas para la Entrega

Al entregar el trabajo práctico, asegúrate de:

1. **Documentar las pruebas realizadas**
2. **Incluir capturas de pantalla** de:
   - Menú funcionando
   - Operaciones CRUD exitosas
   - Manejo de errores
   - Base de datos en DBeaver mostrando los datos

3. **Explicar cualquier limitación** o comportamiento inesperado encontrado

4. **Incluir el diagrama UML** (si lo tienes)

---

¡Con estas pruebas deberías verificar que todo el sistema funciona correctamente! 🚀

Si encuentras algún problema durante las pruebas, anótalo y podemos resolverlo juntos.

