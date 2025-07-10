# Usa imagem base leve com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Cria volume para arquivos temporários (boa prática com Spring Boot)
VOLUME /tmp

# Define argumento para copiar o JAR gerado
ARG JAR_FILE=target/*.jar

# Copia o JAR para dentro da imagem
COPY ${JAR_FILE} app.jar

# Comando de execução
ENTRYPOINT ["java", "-jar", "/app.jar"]
