# Currency-API

Currency-API is a Spring Cloud microservice designed to work with currencies. It offers a range of functionalities related to currency exchange and security services.

## Main Functionalities

1. **Saving Exchange Types**: Save exchange types, such as EUR -> BGN.

2. **Display All Exchanges**: Retrieve and display all currency exchanges saved in the database.

3. **Currency Conversion**: Convert exchange types and calculate total amounts based on quantity needs.

4. **Tax Rate Management**: Add tax rates to specific currency exchanges and calculate total tax rates for predefined currency exchange pairs.

5. **Currency Exchange Rates**: Display all currency exchange rates.

6. **Security Services**: User registration, token generation, and validation.

## Service Discovery

Currency-API uses Netflix Eureka for service discovery. You can access the service discovery at: [http://localhost:8761](http://localhost:8761)

![Service Discovery](https://github.com/Stanislav2177/Currency-exchange/assets/91600823/d3e0fa80-8a62-4775-b4ce-5f5d8c539f2e)

## API Gateway

The API Gateway serves as the entry point to the microservice, handling routing and security. We've integrated Spring Security for enhanced security.

### Routing Configuration

Routes are structured as follows:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: currency-exchange
          uri: lb://currency-exchange
          predicates:
            - Path=/currency-exchange/**
          filters:
            - AuthenticationFilter

```

Before any request passes through the gateway, it checks if a Bearer token is included, except for /auth/register, /auth/token, and /auth/validate endpoints.

Security Service
Currency-API employs Spring Security with JSON Web Tokens (JWT) for user authentication and authorization.

User Registration
To register a user, send an HTTP POST request to: http://localhost:9898/auth/register

Example JSON body:
```
{
    "name": "username",
    "email": "username@mail.bg",
    "password": "password"<br>   
}
```

Token Generation
To generate a token after successful registration, make an HTTP POST request to: http://localhost:9898/auth/token

Example JSON body:
```
{
    "username": "username",
    "password": "password"
}
```
The token is generated using HS256 (HMAC with SHA-256) with a predefined secret key in the JWTService class in the identity service.

Token Validation
To validate the token, make an HTTP GET request to: http://localhost:9898/auth/validate?token={token_value}

# Additional Notes
If you encounter an error regarding Zipkin, it does not affect the functionality but can be resolved by running the Zipkin server.

How to Run Zipkin Container:

Install Docker.
Open a command prompt and run: docker pull openzipkin/zipkin
Start the Zipkin container: docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
Access the Zipkin UI at: http://localhost:9411

