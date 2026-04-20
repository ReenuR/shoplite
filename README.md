# ShopLite — E-Commerce Order Management System

A production-grade, microservices-based Order Management System built with Java 17, Spring Boot 3, Apache Kafka, PostgreSQL, and deployed on AWS.

## Architecture Overview
Client
↓
API Gateway (Spring Cloud Gateway + JWT validation)
↓
┌──────────────────────────────────────────────────────┐
│  User        Product     Inventory    Cart/Order      │
│  Service     Service     Service      Service         │
│  (PG)        (PG)        (PG)         (PG)            │
└──────────────────────────────────────────────────────┘
↓ Kafka events (async)
┌──────────────────────────────────────────────────────┐
│  Payment Service          Notification Service        |
│  (Stripe + PG)            (Email via SES)             │
└──────────────────────────────────────────────────────┘

## Services

| Service | Responsibility | DB | Port |
|---|---|---|---|
| api-gateway | Routing, JWT auth, rate limiting | — | 8080 |
| user-service | Registration, login, JWT issuance | PostgreSQL | 8081 |
| product-service | Product catalog, search | PostgreSQL | 8082 |
| inventory-service | Stock tracking, reservations | PostgreSQL | 8083 |
| order-service | Order lifecycle, state machine | PostgreSQL | 8084 |
| payment-service | Stripe integration, payment lifecycle | PostgreSQL | 8085 |
| notification-service | Email notifications via AWS SES | — | 8086 |

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Messaging:** Apache Kafka
- **Database:** PostgreSQL (per service)
- **Auth:** JWT (Spring Security)
- **Payments:** Stripe API
- **Deployment:** AWS EC2, RDS, MSK (Kafka), SES
- **Containerization:** Docker + Docker Compose
- **API Docs:** Springdoc OpenAPI (Swagger UI)

## Running Locally

```bash
docker-compose up
```

## Development Status

- [ ] user-service
- [ ] product-service
- [ ] inventory-service
- [ ] order-service
- [ ] payment-service
- [ ] notification-service
- [ ] api-gateway
- [ ] AWS deployment
