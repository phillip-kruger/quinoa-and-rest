# quinoa-and-rest

## case 7 

Here we server Quinoa and REST on naked (/). (Bad practise, two technologies on the same root)
- Added custom exception mapper
- Added another exception mapper for 404
- SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- http://localhost:8080/api/foo - 404 page (served by REST with Exception Mapper) &check;

All resources under / is REST and thus the exception Mapper will kick in for any exception, including not-found. 
However you now handle not found to go to index in the case it's not /api.
This is not great, but if you want both Quinoa and REST on the same root, then this is what you can do. I would however recoment case 3
