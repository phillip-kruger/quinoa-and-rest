# quinoa-and-rest

## case 1 

Here we server Quinoa on naked (/) and all REST on /api (via `quarkus.rest.path=/api`)
No custom exception mapper
No SPA routing

http://localhost:8080/ - Home page index.html (served by Quinoa) :heavy_check_mark:
http://localhost:8080/index.html - Home page index.html (served by Quinoa) :heavy_check_mark:
http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) :heavy_check_mark:
http://localhost:8080/foo - 404 page (served by Vertx) :heavy_check_mark:

http://localhost:8080/api/hello - REST Response (server by Vertx) :heavy_check_mark:
http://localhost:8080/api/foo - 404 page (served by Vertx) :heavy_check_mark:
 
## case 2 

Here we server Quinoa on naked (/) and all REST on /api (via `quarkus.rest.path=/api`)
No custom exception mapper
SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

http://localhost:8080/ - Home page index.html (served by Quinoa) :heavy_check_mark:
http://localhost:8080/index.html - Home page index.html (served by Quinoa) :heavy_check_mark:
http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) :heavy_check_mark:
**http://localhost:8080/foo - Home page index.html (served by Quinoa)** :heavy_check_mark:

http://localhost:8080/api/hello - REST Response (server by Vertx) :heavy_check_mark:
http://localhost:8080/api/foo - 404 page (served by Vertx) :heavy_check_mark: