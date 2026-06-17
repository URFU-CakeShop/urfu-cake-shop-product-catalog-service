# 🍰 Cake Shop — Product Catalog Service

**Микросервис каталога продукции для интернет-кондитерской**

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/version-1.0.0-pink?style=flat-square)](CHANGELOG.md)

</div>

---

## 📖 Содержание

- [О сервисе](#-о-сервисе)
- [Модель данных](#-модель-данных)
- [Возможности](#-возможности)
- [Технологии](#-технологии)
- [Быстрый старт](#-быстрый-старт)
- [REST API — Swagger](#-rest-api--swagger)
- [Примеры запросов](#-примеры-запросов)

---

## 🎯 О сервисе

**Product Catalog Service** — это микросервис, отвечающий за управление каталогом продукции кондитерской Cake Shop. Сервис предоставляет REST API для создания и получения продуктов, управления их типами, категориями (с иерархией parent–child) и вариантами (SKU, цена, вес).

> **Основной канал взаимодействия** — REST API. Сервис реализует CRUD-логику для четырёх сущностей, связанных между собой.

---

## 🗂 Модель данных

<img width="1545" height="938" alt="image" src="https://github.com/user-attachments/assets/84cd907e-edf2-4077-af21-99580cb2c86f" />

## ✨ Возможности

| Функция | Описание |
|---|---|
| 🏷 **Управление продуктами** | Создание продуктов с указанием типа и категорий |
| 🗂 **Иерархия категорий** | Древовидная структура категорий с parent–child связями через attach/detach |
| 📋 **Типы продуктов** | Категоризация продуктов по типам (торты, пирожные, печенье и т.д.) |
| 🔄 **Варианты продуктов** | Один продукт может иметь несколько вариантов с разными SKU, ценами и весом |
| 📦 **SKU и ценообразование** | Каждый вариант имеет уникальный SKU, цену и валюту |
| 🧪 **Swagger UI** | Интерактивная документация API для тестирования |
| 🗄 **Миграции БД** | Автоматическое создание схемы через Liquibase |

---

## 🛠 Технологии

- **Java 17** + **Spring Boot 3.5.10**
- **Spring Data JPA** + **PostgreSQL 15** — хранение данных
- **Liquibase** — миграции БД
- **SpringDoc OpenAPI (Swagger)** — документация API
- **Lombok** — сокращение шаблонного кода
- **JUnit 5** + **Mockito** — модульное тестирование
- **Docker Compose** — контейнеризация
- **GitHub Actions** — CI/CD с Trivy security scan

---

## 🚀 Быстрый старт

### Запуск через Docker Compose

```bash
docker compose up -d
```

### Проверка работоспособности

После запуска откройте Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## ⚙️ Конфигурация

Все настройки сервиса передаются через переменные окружения:

```env
# База данных
DB_HOST=cake-shop-product-catalog-db
DB_PORT=5432
DB_NAME=cake-shop-product-catalog
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Spring
SPRING_PROFILES_ACTIVE=default
```

### Таблицы БД

Автоматически создаются при старте через Liquibase:

| Таблица | Назначение |
|---|---|
| `category` | Категории продуктов (с self‑reference parent_id) |
| `product` | Продукты со ссылкой на тип |
| `product_categories` | Связь many‑to‑many между продуктом и категориями |
| `product_type` | Типы продуктов |
| `product_variant` | Варианты продуктов (SKU, цена, вес) |

---

## 🧪 REST API — Swagger

Swagger UI: `http://localhost:8080/swagger-ui.html`

Все ответы обёрнуты в единый формат:

```json
{
  "success": true,
  "data": { ... },
  "message": "Success"
}
```

### Product

| Метод | Endpoint | Описание |
|---|---|---|
| `POST` | `/product` | Создать продукт |

### Product Category

| Метод | Endpoint | Описание |
|---|---|---|
| `GET` | `/product/category` | Получить все категории |
| `GET` | `/product/category/{id}` | Получить категорию по ID |
| `POST` | `/product/category` | Создать категорию |
| `POST` | `/product/category/{sourceId}/attach/{targetId}` | Привязать категорию (target.parent = source) |
| `POST` | `/product/category/{categoryId}/detach` | Отвязать родителя у категории |

### Product Type

| Метод | Endpoint | Описание |
|---|---|---|
| `GET` | `/product/type` | Получить все типы |
| `GET` | `/product/type/{id}` | Получить тип по ID |
| `POST` | `/product/type` | Создать тип |

### Product Variant

| Метод | Endpoint | Описание |
|---|---|---|
| `POST` | `/product/variant` | Создать вариант продукта |
| `GET` | `/product/variant/{id}` | Получить вариант по ID |
| `GET` | `/product/{productId}/variant` | Получить все варианты продукта |

---

## 📝 Примеры запросов

### Создание типа продукта

```json
POST /product/type
{
  "name": "Торт"
}
```

### Создание категории

```json
POST /product/category
{
  "name": "Свадебные"
}
```

### Создание продукта

```json
POST /product
{
  "name": "Наполеон",
  "description": "Классический слоёный торт с заварным кремом",
  "typeId": "550e8400-e29b-41d4-a716-446655440000",
  "categoryIdList": [
    "660e8400-e29b-41d4-a716-446655440001"
  ]
}
```

### Создание варианта продукта

```json
POST /product/variant
{
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "sku": "NAP-1KG",
  "price": 2500.00,
  "currency": "RUB",
  "weight": 1.0,
  "isActive": true
}
```

### Привязка категории (attach)

```bash
POST /product/category/660e8400-e29b-41d4-a716-446655440001/attach/770e8400-e29b-41d4-a716-446655440002
```

Устанавливает категорию `770e...002` дочерней по отношению к `660e...001`.

### Отвязка категории (detach)

```bash
POST /product/category/770e8400-e29b-41d4-a716-446655440002/detach
```

Удаляет родительскую связь у указанной категории.
