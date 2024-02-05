# Flight Search API
This is a Flight Search API that is implemented using Spring Boot. This is a study case project given by Amadeus.

## Usage
- First, environmental variables should be addded with following names and values:
  
    ```
    DB_URL={YOUR_DB_URL}
    DB_USER={YOUR_DB_USERNAME}
    DB_PASS={YOUR_DB_PASSWORD}
    SECRET_KEY={YOUR_SECRET_KEY}
    TOKEN_EXPIRATION_DAYS={YOUR_DESIRED_EXPIRATION_DAY_COUNT_FOR_JWT}
    ```
    - To have strong JWT, please use secret keys longer than 256bits
- Before running the flight-search-api project, run the mock-flight-data application. This application simulates 3rd party APIs, it produces random number of flights. This mock API is needed for initializing the Flight database table. Most importantly, main API daily sends requests to add new flights with a cron job to this mock API.

## Endpoints
- except user registering and logging in endpoints, all other endpoints need JWT in header of each request with ```Authorization: {YOUR_JWT}```.
- User Endpoints
  - POST on ```{BASE_URL}/login```: used for logging in, with body of ```{"username": "{YOUR_USERNAME}", "password": "{YOUR_PASSWORD}"}```, your JWT will be response's headers. 
  - POST on ```{BASE_URL}/register```: used for registering users with role of ```USER``` that has only read permissions, with body of ```{"username": "{YOUR_USERNAME}", "password": "{YOUR_PASSWORD}"}```. 
  - POST on ```{BASE_URL}/register-admin```: used for registering users with role of ```ADMIN``` that has all the permissions, with body of ```{"username": "{YOUR_USERNAME}", "password": "{YOUR_PASSWORD}"}```.
  - POST on ```{BASE_URL}/delete-account```: used for deleting the current (authenticated) user, with body of ```{"username": "{YOUR_USERNAME}", "password": "{YOUR_PASSWORD}"}```.
- Airport Endpoints
  - GET on ```{BASE_URL}/api/v1/airport/all```: for getting list of all airports.
  - GET on ```{BASE_URL}/api/v1/airport/{airport_id}```: for getting airport with given 'airport_id'.
  - POST on ```{BASE_URL}/api/v1/airport/add```: (ADMINs only) for adding a new airport.
  - PUT on ```{BASE_URL}/api/v1/airport/update```: (ADMINs only) for updating an airport.
  - DELETE on ```{BASE_URL}/api/v1/airport/delete/{airport_id}```: (ADMINs only) for deleting an airport with given 'airport_id'.
- Flight Endpoints
  - GET on ```{BASE_URL}/api/v1/flight/all```: for getting list of all flights. This method has 2 unrequired parameters, 'from' and 'to'. Eaither of them should be passed in format of 'yyyy-MM-dd'.
  - GET on ```{BASE_URL}/api/v1/flight/{flight_id}```: for getting flight with given 'flight_id'.
  - POST on ```{BASE_URL}/api/v1/flight/add```: (ADMINs only) for adding a new flight.
  - PUT on ```{BASE_URL}/api/v1/flight/update```: (ADMINs only) for updating an flight.
  - DELETE on ```{BASE_URL}/api/v1/flight/delete/{flight_id}```: (ADMINs only) for deleting a flight with given 'flight_id'.
  - GET on ```{BASE_URL}/api/v1/flight/search/single?from={origin_airport_id}&to={destination_airport_id}&departure={departure_date}```: for getting all single trip flight with asked features, the date should be in format of 'yyyy-MM-dd'.
  - GET on ```{BASE_URL}/api/v1/flight/search/round?from={origin_airport_id}&to={destination_airport_id}&departure={departure_date}&return={return_date}```: for getting all round trip flight with asked features, the dates should be in format of 'yyyy-MM-dd'.
