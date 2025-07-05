# Credit Consultation

Aplicação Spring Boot para consulta de crédito com integração ao Kafka e banco de dados PostgreSQL.

## Pré-requisitos

- Docker
- Docker Compose

## Como rodar a aplicação

### 1. Clone o repositório
```bash
git clone https://github.com/AndreRTN/nfse-consulta-backend
cd credit-consultation
```

### 2. Execute com Docker Compose
```bash
docker-compose up --build
```

### 3. Acesse a aplicação
A aplicação estará disponível em: `http://localhost:8084/api`

## URLs importantes

- **API Base**: `http://localhost:8084/api`
- **Swagger UI**: `http://localhost:8084/api-docs`
- **Kafka UI**: `http://localhost:8080`
- **PostgreSQL**: `localhost:5432`

## Serviços

### Banco de Dados
- **PostgreSQL**: porta 5432
- **Database**: credit_consultation
- **User**: myuser
- **Password**: secret

### Kafka
- **Broker**: localhost:9092
- **Zookeeper**: localhost:2181
- **Consumer Group**: credit-consultation-group
- **Kafka UI**: http://localhost:8080

## Comandos úteis

### Parar todos os serviços
```bash
docker-compose down
```

### Rebuild da aplicação
```bash
docker-compose up --build
```

## Configurações da aplicação

- **Porta**: 8084
- **Context Path**: /api
- **Logs SQL**: habilitado
- **Swagger**: habilitado em /api-docs

## Estrutura do projeto

- **Dockerfile**: Build da aplicação Spring Boot com Maven
- **docker-compose.yaml**: Orquestração completa dos serviços
- **application.properties**: Configurações da aplicação
