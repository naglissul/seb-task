# SEB homework for application

Task:
```
Task: exchange rate portal.

Write a web application with such functionality:
1. Central bank exchange rates page. Exchange rates from the European Central Bank. https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
2. After selecting a specific currency, its exchange rate history is displayed (chart or table).
3. Currency calculator. The amount is entered, the currency is selected, the program displays the amount in foreign currency and the rate at which it was calculated.
4.* Exchange rates must be automatically obtained every day (e.g. using quartz).
5.* Use the H2 database for data storage.
6.* Initially, if no rates yet loaded - populate rates for last 90 days.

Technologies

Backend: any. Preferred Java/Kotlin.
For frontend: any. Preferred Angular.
```

## Run locally

Prerequisites:
- nodejs, ng
- Java 21
- Maybe something else for backend...
OR
- docker
- docker compose

```bash
cd back
./gradlew build
./gradlew bootRun
```
Open http://localhost:8080/swagger-ui/index.html

and also

```bash
cd front
npm install
npm start
```
Open http://localhost:4200

or
```bash
docker compose up -d
```
Open http://localhost:8080/swagger-ui/index.html and http://localhost:80

## Tech
- Spring Kotlin
- Angular
- [OpenAPI Generator](https://openapi-generator.tech/) (npm run genapi)
- Swagger

## How was it done?

### Init

Angular:

```bash
npx @angular/cli new exrates-client --strict --style=css
```

Spring:

https://start.spring.io/index.html

```
Project: Gradle-Kotlin
Language: Kotlin
Spring Boot: 3.4.3
Project Metadata:
- Group: sebtask
- Artifact: exrates
- Name: exrates
- Description: Exchange rates portal
- Package name: sebtask.exrates
- Packaging: Jar
- Java: 21
Dependencies:
- H2 Database
- Spring Web
- Lombok
- Validation
- Spring Boot DevTools
- Spring Boot Actuator
- Spring Data JPA
```
