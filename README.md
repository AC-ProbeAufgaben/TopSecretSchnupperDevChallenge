# Welcome to your Challenge
 


## Prerequisites
* Get [Slack](https://slack.com/intl/de-de/downloads) up and running for Communication 
* IDE of your choosing i.e. [InelliJIDEA](https://www.jetbrains.com/de-de/idea/download)
* Database of your choosing i.e.[MySQL](https://www.mysql.com/de/downloads/)
* [Postman](https://www.postman.com/downloads/)
* Get comfortable and cosy to start your journey

## Task 1
Recreate the following table in the database of your choosing

![image info](ExampleTable.png)

the table name should be FOOD_FRIENDS

To populate the table you can use the "data.sql" file in src/main/resources/data folder

## Task 2

Get this SpringBoot Project up and running  
Create an Endpoint that returns a list of all the data in the Database

This endpoint should be reachable with the following url in postman "http://localhost:8080/all"

## Task 3

Create an Endpoint that returns one Entity of the Database by its ID 

this endpoint should be reachable with the following url in postman  
i.e. "http://localhost:8080/1" for "Steve"


## Task 4 

Create an Endpoint that allows to create a new Entity for the Table

this Endpoint should be reachable with the following url in postman
i.e. "http://localhost:8080/add" (the entity-definition will be send in the body)


## Task 5 

If the endpoint "http://localhost:8080/add" is called with the names  
 "Hawkeye", "Clint","Francis","Barton",  
 "Sören" or Soeren in the name, last_name or email columns  
 the endpoint should return "Due to a lack of noticeable abilities you are not wanted"
 
 ## Task 6
 Create an Endpoint that returns the entity by its name  
  
 the endpoint should be reachable with the following url in postman 
 i.e. "http://localhost:8080/steve" for the Entity of Steve 
 
 ## Task 7
 Create the following Endpoints:   
 * "http://localhost:8080/edit/{id}" (changes in the body) 
      to edit any field of the Entity
  
  * "http://localhost:8080/remove/{id}"  to remove an Entity
        
 ## Task 7
 
 Change the name of the Model FoodFriends to Avengers
 and change the field age into password 
 
 Create a new Entity called Foods with following fields:
 
 id, name 
 
 Change the FavFood field of the Avengers Entity to an  
 appropriate EntityRelationship to Foods
 
 i.e. two avengers can have the same favfood, one avenger can have many favfood
 
 ## Task 8
 
 Create a new Endpoint that returns a list of entities by their favorite food
 
 the endpoint should be reachable with the following url in postman  
 i.e. "http://localhost:8080/food/pizza"  
 returns a list of entities with favfood pizza 
 
 ## Task 9
 
 Create a simple Login where an Avenger
  can login with his name and password
  once logged in the entity will be presented by his non-sensitive data
 

## Technology recommendations
* REST API
* Spring Boot
* Spring Data JPA
 

