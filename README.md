# Sistema de Gestión de Propiedades

## 📋 Descripción del Dominio

Este sistema gestiona **Propiedades** inmobiliarias y sus **Escrituras Notariales** asociadas. El dominio modela la relación entre propiedades y sus documentos legales notariales, permitiendo realizar operaciones CRUD completas sobre ambas entidades.

### Entidades del Dominio

#### Propiedad
Representa una propiedad inmobiliaria con los siguientes atributos:
- **Padrón Catastral**: Identificador único de la propiedad
- **Dirección**: Ubicación física de la propiedad
- **Superficie (m²)**: Área total en metros cuadrados
- **Destino**: Tipo de uso (RES: Residencial, COM: Comercial)
- **Antigüedad**: Años desde su construcción
- **Escritura Notarial**: Documento legal asociado (relación 1→1 opcional)

#### Escritura Notarial
Representa el documento legal notarial con los siguientes atributos:
- **Número de Escritura**: Identificador único del documento
- **Fecha**: Fecha de otorgamiento
- **Notaría**: Nombre de la notaría que la emitió
- **Tomo**: Número de tomo del registro
- **Folio**: Número de folio del registro
- **Observaciones**: Notas adicionales sobre el documento

### Relación 1→1 Unidireccional

La relación entre **Propiedad** y **EscrituraNotarial** es:
- **Unidireccional**: Solo `Propiedad` referencia a `EscrituraNotarial`
- **1→1**: Una propiedad puede tener como máximo una escritura, y una escritura puede estar asociada a una sola propiedad
- **Opcional**: Una propiedad puede existir sin escritura asociada

---

## 🛠️ Requisitos del Sistema

### Software Requerido

- **Java**: Versión 21 (recomendado) o superior
- **MySQL**: Versión 8.0 o superior
- **MySQL JDBC Driver**: Versión 8.0 o superior
- **IDE**: NetBeans (recomendado) o cualquier IDE compatible con Java
- **Gestor de Base de Datos**: DBeaver, MySQL Workbench o similar (opcional)

### Dependencias

- `mysql-connector-j-8.0.xx.jar` o superior (incluido en el proyecto)

---

## 🗄️ Configuración de la Base de Datos

### Paso 1: Instalar MySQL

Si no tienes MySQL instalado:
1. Descarga MySQL Installer desde: https://dev.mysql.com/downloads/installer/
2. Instala MySQL Server siguiendo el asistente
3. Configura una contraseña para el usuario `root`
4. Asegúrate de que el servicio MySQL esté ejecutándose

### Paso 2: Crear la Base de Datos

**Usando DBeaver**

1. Abre DBeaver y conéctate a MySQL
2. Crea un nuevo script SQL (`Ctrl+` o `SQL Editor` → `Nuevo script SQL`)
3. Abre el archivo `database/create_database.sql`
4. Copia y pega el contenido en el editor SQL
5. Ejecuta el script (`Ctrl+Alt+X` o botón ▶️)
6. Verifica que se crearon las tablas:
   - `escritura_notarial`
   - `propiedad`


### Paso 3: Insertar Datos de Prueba

1. Abre un nuevo script SQL en DBeaver
2. Abre el archivo `database/insert_datos_prueba.sql`
3. Copia y pega el contenido
4. Ejecuta el script
5. Verifica los datos:
   - 5 escrituras notariales
   - 6 propiedades (4 con escritura asociada, 2 sin)


### Verificación de la Base de Datos

Ejecuta estas consultas para verificar:

```sql
USE inmobiliaria;

-- Ver escrituras
SELECT * FROM escritura_notarial WHERE eliminado = FALSE;

-- Ver propiedades con sus escrituras
SELECT p.id, p.padron_catastral, p.direccion, e.nro_escritura, e.notaria
FROM propiedad p
LEFT JOIN escritura_notarial e ON p.escritura_id = e.id
WHERE p.eliminado = FALSE;
```

---

## ⚙️ Configuración del Proyecto

### Paso 1: Configurar Credenciales de Base de Datos

1. Abre el archivo: `src/proyectointegrador/Config/db.properties`
2. Modifica las credenciales según tu configuración:

```properties
db.url=jdbc:mysql://localhost:3306/inmobiliaria?useSSL=false&serverTimezone=UTC
db.user=root
db.password=tu_contraseña_aqui
db.driver=com.mysql.cj.jdbc.Driver
```

**Credenciales de Prueba:**
- **Usuario**: `root`
- **Contraseña**: La que configuraste durante la instalación de MySQL
- **Base de datos**: `inmobiliaria`
- **Puerto**: `3306` (por defecto)

### Paso 2: Verificar Driver JDBC

1. Verifica que el driver MySQL JDBC esté en las librerías del proyecto:
   - Clic derecho en el proyecto → `Properties` → `Libraries`
   - Debe aparecer `mysql-connector-j-8.0.xx.jar` o similar
2. Si no está, agrégalo:
   - `Add JAR/Folder...` → Selecciona el archivo `.jar` del driver

---

## 🚀 Compilación y Ejecución

### Compilar el Proyecto

**En NetBeans:**
1. Clic derecho en el proyecto → `Clean and Build`
2. O presiona `F11`
3. Verifica que no haya errores de compilación

### Ejecutar la Aplicación

**En NetBeans:**
1. Clic derecho en `ProyectoIntegrador.java` → `Run File`
2. O presiona `F6`
3. O ejecuta la clase principal: `proyectointegrador.ProyectoIntegrador`


---

## 📖 Flujo de Uso de la Aplicación

### Menú Principal

Al ejecutar la aplicación, verás el menú principal:

```
==================================================
        SISTEMA DE GESTIÓN DE PROPIEDADES
==================================================
1. Gestionar Propiedades
2. Gestionar Escrituras Notariales
S. Salir
```

### Gestionar Propiedades

**Opciones disponibles:**
1. **Crear Propiedad**: Permite crear una nueva propiedad con o sin escritura asociada
2. **Buscar Propiedad por ID**: Busca una propiedad por su identificador
3. **Buscar Propiedad por Padrón Catastral**: Búsqueda por padrón catastral
4. **Listar Todas las Propiedades**: Muestra todas las propiedades activas
5. **Actualizar Propiedad**: Modifica los datos de una propiedad existente
6. **Eliminar Propiedad (lógico)**: Realiza baja lógica (no elimina físicamente)

**Ejemplo de creación:**
1. Selecciona `1. Crear Propiedad`
2. Ingresa los datos solicitados:
   - Padrón Catastral: `PC-007-2024`
   - Dirección: `Av. Ejemplo 123`
   - Superficie: `150.50`
   - Destino: `RES` (o `COM`)
   - Antigüedad: `10`
3. Si deseas asociar una escritura:
   - Responde `S` a "¿Desea asociar una escritura notarial?"
   - Ingresa `0` para crear una nueva escritura
   - Completa los datos de la escritura
   - O ingresa el ID de una escritura existente

### Gestionar Escrituras Notariales

**Opciones disponibles:**
1. **Crear Escritura Notarial**: Crea una nueva escritura
2. **Buscar Escritura por ID**: Busca por identificador
3. **Buscar Escritura por Número**: Búsqueda por número de escritura
4. **Listar Todas las Escrituras**: Muestra todas las escrituras activas
5. **Actualizar Escritura**: Modifica los datos de una escritura
6. **Eliminar Escritura (lógico)**: Realiza baja lógica

**Ejemplo de creación:**
1. Selecciona `1. Crear Escritura Notarial`
2. Ingresa los datos:
   - Número de Escritura: `ESC-006-2024`
   - Fecha: `2024-12-15` (formato: yyyy-MM-dd)
   - Notaría: `Notaría de Prueba`
   - Tomo: `Tomo 200` (opcional)
   - Folio: `Folio 300` (opcional)
   - Observaciones: `Escritura de prueba` (opcional)

### Características del Sistema

- **Baja Lógica**: Las eliminaciones no borran físicamente los registros, solo marcan `eliminado = true`
- **Validaciones**: El sistema valida campos obligatorios y formatos antes de guardar
- **Transacciones**: Las operaciones complejas (crear propiedad con escritura) se ejecutan en transacciones
- **Búsquedas**: Permite buscar por ID y por campos específicos (padrón catastral, número de escritura)
- **Relación 1→1**: Garantiza que cada propiedad tenga máximo una escritura asociada


---


## 🧪 Pruebas del Sistema

### Datos de Prueba Incluidos

El script `insert_datos_prueba.sql` incluye:
- **5 Escrituras Notariales** con datos variados
- **6 Propiedades**:
  - 4 con escritura asociada
  - 2 sin escritura asociada

### Casos de Prueba Recomendados

1. **CRUD Completo**: Crear, leer, actualizar y eliminar (lógico) ambas entidades
2. **Búsquedas**: Probar búsquedas por ID y campos específicos
3. **Validaciones**: Intentar crear registros sin campos obligatorios
4. **Relación 1→1**: Crear propiedad con escritura y verificar la relación
5. **Transacciones**: Verificar que las operaciones complejas se ejecutan correctamente

---


## 📝 Notas Adicionales

- El sistema utiliza **baja lógica**, por lo que los registros eliminados no se borran físicamente
- Las búsquedas solo muestran registros con `eliminado = false`
- La relación 1→1 se garantiza mediante restricción UNIQUE en la base de datos
- Todas las operaciones de escritura utilizan `PreparedStatement` para prevenir SQL injection

## 📽️ Video


## 📄 Informe

- **[Trabajo Práctico Integrador - G135.pdf](Trabajo%20Practico%20Integrador%20-%20G135.pdf)**

