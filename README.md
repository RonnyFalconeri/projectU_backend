# projectU - Backend
<img src="https://user-images.githubusercontent.com/34677325/166123969-0698ef9a-f462-467d-b4db-d1d3a77ad82c.png" width="400"/>


## Introduction
Did you ever had a good idea for a project? Only to forget about it a few days later? Of course you had. Well, its your lucky day because you just found _projectU_ - perhabs the best project managing application in the market, and it's free!

This is the backend part of the application which forms the RESTful API and is developed with Spring Boot.
Read [here](https://github.com/RonnyFalconeri/projectU_frontend) for more information about the frontend part and projectU itself.


## HTTP Endpoints
Every HTTP Endpoint, Method, Response and Object is defined in this [openapi.yml](./src/main/resources/openapi/openapi.yaml).


## Technology Used

### Spring Boot & MongoDB
The frontend makes requests to the _projectU_backend_ which is 
a [separate repository](https://github.com/RonnyFalconeri/projectU_backend). 
The backend is developed with spring boot and it stores the data in a mongoDB database.

### OpenApi Specification
The API was designed with the contract first approach using 
the [OpenApi Specification](https://swagger.io/specification/). 
All the API endpoints with its parameters, responses, objects etc. are defined in a single 
file called [openapi.yml](./src/assets/openapi/openapi.yaml) which is shared between 
frontend and backend. With the openapi.yml the corresponding API client & server can be generated 
using [OpenApi Generators](https://openapi-generator.tech/docs/generators).


## Getting Started

### Requirements
- Java 11
- maven
- docker

### Start MongoDB in Docker
docker-compose
Access MongoDB in the browser with Mongo Express
localhost:8082

### Generate the API
Before compiling and starting the application you have to generate the necessary sources from 
the `openapi.yml` with the maven task:
```shell
maven clean compile
```
