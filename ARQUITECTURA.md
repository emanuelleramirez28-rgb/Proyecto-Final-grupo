# DOCUMENTACIÓN TÉCNICA - AutoElite

## 1. ARQUITECTURA DEL PROYECTO

### Patrón MVC (Modelo-Vista-Controlador)

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│   MODEL     │      │    VIEW      │      │ CONTROLLER  │
│  (Datos)    │  ←→  │  (Interfaz)  │  ←→  │  (Lógica)   │
└─────────────┘      └──────────────┘      └─────────────┘
      ↓
┌─────────────┐
│   DAO       │
│  (BD H2)    │
└─────────────┘
```

## 2. CAPAS DE LA APLICACIÓN

### Capa de Modelos (`model/`)
Define la estructura de datos del negocio:
- `Vehiculo`: Clase abstracta base con atributos comunes
- `Auto`, `Motocicleta`, `Camion`: Subclases que especializan Vehiculo
- `Sucursal`: Representa ubicaciones de la empresa
- `Usuario`: Datos de usuarios del sistema
- `Imagen`: Referencias a imágenes de vehículos

**Características:**
- Encapsulamiento: todos los atributos son `private`
- Getters/Setters para acceso controlado
- Herencia: subclases heredan de Vehiculo
- Polimorfismo: métodos abstractos implementados por subclases

### Capa de Acceso a Datos (`dao/`)
Gestiona la comunicación con la base de datos:
- `ConexionDB`: Patrón Singleton para conexión única
- `VehiculoDAO`: CRUD de vehículos con búsquedas y filtros
- `SucursalDAO`: CRUD de sucursales
- `UsuarioDAO`: CRUD de usuarios y autenticación
- `ImagenDAO`: CRUD de imágenes

**Características:**
- Uso de PreparedStatement para prevenir SQL injection
- Manejo de transacciones
- Métodos de búsqueda y filtrado complejos

### Capa de Controladores (`controller/`)
Coordina la lógica entre vistas y modelos:
- `LoginController`: Gestiona autenticación
- `MainController`: Coordinador principal de la aplicación
- `InventarioController`: Lógica del inventario
- `DashboardController`: Cálculo de estadísticas
- `SucursalController`: Gestión de sucursales
- `GaleriaController`: Manejo de imágenes

**Características:**
- Listeners para eventos de interfaz
- Control de acceso basado en roles
- Manejo de excepciones personalizado

### Capa de Vistas (`view/`)
Interfaces gráficas con Java Swing:
- `LoginView`: Pantalla de autenticación
- `MainView`: Ventana principal con pestañas
- `InventarioView`: Tabla y filtros de vehículos
- `DashboardView`: KPIs y gráficos
- `SucursalView`: Gestión de sucursales
- `GaleriaView`: Visualización de imágenes

**Características:**
- BorderLayout y GridBagLayout
- JTable con modelo personalizado
- Validación de entrada
- Feedback visual (JOptionPane)

## 3. BASE DE DATOS H2

### Diagrama Entidad-Relación (ER)

```
┌──────────────────┐
│   SUCURSALES     │
├──────────────────┤
│ id (PK)          │
│ nombre           │
│ ciudad           │
│ direccion        │
└──────┬───────────┘
       │ (1:N)
       │
┌──────▼───────────┐         ┌──────────────────┐
│   VEHICULOS      │  (1:N)  │   IMAGENES       │
├──────────────────┤────────→├──────────────────┤
│ id (PK)          │         │ id (PK)          │
│ marca            │         │ id_vehiculo (FK) │
│ modelo           │         │ ruta             │
│ año              │         │ es_principal     │
│ precio           │         └──────────────────┘
│ id_sucursal (FK) │
│ activo           │
│ tipo             │
│ [atributos...]   │
└──────────────────┘

┌──────────────────┐
│   USUARIOS       │
├──────────────────┤
│ id (PK)          │
│ username         │
│ password         │
│ rol              │
│ nombre_completo  │
└──────────────────┘
```

### Tablas

| Tabla | Propósito | Campos |
|-------|-----------|--------|
| `sucursales` | Ubicaciones de la empresa | id, nombre, ciudad, direccion |
| `usuarios` | Cuentas de usuario | id, username, password, rol, nombre_completo |
| `vehiculos` | Inventario de vehículos | id, marca, modelo, año, precio, id_sucursal, activo, tipo, [específicos] |
| `imagenes` | Fotos de vehículos | id, id_vehiculo, ruta, es_principal |

## 4. FLUJO DE EJECUCIÓN

```
1. INICIO
   └─→ Main.main() se ejecuta
       └─→ Inicializa UIManager
           └─→ Muestra LoginView

2. LOGIN
   └─→ Usuario ingresa credenciales
       └─→ LoginController.autenticar()
           └─→ UsuarioDAO.autenticar()
               └─→ Consulta base de datos
                   └─→ Si es exitoso: muestra MainView

3. APLICACIÓN PRINCIPAL
   └─→ MainController inicializa controladores específicos
       └─→ Carga datos iniciales
           └─→ InventarioController.cargarVehiculos()
           └─→ DashboardController.actualizarEstadisticas()
               └─→ SucursalController.cargarSucursales()

4. OPERACIONES
   └─→ Usuario interactúa con vistas
       └─→ Listeners disparan acciones en controladores
           └─→ Controladores usan DAOs para modificar datos
               └─→ Vistas se actualizan con nuevos datos

5. CIERRE
   └─→ Usuario hace logout
       └─→ MainController.logout()
           └─→ Vuelve a mostrar LoginView
```

## 5. SEGURIDAD

### Autenticación
- Validación de usuario/contraseña contra BD
- Excepción personalizada `AuthFailedException`
- Sesión almacenada en variable `usuarioActual`

### Autorización
- Roles: `ADMIN` y `VENDEDOR`
- Control de acceso en controladores
- Botones deshabilitados según rol

### Prevención de Inyección SQL
- Uso obligatorio de `PreparedStatement`
- Parámetros vinculados (?)

## 6. MANEJO DE EXCEPCIONES

### Jerarquía de Excepciones

```
Exception
├── AuthFailedException      (Errores de autenticación)
├── InvalidPriceException    (Precios inválidos)
├── DatabaseException        (Errores de BD)
└── [Excepciones de Java]
    ├── SQLException         (Errores JDBC)
    ├── IOException          (Errores de archivos)
    └── NumberFormatException (Conversión inválida)
```

### Estrategia de Manejo

1. **DAO**: Lanza `DatabaseException` envolviendo `SQLException`
2. **Controller**: Atrapa `DatabaseException` y muestra al usuario
3. **Vista**: Muestra mensajes de error amigables

## 7. PATRONES DE DISEÑO UTILIZADOS

### 1. Singleton (ConexionDB)
```java
public static synchronized ConexionDB getInstance() {
    if (instancia == null) {
        instancia = new ConexionDB();
    }
    return instancia;
}
```
**Propósito:** Una única conexión a BD en toda la aplicación

### 2. MVC
**Propósito:** Separación de responsabilidades

### 3. DAO (Data Access Object)
**Propósito:** Abstraer la lógica de acceso a datos

### 4. Template Method (en AbstractVehiculo)
```java
public abstract double calcularImpuesto();
public abstract String obtenerTipo();
```
**Propósito:** Definir estructura común, permitir especialización

## 8. CARACTERÍSTICAS AVANZADAS

### Búsqueda y Filtrado Dinámico
```
Usuario digita en searchField
    ↓
searchButton.addActionListener()
    ↓
realizarBusqueda()
    ↓
VehiculoDAO.buscar(termino)
    ↓
LIKE '%termino%' en SQL
    ↓
Aplicar filtros adicionales
    ↓
Actualizar JTable
```

### Galería de Imágenes
```
Usuario selecciona vehículo → abrirGaleria()
    ↓
ImagenDAO.obtenerImagenesVehiculo(id)
    ↓
GaleriaView muestra imagen en JLabel
    ↓
Botones Anterior/Siguiente navegan
    ↓
JFileChooser para agregar nuevas
    ↓
Files.copy() copia a resources/images/
    ↓
ImagenDAO.insertarImagen() guarda en BD
```

### Dashboard Estadístico
```
DashboardController.actualizarEstadisticas()
    ↓
VehiculoDAO.obtenerTodos() → List<Vehiculo>
    ↓
Iterar y contar:
- totalStock
- totalValor (sum precios)
- vehiculosPorSucursal (map)
- Por tipo (Auto/Moto/Camion)
    ↓
DashboardView.actualizarKPIs()
DashboardView.actualizarGraficoStock()
```

## 9. OPTIMIZACIONES

1. **Caché de datos:** Las listas se cargan una sola vez al iniciar
2. **Búsqueda en memoria:** Se filtran en Java antes de mostrar (evita queries repetidas)
3. **Índices en BD:** PK en tablas principales
4. **Prepared Statements:** Compilación una sola vez de queries

## 10. LIMITACIONES Y MEJORAS FUTURAS

### Limitaciones Actuales
- BD en memoria (datos se pierden al cerrar)
- No hay backup/restore
- Interfaz básica (sin iconografía)
- Historial de cambios

### Mejoras Sugeridas
1. Migrar a BD persistente (MySQL, PostgreSQL)
2. Agregar auditoría (tabla de logs)
3. Mejorar UI con Material Design
4. Agregar búsqueda avanzada
5. Exportación a PDF
6. Soporte multi-idioma
7. Cifrado de contraseñas (hash)

---

**Fecha de documentación:** Abril 2026
**Versión:** 1.0.0
