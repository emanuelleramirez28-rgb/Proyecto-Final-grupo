# GUÍA DE INICIO RÁPIDO - AutoElite

## 1. PREPARACIÓN RÁPIDA (2 minutos)

### Verificar Java
```bash
java -version
javac -version
```
Debe ser Java 17 o superior.

### Navegar al directorio del proyecto
```bash
cd "d:\Proyecto Final"
```

## 2. COMPILAR (1 minuto)

### Windows:
```bash
compile.bat
```

### Linux/Mac:
```bash
chmod +x compile.sh
./compile.sh
```

**Resultado esperado:**
```
[*] Compilación exitosa!
[*] Archivos compilados en: bin/
```

## 3. EJECUTAR (Inmediato)

### Windows:
```bash
run.bat
```

### Linux/Mac:
```bash
./run.sh
```

**Resultado esperado:**
- Se abre ventana de login
- Base de datos H2 se inicializa automáticamente

## 4. LOGIN DE PRUEBA

### Opción 1: Admin (Full Access)
```
Usuario: admin
Contraseña: 123456
```

### Opción 2: Vendedor (View Only)
```
Usuario: vendedor1
Contraseña: 123456
```

## 5. PRIMEROS PASOS EN LA APP

### Como ADMIN:
1. ✅ **Dashboard** → Ver KPIs y estadísticas
2. ✅ **Inventario** → Ver, filtrar, agregar vehículos
3. ✅ **Galería** → Ver imágenes de vehículos
4. ✅ **Sucursales** → Gestionar sucursales

### Como VENDEDOR:
1. ✅ **Dashboard** → Ver estadísticas
2. ✅ **Inventario** → Ver y buscar vehículos
3. ✅ **Galería** → Ver imágenes de vehículos

## 6. DATOS DE PRUEBA CARGADOS

La BD se inicializa con:
- **3 sucursales** en diferentes ciudades
- **3 usuarios** (1 admin, 2 vendedores)
- **13 vehículos**:
  - 5 Autos (Toyota, Honda, Ford, Chevrolet, VW)
  - 4 Motocicletas (Yamaha, Honda, Kawasaki, Harley)
  - 3 Camiones (Volvo, Mercedes, Scania)

## 7. PRUEBA DE FUNCIONALIDADES

### Búsqueda y Filtros
1. En pestña **Inventario**
2. Escribe "Toyota" en el campo de búsqueda
3. Haz click en "Buscar"
4. Filtra por sucursal o tipo de vehículo
5. Prueba el rango de precios

### Dashboard
1. Vé a pestaña **Dashboard**
2. Verás:
   - Stock Total
   - Valor del Inventario
   - Sucursal con más vehículos
   - Gráficos de distribución por tipo

### Galería (Admin)
1. En **Inventario**, selecciona un vehículo
2. Haz click en botón "📷 Galería"
3. Navega con Anterior/Siguiente
4. Prueba agregar imágenes:
   - Click "+ Agregar Imagen"
   - Selecciona un .jpg o .png
   - La imagen se copia a `resources/images/`

## 8. FUNCIONALIDADES AVANZADAS (Admin)

### Agregar Vehículo
- Click "✏ Agregar"
- Completa formulario (funcionalidad extensible)

### Editar Vehículo
- Selecciona un vehículo
- Click "✎ Editar"
- Modifica datos

### Eliminar Vehículo
- Selecciona un vehículo
- Click "✕ Eliminar"
- Confirma la operación
- Se realiza baja lógica (activo = false)

### Gestionar Sucursales
- Vé a pestaña **Sucursales**
- Agregar, editar o eliminar sucursales
- Exportar inventario a CSV

## 9. ARCHIVOS IMPORTANTES

```
src/Main.java              ← Punto de entrada
src/model/                 ← Modelos de datos
src/dao/                   ← Acceso a base de datos
src/controller/            ← Lógica de negocio
src/view/                  ← Interfaces gráficas
lib/h2-2.4.240.jar        ← Base de datos
resources/init.sql        ← Datos iniciales
resources/images/         ← Imágenes de vehículos
```

## 10. SOLUCIÓN DE PROBLEMAS COMUNES

### ❌ Error: "javac is not recognized"
**Solución:** Instalar Java JDK (no JRE) y agregar a PATH

### ❌ Error: "Cannot find Main class"
**Solución:** Asegúrate de estar en directorio correcto y haber compilado

### ❌ Error: "H2 driver not found"
**Solución:** Verificar que `lib/h2-2.4.240.jar` existe

### ❌ La aplicación no responde
**Solución:** Esperar a que termine de cargar datos iniciales

### ❌ Las imágenes no se guardan
**Solución:** Crear carpeta `resources/images/` manualmente

## 11. CONTACTO Y SOPORTE

Para reportar bugs o sugerencias, consulta con el equipo de desarrollo.

---

**Tiempo total de setup:** ~5 minutos
**Requisito único:** Java JDK 17+
**¡Disfruta usando AutoElite!** 🚗
