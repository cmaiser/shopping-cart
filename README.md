# Full Stack Developer Assignment for Chris Maiser
## Explanation

For this assignment, I implemented the api using Spring Boot backed
by an embedded H2 database.  The React front end was created using 
yarn and jest.  The full deliverable is packaged by Maven for easy
portability.  

Because a shopping cart is specific to a single user, I added user authentication and role-based authorization
 in a way that can easily be expanded to support multiple users.  To do this,
 I created a data model with a many-to-many relationship between a user (User)
 and a store item (Item).  The quantity is stored in the join table (Cart).
 
 For the sake of the demo, authentication is triggered by clicking the **Login as Chris** button.
 This will authenticate against a principal in the User table where username is
 **chris** and password is password is a hash of **chris**.    
 
 ## Running the Application
 ### Dependencies
 * [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
 * [Maven](https://maven.apache.org/download.cgi)  
 ### Building the App
* From the project directory containing pom.xml:  
  * ***mvn clean package -DskipTests=true -P prod***
* This will generate a runnable jar at **target/shopping-cart-1.0.0-SNAPSHOT.jar**
### Running the App
* ***java -jar shopping-cart-1.0.0-SNAPSHOT.jar***
* The app will be available at http://localhost:8080
### Testing the App
* Back End Tests
  * These tests require a running app, which is why the app was built with the -DskipTests flag set to true.  Normally, this this testing would use mocks.
  * ***mvn test***
* Front End test
  * From the App directory:
    * ***./node_modules/.bin/jest***
 
 
 
 
 
 

 
