cd /d "%~dp0"
REM gestion-signalement
call mvn clean install -f pom.xml
 java -jar ./target/gestion-signalement.jar
pause
