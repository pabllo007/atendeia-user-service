# 🛠 AtendeIA - User Service (user-service)

Este microserviço é responsável pelo gerenciamento de usuários na plataforma AtendeIA, incluindo cadastro, consulta e gestão de perfis. Ele faz parte de uma arquitetura baseada em microserviços com autenticação SSO (a ser integrada futuramente).

---

## 🚀 Como subir a aplicação localmente

### 1. Requisitos

- Java 17
- Maven 3.8+
- Docker (opcional, para banco de dados)

### 2. Configuração do ambiente

#### application.yml

```yaml
server:
  port: 0

spring:
  application:
    name: user-service
  config:
    import: optional:configserver:${CONFIG_SERVER_URL:http://localhost:8888}
  profiles:
    active: local

eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_SERVER_URL:http://localhost:8761/eureka/}
    register-with-eureka: true
    fetch-registry: true
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}

management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always
```

> **Obs:** Este microserviço está configurado para utilizar porta dinâmica (0), o que será definido em tempo de execução. A porta será informada no console ou registrada no Eureka.

---

## 🧪 Swagger - Documentação da API

### URL padrão (após subir a aplicação):

```
http://localhost:{porta}/swagger-ui.html
```

> **Atenção:** substitua `{porta}` pela porta aleatória informada no console de execução ou visível no Eureka.

### Recursos

- Interface interativa para testar endpoints
- Descrições personalizadas com `@Tag` e `@Operation`
- Exclusão de endpoints do Actuator da documentação
- CORS configurado globalmente para permitir acesso de origens diferentes (ex: Swagger em outro host)

---

## 📦 Endpoints principais do user-service

| Método | Caminho            | Descrição                       |
| ------ | ------------------ | ------------------------------- |
| POST   | /api/usuarios      | Cria um novo usuário            |
| GET    | /api/usuarios      | Lista todos os usuários         |
| GET    | /api/usuarios/{id} | Busca um usuário por ID         |
| PUT    | /api/usuarios/{id} | Atualiza um usuário existente   |
| DELETE | /api/usuarios/{id} | Remove um usuário (soft delete) |

---

## 🧩 Tratamento de erros

A aplicação segue o padrão [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807):

### Exemplo de resposta de erro

```json
{
  "type": "/errors/email-ja-cadastrado",
  "title": "E-mail já utilizado",
  "status": 409,
  "detail": "Já existe um usuário com o e-mail informado.",
  "instance": "/api/usuarios"
}
```

---
