-- Script de inicialización para la base de datos H2 de AutoElite
-- Este script se ejecuta automáticamente al iniciar la aplicación

-- Insertar sucursales
INSERT INTO sucursales (nombre, ciudad, direccion) VALUES ('AutoElite Centro', 'Ciudad Autónoma de Buenos Aires', 'Av. Corrientes 1234, CABA');
INSERT INTO sucursales (nombre, ciudad, direccion) VALUES ('AutoElite Zona Oeste', 'La Matanza', 'Calle Mitre 567, La Matanza');
INSERT INTO sucursales (nombre, ciudad, direccion) VALUES ('AutoElite Zona Sur', 'Lomas de Zamora', 'Ruta 9 km 25, Lomas de Zamora');

-- Insertar usuarios (contraseña en texto plano para demo)
INSERT INTO usuarios (username, password, rol, nombre_completo) VALUES ('admin', '123456', 'ADMIN', 'Administrador Sistema');
INSERT INTO usuarios (username, password, rol, nombre_completo) VALUES ('vendedor1', '123456', 'VENDEDOR', 'Juan García');
INSERT INTO usuarios (username, password, rol, nombre_completo) VALUES ('vendedor2', '123456', 'VENDEDOR', 'María López');

-- Insertar autos
INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, numero_puertas, tipo_combustible, es_automatico) 
VALUES ('Toyota', 'Corolla', 2020, 18500.00, 1, true, 'Auto', 4, 'Gasolina', true);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, numero_puertas, tipo_combustible, es_automatico) 
VALUES ('Honda', 'Civic', 2019, 17000.00, 1, true, 'Auto', 4, 'Gasolina', true);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, numero_puertas, tipo_combustible, es_automatico) 
VALUES ('Ford', 'Focus', 2021, 16500.00, 2, true, 'Auto', 4, 'Gasolina', true);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, numero_puertas, tipo_combustible, es_automatico) 
VALUES ('Chevrolet', 'Cruze', 2020, 15800.00, 2, true, 'Auto', 4, 'Diésel', true);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, numero_puertas, tipo_combustible, es_automatico) 
VALUES ('Volkswagen', 'Golf', 2022, 22000.00, 3, true, 'Auto', 5, 'Gasolina', true);

-- Insertar motocicletas
INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, cilindrada, tipo_manejo) 
VALUES ('Yamaha', 'YZF-R3', 2021, 5500.00, 1, true, 'Motocicleta', 321, 'Deportiva');

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, cilindrada, tipo_manejo) 
VALUES ('Honda', 'CB500F', 2020, 6200.00, 1, true, 'Motocicleta', 471, 'Deportiva');

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, cilindrada, tipo_manejo) 
VALUES ('Kawasaki', 'Ninja 400', 2019, 4800.00, 2, true, 'Motocicleta', 399, 'Deportiva');

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, cilindrada, tipo_manejo) 
VALUES ('Harley-Davidson', 'Street 750', 2022, 8500.00, 3, true, 'Motocicleta', 750, 'Crucero');

-- Insertar camiones
INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, capacidad_carga, numero_ejes) 
VALUES ('Volvo', 'FH16', 2020, 65000.00, 1, true, 'Camion', 25.0, 3);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, capacidad_carga, numero_ejes) 
VALUES ('Mercedes-Benz', 'Actros', 2019, 60000.00, 2, true, 'Camion', 24.0, 3);

INSERT INTO vehiculos (marca, modelo, año, precio, id_sucursal, activo, tipo, capacidad_carga, numero_ejes) 
VALUES ('Scania', 'R440', 2021, 62000.00, 3, true, 'Camion', 25.5, 3);

-- Insertar algunas imágenes de ejemplo (con rutas ficticias)
INSERT INTO imagenes (id_vehiculo, ruta, es_principal) 
VALUES (1, 'resources/images/toyota_corolla_1.jpg', true);

INSERT INTO imagenes (id_vehiculo, ruta, es_principal) 
VALUES (2, 'resources/images/honda_civic_1.jpg', true);

INSERT INTO imagenes (id_vehiculo, ruta, es_principal) 
VALUES (6, 'resources/images/yamaha_yzf_1.jpg', true);

INSERT INTO imagenes (id_vehiculo, ruta, es_principal) 
VALUES (11, 'resources/images/volvo_fh16_1.jpg', true);

