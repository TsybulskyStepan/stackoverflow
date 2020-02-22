# Stackoverflow answers

## Spring WebFlux.
### 1. [Call downstream service simultaneously and response to the client in chunks](https://stackoverflow.com/questions/59722242/how-to-return-response-immediate-to-client-in-spring-flux-by-controlling-the-no/59723426#59723426).
Command to run the project: `./mvnw -pl reactive-stream-response clean spring-boot:run`

Next, call the service via: `curl http://localhost:8080/stream` and see how response comes by chunks

### 2. [Flux takeUntilOther](https://stackoverflow.com/questions/60341194/webflux-how-to-work-takeuntilother-method/60344215?noredirect=1#comment106759902_60344215)
Command to run the project: `./mvnw -pl flux-take-until-other clean spring-boot:run`