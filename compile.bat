@echo off
REM Script de compilación para AutoElite
REM Compila todo el código Java y copia los recursos

echo [*] Compilando AutoElite...
echo.

REM Crear directorio de salida si no existe
if not exist "bin" mkdir bin

REM Compilar todo el código
javac -d bin -cp "lib/h2-2.4.240.jar;src" src/*.java src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java src/exceptions/*.java

if %errorlevel% neq 0 (
    echo [!] Error en la compilación
    pause
    exit /b 1
)

echo [*] Compilación exitosa!
echo [*] Archivos compilados en: bin/
pause
