# Test Automation with Quarkus

Example of a simple [Quarkus](https://quarkus.io/) application written in [Kotlin](https://kotlinlang.org/).
Shows how comprehensive **Test Automation** might be realized using
- [Junit 5](https://junit.org/junit5/)
- [MockK](https://mockk.io/)
- [Strikt](https://strikt.io/)
- [Testcontainers](https://www.testcontainers.org/)

## Overview
+ [The Quarkus/Kotlin application _Superhero Repository_](#the-quarkus-kotlin-application-superhero-repository)
+ + [Running the application (in dev mode)](#running-the-application-in-dev-mode)
+ + [Using the application](#using-the-application)
+ [Test Automation](#test-automation)
+ + [Executing the tests](#executing-the-tests)
+ [Further Hints](#further-hints)

## The Quarkus/Kotlin application _Superhero Repository_
<img src="quarkus-testautomation.png" alt="Scenario" width="750"/>

The Quarkus/Kotlin application represents a _Superhero Repository_ where _Superheros_ might be stored and retrieved.

A _Superhero_ has these attributes:
- The Superhero name (e.g. _Batman_)
- The location (e.g. _Gotham City_)
- The real name  (e.g. _Bruce Wayne_)
- The occupation (e.g. _Businessman_)

The Quarkus/Kotlin application consists of three components:
1. The **_SuperheroResource_** providing the HTTP-/REST-API
2. The **_SuperheroService_** which realizes the business logic. 
To be precise it contains an "anonymization logic”: this makes sure that the real name and occupation of a 
Superhero can be stored tough but - in order to protect the Superhero's secret identity - this information will never 
be exposed afterwards.
3. The **_SuperheroRepository_** that encapsulates the access to the (PostgreSQL) database.

### Running the application (in dev mode)

You can run the application in dev mode (what enables live coding) using:
```shell script
./mvnw -Dquarkus-profile=prod quarkus:dev 
```
**Note:** To start the application please make sure to use the `prod` profile and have a
[PostgreSQL database](#setup-postgresql-with-docker) running as configured in the `application-prod.yml`:

```yaml
quarkus:
  datasource:
    username: postgres
    password: mysecretpassword
  jdbc:
    url: jdbc:postgresql://localhost:5432/superherodb
``` 

### Using the application

After successfully starting the application you can add a _Superhero_ with a `POST` call the endpoint
`http://localhost:8080/superheroes` with request like this:
```json
{
  "name": "Deadpool",
  "location": "Sedona",
  "realName": "Wade Winston Wilson",
  "occupation": "Mercenary"
}
```

The list of all (anomyized) _Superheros_ can be retrieved with a `GET` request to endpoint
`http://localhost:8080/superheroes`:
```json
[
  {
    "id": 1,
    "name": "Deadpool",
    "location": "Sedona"
  }
]
```

Or you can get a specific _Superhero_ by calling `http://localhost:8080/superheroes/{id}`.


**Note:** If you want a list of predefined _Superheros_ to be created automatically after starting the app just
uncomment the `initialize()` function call in the startup bean in `InitializeData.kt`:
```kotlin
constructor(superheroService: SuperheroService) {
  this.superheroService = superheroService
  initialize() // uncomment this to create a list of Superheros after starting the app
}
```

## Test Automation
Automated tests might be grouped at different levels of granularity, and how tests should be distributed among 
the different groups in terms of quantity:

<img src="test-pyramid.png" alt="Scenario" width="400"/>

Testing the _Superhero Registry_ application requires tests of each group:
- **Function Unit Tests**: Test the core business logic and mock everything not immediate necessary (see 
SuperheroServiceUnitTests.kt`). 
- **Technology Integration Tests**: Tests in this group are used to verify code written to use a particular technology
  (see `SuperheroResourceIntegrationTests.kt`)
- **End-to-End Tests**: Ensure that the entire control flow works from request to response.
Or to put it this way: Whether the interaction of all the components - tested isolated with Unit or Integration 
Tests - works as well (see `SuperheroRegistryEnd2EndTests.kt`).

## Executing the tests
The tests can be either executed directly within your IDE (e.g. in IntelliJ `Run` > `SuperheroServiceUnitTests.kt` etc.) 
or by running the corresponding Maven command:
```shell script
./mvnw test
```


Of course, it's also possible to use [Quarkus' Continous Testing Mode](https://quarkus.io/guides/continuous-testing):
```shell script
./mvnw quarkus:test
```

**Note:** When executing the End-to-End test `SuperheroRegistryEnd2EndTests.kt` the PostgreSQL database is replaced with 
a [Tesctontainers](https://www.testcontainers.org/) instance. Therefore, a Docker daemon needs to be running on your 
machine. 

## Further Hints

#### Setup PostgreSQL with Docker to run the application
Execute the following steps:
```shell script
docker pull postgres  

docker run -p 5432:5432 --name postgres-db -e POSTGRES_PASSWORD=mysecretpassword -d postgres
```

