This is a personal project to experiment and learn how to use websockets in a Spring Boot app with a React frontend.

## Learning resources
* https://stomp.github.io/
* https://stomp-js.github.io/guide/stompjs/using-stompjs-v5.html
* https://spring.io/guides/gs/messaging-stomp-websocket/

## Project overview

You'll find two modules in this project: a client and a server.

The client uses React + Typescript in a standard create-react-app setup, while the server uses Spring (Boot) + Kotlin.

On the front-end side, the code is pretty straight forward. Proxying the websocket through the development server to the backend service, required some trial and error but the resulting code in `client/setupProxy.js` is quite stable.

In the back-end you'll find most code documented with some hints about what is happening. A good place to start are the `WebSocketConfig.kt` and `GreetingController.kt`.

## Demo time

1. Start your Spring Boot based server by running `Server.kt` in the server project.
2. Start the react client by running `npm start` in the client project.
3. Open your browser at http://localhost:3000

![Demo](docs/demo.png )

