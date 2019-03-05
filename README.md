# Inter CRUD

Spring Boot REST Application for User and Unique Digit CRUD

**To run the Spring application:**
` mvn spring-boot:run `

**To run unit tests:**
` mvn test `

**Base URL:**
```
 http://localhost:8080/
 [HEADER] Content-Type: application/json
```
### Clients CRUD

- **List Users**
```
[GET] api/usuario
```

- **Find User**
```
[GET] api/usuario/{Integer}
```

- **Create User**
```
[POST] api/usuario
[BODY] { 
  "name": String, 
  "email": String" 
}
```

- **Update User**
```
[PUT] api/usuario/{Integer}
[BODY] { 
  "name": String, 
  "email": String 
}
```
- **Delete User**
```
[DELETE] api/usuario/{Integer}
```

- **List Digit**
```
[GET] api/digitoUnico
[HEADER] user: {Integer}
```

- **Create Digit**
```
[POST] api/digitoUnico
[HEADER] user: {Optional Integer}
[BODY] { 
  "k": Integer, 
  "n": String containing positive number
}
```

[GuilhermeFGL](https://www.linkedin.com/in/guilherme-faria-da-gama-lima-37baa647/)
