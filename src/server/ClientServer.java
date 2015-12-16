package server;

import java.io.IOException;

import service.GameService;

public class ClientServer {
	
	public ClientServer(){
		
	}
	
	public boolean sendMessageToGame(String message) throws IOException{
		if(GameService.getGame() != null){
			GameService.getGame().sendMessageToGame(message);
			return true;
		}
		return false;
	}
	
}
