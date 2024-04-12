# quinoa-and-rest

## case 5 

Here we server Quinoa and REST on naked (/). (Bad practise, two technologies on the same root)
- No custom exception mapper
- SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - Home page index.html (served by Quinoa)** &check;


/api/foo is not found by REST, but passed on, as there are No exception mapper, and other extensions that serve from /

(This might not be what you want, but it's correct. You problaby want case 2)