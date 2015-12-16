package model;

import java.io.IOException;

import org.java_websocket.WebSocket;

import Connections.GameManager;
import server.ClientServer;


public class ClientThread {
	private WebSocket client;
	private ClientServer clientServer;
	private int sessionUserId;
	private String remoteAddress;
	private boolean playerInstantiated;
	
	public ClientThread(WebSocket client){
		this.client = client;
		this.clientServer = new ClientServer();
		this.remoteAddress = client.getRemoteSocketAddress().getAddress().getHostAddress();
		playerInstantiated = false;
	}
	
	public synchronized void sendMessageToClient(String message){
		try{
			if(this.client.isOpen()){
				this.client.send(message);
			} else {
				System.out.println("client not open...");
			}			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendMessageToGame(String messageBundle) throws IOException{
		String[] messages = messageBundle.split("\\?");
		for(String message : messages){
			if(message.startsWith("LOGON$")){
				if(!playerInstantiated){
					message = message.substring(0, message.length()) + "#" + this.sessionUserId + "?";
					
					boolean logonSendToGame = this.clientServer.sendMessageToGame(message);
					if(logonSendToGame){
						playerInstantiated = true;
					}
					
				}
			} else if(message.startsWith("RECONNECT$")){
				System.out.println("try to reconnect...");
				reconnectPlayer(message);
			} else {
				this.clientServer.sendMessageToGame(message);
			}
		}
		
		
	}
	
	public WebSocket getClient() {
		return client;
	}

	public void setClient(WebSocket client) {
		this.client = client;
	}

	public int getSessionUserId() {
		return sessionUserId;
	}

	public void setSessionUserId(int sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public boolean isPlayerInstantiated() {
		return playerInstantiated;
	}

	public void setPlayerInstantiated(boolean playerInstantiated) {
		this.playerInstantiated = playerInstantiated;
	}
	
	private void reconnectPlayer(String message){
		String[] parameters = message.split("\\$")[1].split("#");

		int sessionUserId = -1;
		String sessionGameId = "";
		try {
			setParameters(parameters, sessionUserId, sessionGameId);
		} catch(Exception ex){
			ex.printStackTrace();
		}
		if(sessionGameId != GameManager.getUniqueInstance().getGameSession()){
			System.out.println("failure 01");
			this.sendMessageToClient("RECONNECT_REFUSED_FAILURE01$NOT_CURRENT_GAME_SESSION");
		}
		if(sessionUserId != this.sessionUserId){
			System.out.println("failure 02");
			this.sendMessageToClient("RECONNECT_REFUSED_FAILURE02$USER_DOES_NOT_EXIST_IN_CURRENT_GAME");
		}
	}
	
	private void setParameters(String[] parametersFromArray, Object... params) throws Exception{
		if(parametersFromArray.length != params.length){
			throw new Exception("INTERNAL ERROR: The number of parameters following the string-array should match"
					+ " the length of the string-array");
		}
		for(int index = 0; index < parametersFromArray.length; index++){
			Object param = params[index];
			if(parametersFromArray[index] != "null"){
				if(param instanceof Integer){
					param = Integer.parseInt(parametersFromArray[index]);
				}
				if(param instanceof String){
					param = parametersFromArray[index];
				}
			} else {
				param = null;
			}
		}
	}
}
