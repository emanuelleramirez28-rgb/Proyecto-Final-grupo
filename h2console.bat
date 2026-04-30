@echo off
REM Script para abrir H2 Console - AutoElite
REM Permite navegar y editar la base de datos directamente

echo [*] Iniciando H2 Console...
echo.
echo [!] En el navegador que se abrira:
echo     JDBC URL: jdbc:h2:./data/autoelite
echo     User:     sa
echo     Password: (dejar en blanco)
echo.
echo Presiona ENTER para continuar...
pause > nul

REM Iniciar H2 Server con la consola web
java -cp "lib/h2-2.4.240.jar" org.h2.tools.Server -web

pause
