#!/bin/sh
# Maven Wrapper Script
# Baixa o Maven automaticamente se não estiver instalado.

set -e

MAVEN_WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

# Lê a URL do wrapper jar
WRAPPER_URL=$(grep "wrapperUrl" "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2)

# Baixa o wrapper jar se necessário
if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
    if command -v curl > /dev/null 2>&1; then
        curl -fsSL -o "$MAVEN_WRAPPER_JAR" "$WRAPPER_URL"
    elif command -v wget > /dev/null 2>&1; then
        wget -q -O "$MAVEN_WRAPPER_JAR" "$WRAPPER_URL"
    else
        echo "Erro: curl ou wget é necessário para baixar o Maven Wrapper."
        exit 1
    fi
fi

exec java -jar "$MAVEN_WRAPPER_JAR" "$@"
