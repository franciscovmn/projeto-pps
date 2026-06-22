@REM Maven Wrapper Script para Windows
@echo off
setlocal

set MAVEN_WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=.mvn\wrapper\maven-wrapper.properties

for /f "tokens=2 delims==" %%a in ('findstr "wrapperUrl" "%MAVEN_WRAPPER_PROPERTIES%"') do set WRAPPER_URL=%%a

if not exist "%MAVEN_WRAPPER_JAR%" (
    curl -fsSL -o "%MAVEN_WRAPPER_JAR%" "%WRAPPER_URL%"
)

java -jar "%MAVEN_WRAPPER_JAR%" %*
