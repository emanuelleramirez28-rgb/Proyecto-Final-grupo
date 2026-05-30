# AutoElite - Sistema de Concesionarios de Vehículos Usados

##   Descripción
AutoElite es una aplicación de escritorio desarrollada en Java Swing para la gestión integral de concesionarios de vehículos usados. Incluye módulos de administración de inventario, gestión de sucursales, galería multimedia y dashboard estadístico.

La aplicación sigue el patrón de diseño **MVC (Modelo-Vista-Controlador)** y utiliza **H2 Database Engine** como base de datos en memoria, lo que elimina la necesidad de instalar un servidor de base de datos externo. Está orientada a pequeñas y medianas empresas del sector automotriz que necesiten digitalizar su proceso de gestión de inventario y sucursales de forma rápida y sin infraestructura adicional.

##  Requisitos del Sistema
- **Java JDK 17 o superior** — versiones anteriores no garantizan compatibilidad con las APIs utilizadas
- **Windows, Linux o macOS** — probado en Windows 10/11, Ubuntu 20.04+ y macOS Ventura
- **200 MB de espacio en disco** — incluye binarios compilados, dependencias y carpeta de imágenes
- **RAM mínima recomendada:** 512 MB disponibles para la JVM
- **Resolución de pantalla mínima:** 1024 × 768 px para visualizar correctamente todos los módulos

##  Instalación

1. Asegúrate de tener instalado Java JDK 17+:
   ```bash
   java -version
   javac -version
   ```
   Ambos comandos deben mostrar una versión **17 o superior**. Si no está instalado, descárgalo desde [https://adoptium.net](https://adoptium.net) (distribución recomendada: Eclipse Temurin).

2. El proyecto ya incluye H2 Database Engine en la carpeta `lib/` — **no es necesario instalarlo por separado**.

3. Clona o descarga el proyecto y descomprímelo en una carpeta de tu elección. Asegúrate de que la estructura de directorios se mantiene intacta (especialmente `lib/`, `src/` y `resources/`).

##  Compilación

Los scripts de compilación incluyen automáticamente el JAR de H2 en el classpath y guardan los `.class` en la carpeta `bin/`. Asegúrate de ejecutarlos **desde el directorio raíz del proyecto** (donde están los archivos `.bat` / `.sh`).

###  En Windows:
```bash
compile.bat
```

###  En Linux/macOS:
```bash
chmod +x compile.sh
./compile.sh
```

> Si prefieres compilar manualmente, el comando equivalente es:
> ```bash
> javac -cp lib/h2-2.4.240.jar -d bin src/**/*.java src/Main.java
> ```

##  Ejecución

Al iniciar, la aplicación ejecuta automáticamente el script `resources/init.sql` que crea las tablas y carga los datos de prueba en la base de datos en memoria.

###  En Windows:
```bash
run.bat
```

###  En Linux/macOS:
```bash
chmod +x run.sh
./run.sh
```

> Ejecución manual equivalente:
> ```bash
> java -cp bin:lib/h2-2.4.240.jar Main
> ```
> En Windows reemplaza `:` por `;` en el classpath.

##  Credenciales de Prueba

Estas credenciales son cargadas por `init.sql` al iniciar la aplicación. Sirven para explorar las funcionalidades según el rol.

### Admin:
- **Usuario:** admin
- **Contraseña:** 123456
- **Permisos:** acceso total — puede crear, editar y eliminar vehículos, gestionar sucursales y ver el dashboard completo.

### Vendedor:
- **Usuario:** vendedor1
- **Contraseña:** 123456
- **Permisos:** solo lectura del inventario y la galería — no puede realizar modificaciones.

## Estructura del Proyecto

```
Proyecto Final/
├── src/
│   ├── Main.java                 (Punto de entrada — lanza la ventana de login)
│   ├── model/                    (Modelos de datos — clases POJO con getters/setters)
│   │   ├── Vehiculo.java        (Clase abstracta base con atributos comunes)
│   │   ├── Auto.java            (Extiende Vehiculo — tipo automóvil)
│   │   ├── Motocicleta.java     (Extiende Vehiculo — tipo motocicleta)
│   │   ├── Camion.java          (Extiende Vehiculo — tipo camión/carga)
│   │   ├── Sucursal.java        (Datos de cada sucursal del concesionario)
│   │   ├── Usuario.java         (Datos y rol del usuario autenticado)
│   │   └── Imagen.java          (Ruta y metadatos de imágenes de vehículos)
│   ├── dao/                      (Data Access Objects — acceso a H2 vía JDBC)
│   │   ├── ConexionDB.java      (Singleton de conexión y carga del init.sql)
│   │   ├── VehiculoDAO.java     (CRUD de vehículos con filtros)
│   │   ├── SucursalDAO.java     (CRUD de sucursales y exportación CSV)
│   │   ├── UsuarioDAO.java      (Autenticación y consulta de usuarios)
│   │   └── ImagenDAO.java       (Inserción, consulta y eliminación de imágenes)
│   ├── controller/               (Controladores MVC — lógica de negocio)
│   │   ├── LoginController.java          (Valida credenciales y gestiona sesión)
│   │   ├── MainController.java           (Navegación entre módulos)
│   │   ├── InventarioController.java     (Lógica de filtros, búsqueda y CRUD)
│   │   ├── DashboardController.java      (Cálculo de KPIs y estadísticas)
│   │   ├── SucursalController.java       (Gestión y exportación de sucursales)
│   │   └── GaleriaController.java        (Carga y navegación de imágenes)
│   ├── view/                     (Interfaces gráficas Java Swing)
│   │   ├── LoginView.java               (Pantalla de inicio de sesión)
│   │   ├── MainView.java                (Ventana principal con menú lateral)
│   │   ├── InventarioView.java          (Tabla de vehículos con filtros)
│   │   ├── DashboardView.java           (KPIs y gráfico de barras/pastel)
│   │   ├── SucursalView.java            (Lista y formulario de sucursales)
│   │   └── GaleriaView.java             (Visor de imágenes con navegación)
│   └── exceptions/               (Excepciones personalizadas del dominio)
│       ├── AuthFailedException.java     (Credenciales incorrectas o usuario inactivo)
│       ├── InvalidPriceException.java   (Precio negativo o fuera de rango)
│       └── DatabaseException.java       (Errores de conexión o consulta SQL)
├── resources/
│   ├── init.sql                  (Script DDL + INSERT de datos de prueba)
│   └── images/                   (Carpeta destino de imágenes copiadas)
├── lib/
│   └── h2-2.4.240.jar           (Base de datos H2 — no requiere instalación)
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
- Validación de campos vacíos antes de consultar la base de datos
- Bloqueo de acceso a módulos de edición cuando el rol es VENDEDOR
- Mensaje de error descriptivo al ingresar credenciales incorrectas

### Módulo 2: Gestión de Inventario (CRUD)
- Tabla de vehículos con filtros avanzados
- Búsqueda en tiempo real por marca/modelo
- Filtros por sucursal, tipo de vehículo y rango de precios
- Agregar, editar y eliminar vehículos (solo ADMIN)
- Baja lógica de vehículos (el registro permanece en BD con estado inactivo)
- Validación de precio negativo lanzando `InvalidPriceException`
- Los campos de formulario validan que no queden vacíos antes de guardar

### Módulo 3: Galería Multimedia
- Visualización de imágenes de vehículos
- Botones Anterior/Siguiente para navegar entre fotos
- Agregar múltiples imágenes desde el sistema de archivos local
- Establecer imagen principal (destacada en la tabla de inventario)
- Eliminar imágenes individuales con confirmación previa
- Las imágenes se copian automáticamente a `resources/images/` con nombre único basado en timestamp para evitar colisiones

### Módulo 4: Dashboard Estadístico
- KPIs: Stock Total, Valor del Inventario, Sucursal Principal
- Gráficos de porcentaje de stock por tipo de vehículo (Auto / Moto / Camión)
- Los KPIs se recalculan cada vez que se accede al módulo para reflejar cambios recientes

### Módulo 5: Gestión de Sucursales
- CRUD completo de sucursales (nombre, dirección, teléfono, encargado)
- Exportación de inventario a CSV por sucursal usando `FileWriter` y `PrintWriter`
- El archivo CSV se guarda en el directorio raíz del proyecto con el nombre de la sucursal

## Arquitectura

### Patrón MVC
- **Model:** Clases en paquete `model/` — representan las entidades del dominio (Vehiculo, Sucursal, Usuario, Imagen). Solo contienen datos y no tienen lógica de negocio.
- **View:** Clases en paquete `view/` — construidas con Java Swing. Se comunican con su controller correspondiente a través de listeners y nunca acceden directamente al DAO.
- **Controller:** Clases en paquete `controller/` — reciben eventos de la vista, invocan el DAO correspondiente y actualizan la vista con el resultado.

### Base de Datos
- **Tipo:** H2 Database Engine (en memoria)
- **Modo:** `jdbc:h2:mem:autoelite` — la base de datos existe únicamente mientras la JVM esté activa
- **Inicialización:** Script automático al iniciar — `ConexionDB.java` ejecuta `init.sql` en el primer `getConnection()`
- **Driver:** Incluido en `lib/h2-2.4.240.jar`, no requiere configuración adicional

### Encapsulamiento
- Todos los atributos de clase son `private`
- Acceso mediante getters y setters públicos
- Los controladores no acceden directamente a los atributos del modelo, siempre usan sus métodos de acceso

### Polimorfismo
- Métodos `calcularImpuesto()`, `obtenerTipo()`, `mostrarDetalles()` implementados en subclases
- `Vehiculo` es abstracta — no se puede instanciar directamente; siempre se trabaja con `Auto`, `Motocicleta` o `Camion`
- Los DAOs reciben y retornan referencias de tipo `Vehiculo`, aprovechando el polimorfismo para manejar los tres tipos con el mismo código

## Excepciones Personalizadas

- `AuthFailedException`: Se lanza cuando las credenciales no coinciden con ningún usuario en la base de datos, o cuando el usuario está marcado como inactivo.
- `InvalidPriceException`: Se lanza cuando se intenta guardar un vehículo con precio negativo o igual a cero.
- `DatabaseException`: Envuelve las `SQLException` de JDBC para desacoplar la capa DAO del resto de la aplicación. Incluye el mensaje original como causa.

## Manejo de Archivos

- **Imágenes:** Al agregar una imagen desde la galería, se copia automáticamente al directorio `resources/images/` con un nombre único generado a partir del timestamp actual (ej: `img_1717000000000.jpg`). Esto evita sobreescribir imágenes con el mismo nombre original.
- **Exportación CSV:** Se genera usando `FileWriter` y `PrintWriter`. El archivo incluye encabezados y una fila por vehículo activo de la sucursal seleccionada. Se guarda en el directorio raíz del proyecto.
- **Ruta de imágenes:** Solo se almacena la ruta relativa en la base de datos (ej: `resources/images/img_xxx.jpg`), lo que hace el proyecto portátil entre sistemas operativos.

## Notas Importantes

1. **Base de datos en memoria:** Los datos se pierden al cerrar la aplicación (por diseño H2 en memoria). Si necesitas persistencia entre sesiones, cambia el modo de conexión en `ConexionDB.java` a `jdbc:h2:file:./data/autoelite`.
2. **Rutas de imágenes:** Las imágenes se guardan en `resources/images/` con nombres únicos basados en timestamp. Si eliminas la carpeta manualmente, las referencias en la BD quedarán rotas — la galería mostrará un placeholder en su lugar.
3. **Validaciones:** Se implementan validaciones de precios negativos y campos vacíos tanto en la vista (antes de llamar al controlador) como en el modelo (lanzando excepciones).
4. **Permisos:** Los VENDEDOREs solo pueden ver el inventario y la galería. Los ADMINs pueden realizar CRUD completo en inventario y sucursales. Esta restricción se aplica ocultando/deshabilitando botones de acción en la vista según el rol de la sesión activa.

## Solución de Problemas

### Error: "No fue posible encontrar la clase Main"
- Asegúrate de ejecutar desde el directorio raíz del proyecto
- Verifica que el directorio `bin/` existe después de compilar — si no existe, vuelve a correr el script de compilación
- En Linux/macOS confirma que el script tiene permisos de ejecución: `chmod +x run.sh`

### Error: "Unable to load H2 driver"
- Verifica que `lib/h2-2.4.240.jar` está en el directorio `lib/`
- Confirma que el script de ejecución incluye `-cp bin:lib/h2-2.4.240.jar` (Linux) o `-cp bin;lib/h2-2.4.240.jar` (Windows) en el classpath

### Las imágenes no se guardan
- Asegúrate de que la carpeta `resources/images/` existe y tiene permisos de escritura
- En Linux ejecuta: `mkdir -p resources/images && chmod 755 resources/images`

### La aplicación inicia pero la tabla de inventario aparece vacía
- El script `init.sql` no se ejecutó correctamente. Revisa la consola en busca de errores SQL al iniciar.
- Verifica que `resources/init.sql` existe y no está vacío.

## Autor
Desarrollado como proyecto final de demostración de Java Swing + H2 Database.
Universidad Mariano Gálvez de Guatemala — Facultad de Ingeniería en Sistemas.

## Licencia
Este proyecto es de código abierto y está disponible para propósitos educativos. Puedes usarlo, modificarlo y distribuirlo libremente siempre que se mantenga esta nota de atribución.
