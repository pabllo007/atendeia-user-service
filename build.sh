#!/bin/bash

IMAGE_NAME="atendeia-user-service"
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "➡️ Versão detectada no pom.xml: $VERSION"

# Build da imagem com tags
docker build -t $IMAGE_NAME:$VERSION -t $IMAGE_NAME:latest .

# Remove container antigo (se existir)
docker rm -f user-service 2>/dev/null

# Define a URL do Config Server como variável de ambiente
CONFIG_SERVER_URL="http://config-server:8888"

# Define o profile ativo (pode alterar conforme o ambiente)
SPRING_PROFILE="local"

# Sobe o container com as variáveis de ambiente
docker run -d -p 8080:8080 \
  -e CONFIG_SERVER_URL=$CONFIG_SERVER_URL \
  -e SPRING_PROFILES_ACTIVE=$SPRING_PROFILE \
  --name user-service \
  $IMAGE_NAME:$VERSION

# Detecta o IP local com fallback para localhost
HOST_IP=$(hostname -I | awk '{print $1}')
if [ -z "$HOST_IP" ]; then
  HOST_IP="localhost"
fi

echo "✅ Imagem do User Service criada e container iniciado!"
echo "🌐 Serviço disponível em: http://$HOST_IP:8080"
