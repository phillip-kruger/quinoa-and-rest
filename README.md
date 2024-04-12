# quinoa-and-rest

## case 2 

Here we server Quinoa on naked (/) and all REST on /api (via `quarkus.rest.path=/api`)
- No custom exception mapper
- SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- **http://localhost:8080/foo - Home page index.html (served by Quinoa)** &check;
- http://localhost:8080/api/hello - REST Response (server by Vertx) &check;
- http://localhost:8080/api/foo - 404 page (served by Vertx) &check;