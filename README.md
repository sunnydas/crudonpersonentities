Simple Person Entity Service  
===============================

## Assumptions

1. APIs use Basic authentication for clients.
2. Gradle wrapper is being used as the build tool (tested on 4.10).
3. In memory database being used for persistence.

## Description
The Person entities service provides the following REST APIs:

N.B1: Currently "admin" and "developer" users are seeded in the authorization module.Password is "password"

1. Create person.
   This API provides functionality of basic person creation.
   ```
       Resource: api/v1/persons
	   HTTP Verb: POST
	   {
	    "firstName":"Sunny",
     	"lastName": "Das",
	    "age":"34",
	    "favoriteColor":"blue",
	    "personHobbies" : [{
		                   "hobby":"running"
	                    },
	                    {
		                   "hobby":"reading"
	                    }
	                  ]
       }
	   Content Type: application/json
	   Authorization: Basic YWRtaW46cGFzc3dvcmQ=
	   Response Example: 
	   {
        "firstName": "Sunny",
        "lastName": "Das",
        "personId": 1,
        "age": 34,
        "favoriteColor": "blue",
        "hobby": [
          "running",
          "reading"
        ]
       }
	   Response Code: 201
   ```   
    
2. Modify Person.
   This API provides the functionality of modifying a person entity.
   ```
       Resource: /api/v1/persons/{personId}
	   HTTP Verb: PUT
	   {
	    "firstName":"Sunny",
     	"lastName": "Das",
	    "age":"34",
	    "favoriteColor":"blue",
	    "personHobbies" : [{
		                   "hobby":"running"
	                    },
	                    {
		                   "hobby":"reading"
	                    }
	                  ]
       }
	   Content Type: application/json
	   Authorization: Basic YWRtaW46cGFzc3dvcmQ=
	   Response Example: 
	   {
        "firstName": "Sunny",
        "lastName": "Das",
        "personId": 1,
        "age": 34,
        "favoriteColor": "blue",
        "hobby": [
          "running",
          "reading"
        ]
       }
	   Response code: 200
   ```    
3. Get a Person 
   This API provides the functionality of getting details of a person based on the person ID. 
   ```
   Resource: /api/v1/persons/{personId}
	   HTTP Verb: GET
	    Authorization: Basic YWRtaW46cGFzc3dvcmQ=
	   Response Example: 
	   {
        "firstName": "Sunny",
        "lastName": "Das",
        "personId": 1,
        "age": 34,
        "favoriteColor": "blue",
        "hobby": [
          "running",
          "reading"
        ]
       }
	   Response code: 200
   ```	   
4. Get all Persons
   This API provides the functionality of fetching all Persons in the system.
   ```
   Resource: /api/v1/persons
	   HTTP Verb: GET
	    Authorization: Basic YWRtaW46cGFzc3dvcmQ=
	   Response Example: 
	   [
       {
        "firstName": "Sunny",
        "lastName": "Das",
        "personId": 1,
        "age": 34,
        "favoriteColor": "blue",
        "hobby": [
            "running",
            "reading"
        ]
       }
       ]
	   Response code: 200
   ```   
5. Delete Person.
   This API provides the functionality of deleting a Person based on personId.
   ```
       Resource: /api/v1/persons/{personId}
	   HTTP Verb: DELETE
	   Response code: 204
   ```   
## Build/Execute 

1. Build
   ```
      gradlew clean build
   ``` 

2. Run
   ```
      gradlew bootRun
   ```    

## Run Instructions

1. By default the application runs on port 9090, for example:
   ```
      localhost:9095/api/v1/persons
   ```
   
## Technology stack

1. Java 
2. SpringBoot
3. H2   
