# Sportsbook
#### Example spring boot app using mongoDB and websocket

### Built With
* [Spring boot](https://spring.io/projects/spring-boot)
* [MongoDb](https://www.mongodb.com/)
* [Gradle](https://gradle.org/)
* [Docker](https://www.docker.com)


## Getting Started

### Prerequsites
* Docker
* Java 11
* Curl
* A websocket client such as wscat

## Usage
### Running locally

1. Run mongoDB in docker
   1. ```docker-compse up -d```
2. Run the spring boot app
   1. ```./gradlew bootRun```

### Testing locally
- Subscribe to updates via websocket client
  - `wscat --connect ws://localhost:8080/websocket`
- Create event
  - ```curl -X POST localhost:8080/event -d '{"eventName": "asdf", "score": "1-0"}' -H "Content-type: Application/json"```
- Update event
   - ```curl -X PUT localhost:8080/event/eventId -d '{"score": "1-1"}' -H "Content-type: Application/json"```
- Get event
   - ```curl -X GET localhost:8080/event/eventId```

## Roadmap

- [ ] Lookup event by name
- [ ] Subscribe to specific events via websocket
- [ ] Create docker image of spring boot app
  - [ ] Add docker image to docker-compose
- [ ] Add kubernetes template files
  - [ ] Add instructions to deploy to local kubernetes
