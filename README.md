# job-search-genie
A simple Full-stack app built with Spring Boot and Angular 9 to search for jobs by city.

## Requirements to run api
1. Install [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## How to run api (method 1)
1. Open terminal
2. Go to `api` folder
3. Run `./gradlew clean build && java -jar build/libs/jobsearch-0.0.1-SNAPSHOT.jar`

## How to run api (method 2)
1. Open terminal
2. Go to `api` folder
3. Run `./gradlew :bootRun`

## How to run tests on api
1. Open terminal
2. Go to `api` folder
3. Run `./gradlew :test`

## Requirements to run angular web app
1. Install [Node](https://nodejs.org/pt-br/download/)

## How to run angular web app
1. Open terminal
2. Go to `web` folder
3. Run `npm i && npm start`
3. Open a browser and go to `http://localhost:4200`

## How to run tests on angular web app
1. Open terminal
2. Go to `web` folder
3. Run `npm i && npm test`

## Testing Use case on the web app
1. Run api
2. Run angular web app
3. Open a browser and go to `http://localhost:4200`
4. On the search input type `london` and select `London, GB`
5. A list of cards with jobs should appear

