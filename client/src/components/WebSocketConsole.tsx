import React, { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";

interface Props {
  topic: string;
}

export default function WebSocketConsole(props: Props) {
  const [messages, setMessages] = useState<any[]>([]);
  const [echoMessage, setEchoMessage] = useState("Example message!");
  const [activeClient, setActiveClient] = useState<Client>();

  const sendEchoMessage = function () {
    if (activeClient) {
      activeClient.publish({ destination: "/app/echo", body: JSON.stringify({ message: echoMessage }) });
    }
  };

  useEffect(() => {
    const url = new URL("/ws", window.location.href);
    url.protocol = url.protocol.replace("http", "ws");
    const client = new Client({
      brokerURL: url.href,
    });
    client.onConnect = () => {
      setMessages((oldMessages) => ["Client connected to server!"].concat(oldMessages));
      client.subscribe(props.topic, (message) => {
        setMessages((oldMessages) => [message.headers["destination"] + " " + message.body].concat(oldMessages));
      });
      client.subscribe("/topic/messages", (message) => {
        setMessages((oldMessages) => [message.headers["destination"] + " " + message.body].concat(oldMessages));
      });
    };
    client.onDisconnect = () => {
      console.log("Client disconnected from server");
    };
    client.onWebSocketClose = () => {
      setMessages((oldMessages) => ["Lost connection to server!"].concat(oldMessages));
    };
    client.onStompError = (frame) => {
      console.error(frame.headers["message"]);
      if (frame.body) {
        console.error(frame.body);
      }
      setMessages((oldMessages) => [frame.body].concat(oldMessages));
    };

    setMessages([]);
    client.activate();

    setActiveClient(client);

    return () => {
      client.onWebSocketClose = () => {};
      client.deactivate();
    };
  }, [props.topic]);

  return (
    <>
      <div>
        <label>
          Send echo message:
          <div className="input-group">
            <input type="text" value={echoMessage} onChange={(ev) => setEchoMessage(ev.target.value)} />
          </div>
          <button onClick={sendEchoMessage}>Send</button>
        </label>
      </div>
      <div>
        {messages.map((message, index) => (
          <div key={index}>
            <code>{message}</code>
          </div>
        ))}
      </div>
    </>
  );
}
