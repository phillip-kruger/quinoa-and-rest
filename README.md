# quinoa-and-rest

## case 3 old version

Here we server Quinoa on naked (/) and all REST on /api (via `quarkus.rest.path=/api`)
- Added custom exception mapper
- SPA routing turned on (via `quarkus.quinoa.enable-spa-routing=true`)

### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - Home page index.html (served by Quinoa)** &cross;

The old way (ResumeOn404) made all urls redirect on a 404. Even in the case where rest on /api. This is wrong.