#!/bin/bash
# Script de ejecución para AutoElite (Linux/Mac)

echo "[*] Iniciando AutoElite..."
echo ""

# Verificar si existe el directorio bin
if [ ! -d "bin" ]; then
    echo "[!] El directorio bin no existe. Ejecute compile.sh primero."
    exit 1
fi

# Ejecutar la aplicación
java -cp "bin:lib/h2-2.4.240.jar" Main
