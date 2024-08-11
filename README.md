# Food Order App

The application covers the basic part for online orders of food and drinks from a restaurant. 
The restaurant offers a varied menu of soups, salads, main dishes, desserts and drinks.


## Getting Started

### Frontend: 
```bash
    go into ui folder
    npm install
    npm start
```

### Database
MySQL

For linux execute:
1.docker pull mysql:latest
2.docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=foodorderapp -p 3306:3306 -d mysql:latest
3.Make connection to database with user: root and password: root

### Back-end
1../mvnw clean package (has to start container with mysql before compile) 
2.Start the app

### Admin for test

* email: admin
* password: admin

### User for test
* email: boev@abv.bg
* password: 12345

Or change default admin from:

src/main/java/com/foodorderapp/config/SetupDataLoader.java

