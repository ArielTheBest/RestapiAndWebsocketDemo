package com.myproject.restapiandwebscoket.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.restapiandwebscoket.model.MyObject;
import com.myproject.restapiandwebsocket.handler.ClientHandler;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class MyRestContoller {
	
	private ClientHandler clientHandler;
	
	private ResponseEntity<String> responseEntity;
	
	private final String webSocketAddress = "wss://echo.websocket.org";
	
	private void initializeWebSocket() throws URISyntaxException {
		clientHandler = new ClientHandler(new URI(webSocketAddress));
		
	}

	private ResponseEntity<String> sendMessageOverSocket(String message) {
		if (clientHandler == null) {
			try {
				initializeWebSocket();
			} catch (URISyntaxException uriSyntaxException) {
				uriSyntaxException.printStackTrace();
				return new ResponseEntity<String>("ERROR: " + uriSyntaxException, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		clientHandler.sendMessage(message);
		return  new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
		
	@RequestMapping(value = "restapiandwebscoket/update", method = RequestMethod.POST, consumes="application/json", produces="text/plain")
    public ResponseEntity<String> getDetails(@RequestBody MyObject myObject)
    {
		String message = myObject.getData();
		if(message == null)
			return new ResponseEntity<String>("ERROR: Data is empty", HttpStatus.BAD_REQUEST);
		responseEntity = sendMessageOverSocket(message);
		return responseEntity;
    }
	
}
