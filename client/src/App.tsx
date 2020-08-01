import React, { useState } from "react";
import WebSocketConsole from "./components/WebSocketConsole";

function App() {
  const [topic, setTopic] = useState("/topic/greetings");

  return (
    <div className="container">
      <div className="jumbotron">
        <h1>WebSocket demo app</h1>
        <p className="lead">This small demo app tires to show different use cases for websockets</p>
        <hr />
        Supported topics are
        <ul>
          <li>
            <code>/topic/greetings</code> illustrates a basic case where the server regularly outputs messages
          </li>
          <li>
            <code>/topic/user-greetings</code> outputs an access denied message due to the user missing subscription rights to the topic
          </li>
          <li>
            <code>/topic/non-existing</code> showcases what happens when the client subscribes to a non-existing topic (no output / no error)
          </li>
        </ul>
      </div>
      <h3>Parameters</h3>
      <div className="p-3">
        <label>
          Topic:
          <div className="input-group">
            <input type="text" value={topic} onChange={(ev) => setTopic(ev.target.value)} />
          </div>
        </label>
      </div>
      <h3>Output</h3>
      <div className="p-3">
        <WebSocketConsole topic={topic} />
      </div>
    </div>
  );
}

export default App;
