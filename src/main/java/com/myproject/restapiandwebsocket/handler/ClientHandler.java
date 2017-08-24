package com.myproject.restapiandwebsocket.handler;

import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class ClientHandler {		
	public ClientHandler(URI endpointURI) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, endpointURI);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	Session userSession = null;

	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println("Client: opening websocket ");
		this.userSession = userSession;
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		System.out.println("Client: closing websocket");
		this.userSession = null;
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println("Client: received message " + message);
	}

	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
	}

}
