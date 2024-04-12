# quinoa-and-rest

This repo contain multiple branches, each containing the code for a certain case when using Quinoa and REST together.

We will start with case 3 and 6 as they are relevant to https://github.com/quarkiverse/quarkus-quinoa/issues/666

 
## Case 3 - The correct way to do this.

When you decide to serve 2 technologies over HTTP (REST and Quinoa) the correct way to do it would be to have them serving from different 
roots. Example, you might choose to serve all WebPages from `/` and all REST from `/api`. This way the technologies do not "step on each other's toes"

So, in case 3 we server Quinoa on naked (`/`) and all REST on `/api` (via `quarkus.rest.path=/api`)

We also:

- Added custom exception mapper, and
- Turned on SPA routing (via `quarkus.quinoa.enable-spa-routing=true`)

### The old way (Quarkus 3.8)

We had this Build item called ResumeOn404, that is used when the user enable SPA in Quinoa. 
This was needed because NotFound was handled by each extension themselves, and not by the underlying VertX.

#### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - Home page index.html (served by Quinoa)** &cross;

The old way (ResumeOn404) made all urls redirect on a 404. 
Even in the case where rest on `/api` (as in explicitly via `quarkus.rest.path=/api`). **This is wrong.**

For detail and code see https://github.com/phillip-kruger/quinoa-and-rest/tree/case3-old

### The new way (Quarkus 3.9)

Now we moved the 404 Handling to the underlying Vertx. No need for ResumeOn404 anymore.

#### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - 404 page (served by REST with Exception Mapper)** &check;

For detail and code see https://github.com/phillip-kruger/quinoa-and-rest/tree/case3

## Case 6 - Mixing REST and Quinoa

This is the same cenario than case 3, except we do not have `quarkus.rest.path=/api` and now both technologies are using `/` as their root.
This should not be recomended.

So in case 6 we serve Quinoa and REST on naked (/).

We also: 
- Added custom exception mapper
- Turned on SPA routing (via `quarkus.quinoa.enable-spa-routing=true`)

### The old way (Quarkus 3.8)

We had this Build item called ResumeOn404, that is used when the user enable SPA in Quinoa. 
This was needed because NotFound was handled by each extension themselves, and not by the underlying VertX.

#### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check; (you get what you want due to resumeOn404 hack)
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - Home page index.html (served by Quinoa)** &cross;

The old way (ResumeOn404) made all urls redirect on a 404. 
In the case with your rest on `/api/foo` you get the HTML home page . **This is wrong.**

For detail and code see https://github.com/phillip-kruger/quinoa-and-rest/tree/case6-old

### The new way (Quarkus 3.9)

Now we moved the 404 Handling to the underlying Vertx. No need for ResumeOn404 anymore.

#### Results:

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- **http://localhost:8080/foo - 404 page (served by REST with Exception Mapper)** &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- **http://localhost:8080/api/foo - 404 page (served by REST with Exception Mapper)** &check;

All resources under / is REST and thus the exception Mapper will kick in for any exception, including not-found. 

(This might not be what you want, but it's correct. You problaby want case 3)

For detail and code see https://github.com/phillip-kruger/quinoa-and-rest/tree/case6

## Case 7 - A way to get case 6 to do what you want

If for some reason you can not, or do not want to, seperate REST and Quinoa with seperate roots (via `quarkus.rest.path=/api`) 
you can add the following in your ExceptionMapper:

```
@Context
private UriInfo uriInfo;

@ServerExceptionMapper
public Response handleWeb404(NotFoundException exception) {
    String path = uriInfo.getRequestUri().getPath();
    if(path.startsWith("/api")){
        return handleExceptions(exception);
    }
    return Response.seeOther(URI.create("/")).build();
}
```

#### Results

- http://localhost:8080/ - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/index.html - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/quinoa.html - Quinoa page quinoa.html (served by Quinoa) &check;
- http://localhost:8080/foo - Home page index.html (served by Quinoa) &check;
- http://localhost:8080/api/hello - REST Response (server by REST) &check;
- http://localhost:8080/api/foo - 404 page (served by REST with Exception Mapper) &check;

All resources under / is REST and thus the exception Mapper will kick in for any exception, including not-found. 
However you now handle not found to go to index in the case it's not /api.
This is not great, but if you want both Quinoa and REST on the same root, then this is what you can do. I would however recomend case 3

For detail and code see https://github.com/phillip-kruger/quinoa-and-rest/tree/case7

## Other cases (for interest) that are all working fine in old and new:

- https://github.com/phillip-kruger/quinoa-and-rest/tree/case1
- https://github.com/phillip-kruger/quinoa-and-rest/tree/case2
- https://github.com/phillip-kruger/quinoa-and-rest/tree/case4
- https://github.com/phillip-kruger/quinoa-and-rest/tree/case5