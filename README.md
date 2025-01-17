# Yoga

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.

## Start the project

Git clone:

> git clone https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing

Front-end :

Go inside folder:

> cd yoga

Go instode the front-end:

> cd .\front\

Install dependencies:

> npm install

Launch Front-end:

> npm run start;


## Ressources

### Mockoon env 

### Postman collection

For Postman import the collection

> ressources/postman/yoga.postman_collection.json 

by following the documentation: 

https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman


### MySQL

Install and launch MySQL Configurator and create a database names "numdevtest"

SQL script for creating the schema is available `ressources/sql/script.sql`
Open the MySQL commande line console and type : source <your path to>/ressources/sql/script.sql

By default the admin account is:
- login: yoga@studio.com
- password: test!1234


### Test

#### E2E

Launching e2e test:

> npm run e2e or npx cypress open, select E2E testing, select electron as test browser and launch the "all.cy.ts" test

Generate coverage report (you should launch e2e test before):

> npm run e2e:coverage

Report is available here:

> front/coverage/lcov-report/index.html

#### Tests

Launching front-end tests:

> npm run test

Launch front-end test coverage:

> npm test -- --coverage

for following change:

> npm run test:watch


Back-end :

> cd .\back\

launch tests and Jacoco coverage:

mvn clean install

Open Jacoco coverage :

back -> target -> site -> index.html
