# Administration Service
## Requirements
- Java 11
- Maven

## How to Start & Test the Application

1. Clone this repository.
2. Open a terminal and navigate to the project root directory.
3. Run `mvn test` to run all test cases (optional).
4. Run `mvn clean package` to build the application.
5. Run `java -jar -Dgithub.auth.token=your-github-token target/administration-0.0.1-SNAPSHOT.jar` to start the application.
6. To Test the APIs, first import the provided Postman collection to Postman and hit the `POST: localhost:8080/api/admin/users` (Add User) API to add new user.
7. Now all other APIs can be tested.

Note: I'm using H2 In-Memory database. So, terminating the application will clean the database.