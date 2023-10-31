# Currency-exchange
Project is in process of developing

Aware:
After sending HTTP requests to currency-exchange controller, there is error which appear: org.springframework.web.client.ResourceAccessException: I/O error on POST request for "http://localhost:9411/api/v2/spans"
This is not affecting the controller to work properly, but can be stopped by running zipkin server.
Guide for running container:
1. Install Docker
2. Open cmd and type: docker pull openzipkin/zipkin
3. Run zipkin container: docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
4. Access zipkin UI: http://localhost:9411

