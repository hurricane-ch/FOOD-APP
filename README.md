# Food Order App

The application covers the basic part for online orders of food and drinks from a restaurant. 
The restaurant offers a varied menu of soups, salads, main dishes, desserts and drinks.


## Getting Started

### Frontend: 
```bash
    npm install
    ng serve 
```    

### Back-end
Spring Boot, Spring Security

localhost:8080

1. ./mvnw clean package

### Database
MySQL

For linux execute:
1.docker pull mysql:latest
2.docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=foodorderapp -p 3306:3306 -d mysql:latest
3.Make connection to database with user: root and password: root

### Admin for test

* email: admin
* password: admin

### User for test
* email: boev@abv.bg
* password: 12345

Or change default admin from:
```java
 package com.foodorderapp.config;
```
