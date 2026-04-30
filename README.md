# AutoElite - Sistema de Concesionarios de Vehículos Usados

## Descripción
AutoElite es una aplicación de escritorio desarrollada en Java Swing para la gestión integral de concesionarios de vehículos usados. Incluye módulos de administración de inventario, gestión de sucursales, galería multimedia y dashboard estadístico.

## Requisitos del Sistema
- **Java JDK 17 o superior**
- **Windows, Linux o macOS**
- 200 MB de espacio en disco

## Instalación

1. Asegúrate de tener instalado Java JDK 17+:
   ```bash
   java -version
   javac -version
   ```

2. El proyecto ya incluye H2 Database Engine en la carpeta `lib/`

## Compilación

### En Windows:
```bash
compile.bat
```

### En Linux/macOS:
```bash
chmod +x compile.sh
./compile.sh
```

## Ejecución

### En Windows:
```bash
run.bat
```

### En Linux/macOS:
```bash
chmod +x run.sh
./run.sh
```

## Credenciales de Prueba

### Admin:
- **Usuario:** admin
- **Contraseña:** 123456

### Vendedor:
- **Usuario:** vendedor1
- **Contraseña:** 123456

## Estructura del Proyecto

```
Proyecto Final/
├── src/
│   ├── Main.java                 (Punto de entrada)
│   ├── model/                    (Modelos de datos)
│   │   ├── Vehiculo.java        (Clase abstracta)
│   │   ├── Auto.java
│   │   ├── Motocicleta.java
│   │   ├── Camion.java
│   │   ├── Sucursal.java
│   │   ├── Usuario.java
│   │   └── Imagen.java
│   ├── dao/                      (Data Access Objects)
│   │   ├── ConexionDB.java
│   │   ├── VehiculoDAO.java
│   │   ├── SucursalDAO.java
│   │   ├── UsuarioDAO.java
│   │   └── ImagenDAO.java
│   ├── controller/               (Controladores MVC)
│   │   ├── LoginController.java
│   │   ├── MainController.java
│   │   ├── InventarioController.java
│   │   ├── DashboardController.java
│   │   ├── SucursalController.java
│   │   └── GaleriaController.java
│   ├── view/                     (Interfaces gráficas)
│   │   ├── LoginView.java
│   │   ├── MainView.java
│   │   ├── InventarioView.java
│   │   ├── DashboardView.java
│   │   ├── SucursalView.java
│   │   └── GaleriaView.java
│   └── exceptions/               (Excepciones personalizadas)
│       ├── AuthFailedException.java
│       ├── InvalidPriceException.java
│       └── DatabaseException.java
├── resources/
│   ├── init.sql                  (Script de inicialización)
│   └── images/                   (Carpeta para imágenes)
├── lib/
│   └── h2-2.4.240.jar           (Base de datos H2)
├── compile.bat                   (Script compilación Windows)
├── run.bat                        (Script ejecución Windows)
├── compile.sh                     (Script compilación Linux/Mac)
└── run.sh                         (Script ejecución Linux/Mac)
```

## Características Principales

### Módulo 1: Login y Seguridad
- Autenticación de usuarios contra base de datos
- Roles: ADMIN y VENDEDOR
- Manejo de sesiones

### Módulo 2: Gestión de Inventario (CRUD)
- Tabla de vehículos con filtros avanzados
- Búsqueda en tiempo real por marca/modelo
- Filtros por sucursal, tipo de vehículo y rango de precios
- Agregar, editar y eliminar vehículos (solo ADMIN)
- Baja lógica de vehículos

### Módulo 3: Galería Multimedia
- Visualización de imágenes de vehículos
- Botones Anterior/Siguiente
- Agregar múltiples imágenes
- Establecer imagen principal
- Eliminar imágenes

### Módulo 4: Dashboard Estadístico
- KPIs: Stock Total, Valor del Inventario, Sucursal Principal
- Gráficos de porcentaje de stock por tipo de vehículo

### Módulo 5: Gestión de Sucursales
- CRUD completo de sucursales
- Exportación de inventario a CSV

## Arquitectura

### Patrón MVC
- **Model:** Clases en paquete `model/`
- **View:** Clases en paquete `view/`
- **Controller:** Clases en paquete `controller/`

### Base de Datos
- **Tipo:** H2 Database Engine (en memoria)
- **Modo:** jdbc:h2:mem:autoelite
- **Inicialización:** Script automático al iniciar

### Encapsulamiento
- Todos los atributos de clase son `private`
- Acceso mediante getters y setters públicos

### Polimorfismo
- Métodos `calcularImpuesto()`, `obtenerTipo()`, `mostrarDetalles()` implementados en subclases

## Excepciones Personalizadas

- `AuthFailedException`: Errores de autenticación
- `InvalidPriceException`: Precios inválidos
- `DatabaseException`: Errores de base de datos

## Manejo de Archivos

- Imágenes: Copia automática a `resources/images/`
- Exportación: CSV con FileWriter y PrintWriter
- Ruta de imágenes almacenada en base de datos

## Notas Importantes

1. **Base de datos en memoria:** Los datos se pierden al cerrar la aplicación (por diseño H2 en memoria)
2. **Rutas de imágenes:** Las imágenes se guardan en `resources/images/` con nombres únicos basados en timestamp
3. **Validaciones:** Se implementan validaciones de precios negativos y campos vacíos
4. **Permisos:** Los VENDEDOREs solo pueden ver el inventario, los ADMINs pueden realizar CRUD completo

## Solución de Problemas

### Error: "No fue posible encontrar la clase Main"
- Asegúrate de ejecutar desde el directorio raíz del proyecto
- Verifica que el directorio `bin/` existe después de compilar

### Error: "Unable to load H2 driver"
- Verifica que `lib/h2-2.4.240.jar` está en el directorio lib/

### Las imágenes no se guardan
- Asegúrate de que la carpeta `resources/images/` existe y tiene permisos de escritura

## Autor
Desarrollado como proyecto final de demostración de Java Swing + H2 Database.

## Licencia
Este proyecto es de código abierto y está disponible para propósitos educativos.
