@echo off
REM Script de ejecución para AutoElite
REM Ejecuta la aplicación compilada

echo [*] Iniciando AutoElite...
echo.

REM Verificar si existe el directorio bin
if not exist "bin" (
    echo [!] El directorio bin no existe. Ejecute compile.bat primero.
    pause
    exit /b 1
)

REM Ejecutar la aplicación
java -cp "bin;lib/h2-2.4.240.jar" Main

pause
