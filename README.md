# Stackoverflow answers

## Spring WebFlux.
### 1. [Call downstream service simultaneously and response to the client in chunks](https://stackoverflow.com/questions/59722242/how-to-return-response-immediate-to-client-in-spring-flux-by-controlling-the-no/59723426#59723426).
Command to run the project: `./mvnw -pl reactive-stream-response clean spring-boot:run`

Next, call the service via: `curl http://localhost:8080/stream` and see how response comes by chunks

### 2. [Flux takeUntilOther](https://stackoverflow.com/questions/60341194/webflux-how-to-work-takeuntilother-method/60344215?noredirect=1#comment106759902_60344215)
Command to run the project: `./mvnw -pl flux-take-until-other clean spring-boot:run`

### 3. [Async/Scheduled Call external service](https://stackoverflow.com/questions/60351103/how-to-know-that-all-threads-completed-in-spring-boot-async-with-scheduled/60352370#60352370)
Command to run the project: `./mvnw -pl async-scheduled-call-external-service clean spring-boot:run`