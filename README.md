# quinoa-and-rest

## case 1 

Here we server Quinoa on naked (/) and all REST on /api (via quarkus.rest.path=/api)
- No custom exception mapper
- No SPA routing

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - 404 page (served by Vertx) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- http://localhost:8080/api/foo - 404 page (served by Vertx) &check;