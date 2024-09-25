# Currency Exchange API
The following project is allowing user's to exchange the currencies between the currencies accounts they have.
After creation new user's account, by default user have 2 currency accounts - PLN and USD.
API allows you to check the currency balances.

## Technologies
- Java 17
- SpringBoot 3.3.4
- Maven
- MySQL 8.0
- Liquibase
- Mapstruct 1.6.2

## Installation
After downloading the repository you need to set up the configuration.

### Running application with maven
##### 1 Create a MySQL database for this application.
##### 2. Create the environmental variables:
- MYSQL_DATASOURCE_URL
    - indicates a path to your db
- MYSQL_USER 
    - indicates your username at the db server
- MYSQL_PASSWORD 
    - indicates your password to the database

##### 4. Set the values for each of the variables

##### 5. Once the configuration is done you can run the application with the command
##
<tab>mvn spring-boot:ru<tab>

_NOTE_: In order to make this application running, make sure that your DB server is running

## API Documentation
Swagger is included in the project. 
Hence, the API documentation is available at http://localhost:8080/swagger-ui.html.
