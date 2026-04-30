#!/bin/bash
# Script para abrir H2 Console - AutoElite
# Permite navegar y editar la base de datos directamente

echo "[*] Iniciando H2 Console..."
echo ""
echo "[!] En el navegador que se abrira:"
echo "    JDBC URL: jdbc:h2:./data/autoelite"
echo "    User:     sa"
echo "    Password: (dejar en blanco)"
echo ""
read -p "Presiona ENTER para continuar..."

# Iniciar H2 Server con la consola web
java -cp "lib/h2-2.4.240.jar" org.h2.tools.Server -web
