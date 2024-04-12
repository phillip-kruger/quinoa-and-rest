# quinoa-and-rest

## case 6 

Here we server Quinoa and REST on naked (/). (Bad practise, two technologies on the same root)
- Added custom exception mapper
- SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- **http://localhost:8080/foo - 404 page (served by REST with Exception Mapper)** &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - 404 page (served by REST with Exception Mapper)** &check;

All resources under / is REST and thus the exception Mapper will kick in for any exception, including not-found. 

(This might not be what you want, but it's correct. You problaby want case 3)