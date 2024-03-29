# java-mongodb-redis-api

# REST API Example using JAVA 17, Spring, MongoDB and Redis

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

This project is an API built using **Java 17, Spring, Mongo DB and Redis.**

## Table of Contents

- [Setup](#setup)
- [API Endpoints](#api-endpoints)

## Setup

1. Clone the repository:

2. Install dependencies with Maven

3. Run in terminal:
```bash
cd /config/docker/mongodb/
docker compose up -d
```

4. Acess mongo express via: http://localhost:8086.

5. Log with admin:pass and create a database called 'catalog'.

6. Start the application with Maven

7. The API will be accessible at http://localhost:8080

## API Endpoints
The API provides the following endpoints:

**API PRODUCTS**
* Create Product
``` bash
curl --location --request POST 'http://localhost:8080/catalog-api/api/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Hamburguer do Futuro", 
    "description": "280g de Hamburguer Vegetal", 
    "ownerId": "12345678", 
    "price": "132", 
    "categoryId": "65d13b447698b50ade0d1395"
}'
```

* List Products
``` bash
curl --location --request GET 'http://localhost:8080/catalog-api/api/products'
```

* Update Product
``` bash
curl --location --request PUT 'http://localhost:8080/catalog-api/api/products/65d69ecfe8faa649124c2002' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Hamburguer do Futuro", 
    "description": "280g de Hamburguer Vegetal", 
    "ownerId": "12345678", 
    "price": "145", 
    "categoryId": "65d13b447698b50ade0d1395"
}'
```

* Delete Product
``` bash
curl --location --request DELETE 'http://localhost:8080/catalog-api/api/products/65d69ecfe8faa649124c2002'
```

**API CATEGORY**
* Create Category
``` bash
curl --location --request POST 'http://localhost:8080/catalog-api/api/categories' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Pizzaria", 
    "description": "Categoria de Pizzaria", 
    "ownerId": "12345678"
}'
```

* List Categories
``` bash
curl --location --request GET 'http://localhost:8080/catalog-api/api/categories'
```

* Update Category
``` bash
curl --location --request PUT 'http://localhost:8080/catalog-api/api/categories/65d13b447698b50ade0d1395' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "Hamburgueria", 
    "description": "Categoria de Hamburgueria", 
    "ownerId": "12345678"
}'
```

* Delete Category
``` bash
curl --location --request DELETE 'http://localhost:8080/catalog-api/api/categories/65d1372f3a284c07ed2460bc'
```
