# Guía: Acceso a Base de Datos H2 - AutoElite

## 📊 Problema Original
La BD estaba configurada como **in-memory** (`jdbc:h2:mem:autoelite`), lo que significa que solo existía mientras la aplicación estaba corriendo.

## ✅ Solución Implementada
Se cambió la configuración a **BD persistente en archivo**:
- **Nueva URL:** `jdbc:h2:./data/autoelite`
- **Ubicación:** Carpeta `data/` en el directorio raíz del proyecto
- **Ventaja:** Los datos se guardan en disco y pueden accederse desde H2 Console

## 🔧 Pasos para Ver las Contraseñas de USUARIOS

### Paso 1: Compilar con los cambios
```bash
cd d:\Proyecto Final
compile.bat
```

### Paso 2: Ejecutar AutoElite (opcional)
Ejecuta la aplicación una sola vez:
```bash
run.bat
```
Esto inicializa la BD con los datos de prueba. Después puedes cerrar la aplicación.

### Paso 3: Abrir H2 Console
En la carpeta del proyecto, ejecuta:

**Windows:**
```bash
h2console.bat
```

**Linux/Mac:**
```bash
chmod +x h2console.sh
./h2console.sh
```

### Paso 4: Conectarse
Se abrirá automáticamente en el navegador (http://localhost:8082):

```
JDBC URL:  jdbc:h2:./data/autoelite
User:      sa
Password:  (dejar en blanco)
```

Haz click en **"Connect"**

### Paso 5: Ver las contraseñas
En la consola web, ejecuta esta consulta SQL:

```sql
SELECT id, username, password, rol, nombre_completo FROM usuarios;
```

Verás:
```
ID | USERNAME   | PASSWORD | ROL       | NOMBRE_COMPLETO
1  | admin      | 123456   | ADMIN     | Administrador Sistema
2  | vendedor1  | 123456   | VENDEDOR  | Juan García
3  | vendedor2  | 123456   | VENDEDOR  | María López
```

## 🔐 Cambiar Contraseña desde H2 Console

Si deseas cambiar una contraseña, ejecuta:

```sql
UPDATE usuarios SET password = 'nueva_contraseña' WHERE username = 'admin';
```

Luego presiona **"Run"** (Ctrl+Enter)

## 📋 Estructura de Tablas Disponibles

### Tabla: `usuarios`
```
SELECT * FROM usuarios;
```

### Tabla: `vehiculos`
```
SELECT * FROM vehiculos;
```

### Tabla: `sucursales`
```
SELECT * FROM sucursales;
```

### Tabla: `imagenes`
```
SELECT * FROM imagenes;
```

## ⚠️ Notas Importantes

1. **H2 Console y AutoElite no pueden estar activos simultáneamente** (ocupan la misma BD)
   - Cierra AutoElite antes de abrir H2 Console
   - O cierra H2 Console antes de ejecutar AutoElite

2. **Datos persistentes:** A partir de ahora, todos los cambios se guardan en `data/autoelite.mv.db`

3. **Archivos de BD:**
   - `data/autoelite.mv.db` - Archivo de datos
   - `data/autoelite.trace.db` - Log de errores (opcional)

4. **Cambios en el código:**
   - Solo se modificó `ConexionDB.java` (línea 12)
   - El resto de la aplicación sigue funcionando igual

## 🧹 Limpiar la Base de Datos

Si quieres empezar desde cero, borra la carpeta `data/`:
```bash
rmdir /s data
```

Luego ejecuta AutoElite nuevamente para reinicializar.

## 🆘 Solución de Problemas

### ❌ Error: "Database not found"
**Solución:** Asegúrate de haber ejecutado AutoElite al menos una vez para crear los archivos de BD

### ❌ Puerto 8082 en uso
**Solución:** El puerto está ocupado. Espera o cierra H2 Console y reinicia

### ❌ No aparecen los datos
**Solución:** Ejecuta `run.bat` una vez para que se populate la BD con init.sql

---

**¡Ahora puedes acceder y modificar la BD desde la consola web H2!** 🎉
