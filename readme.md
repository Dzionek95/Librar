#**Library**

###Goal:
It's main purpose is to practise my Java and Spring Boot skills and get more familiar with various libraries

###How to run:
* You need to have Java 8 (or higher) and Maven 3(or higher) installed
* To start application execute `mvn spring-boot:run`
* To start tests only execute `mvn surefire:test`
* To check swagger documentation you need to enter `localhost:8082/swagger-ui.html`
access is only for Admin account (login: Admin, password:Admin)
* To check sonar stats you need to: 
    * `mvn clean install site`
    * `mvn sonar:sonar` (before you need to start SonarQube server- default `localhost:9000`)

###About the application itself
* App is divided into Admin and Reader part
* Admin is able to do following: 
    * Manage accounts (CRUD)
    * Accept new books requests
    * Manage books (CRUD)
* Reader is able to do following:
    * Display all books
    * Look for books by author, category, title
    * If book is already rented reader is automatically added to waiting queue
    * Display ordered queue to book
    * Create request to get new book for library (that can be accepted by Admin)
* Sonar stats:
    * Bugs- 0
    * Vulnerabilities- 0  
    * Code Smells- 15
    * Code Coverage- 27.9% (Without Models, Configurations(Beans), Repositories)
    * Duplications-  0.0%   
    
###Technologies that has been used:
* Java 
* Maven 
* Spring Boot
* Spring Data
* Spring Security
* JUnit
* Mockito
* Hamcrest
* Slf4j
* Lombok
* H2 (dev env) / MySQL 
* Swagger 
* SonarQube (Jacoco for code test coverage)



 