@echo off

REM by Magm 2005

set TMP_CLASSPATH=%CLASSPATH%

for %%i in (".\drivers\*.jar") do call ".\add.bat" %%i

java -classpath "%TMP_CLASSPATH%";.\practico.jar ar.com.magm.j2se.jdbc.practico.CrearPoblarBDPractico scripts

