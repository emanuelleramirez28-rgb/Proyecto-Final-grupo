#!/bin/bash
# Script de compilación para AutoElite (Linux/Mac)

echo "[*] Compilando AutoElite..."
echo ""

# Crear directorio de salida si no existe
mkdir -p bin

# Compilar todo el código
javac -d bin -cp "lib/h2-2.4.240.jar:src" src/*.java src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java src/exceptions/*.java

if [ $? -ne 0 ]; then
    echo "[!] Error en la compilación"
    exit 1
fi

echo "[*] Compilación exitosa!"
echo "[*] Archivos compilados en: bin/"
