package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.GameServer;
import Connections.ClientManager;
import Connections.GameManager;

public class GameThread extends Thread{

	private BufferedReader  in;
	private DataOutputStream out;
	private GameServer mainServer;
	
	public GameThread(BufferedReader in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("running");
				String s = in.readLine();
				System.out.println("message from game: " + s);
				String[] gameParameters = {};
				if(s == null){
					stopGame();
					return;
				} else {
					try{
						gameParameters = s.split("\\$")[1].split("#");
					} catch(ArrayIndexOutOfBoundsException e){
						
					}
				}
				if(s.startsWith("SESSION_USER_ID$")){
					int sessionUserId = Integer.parseInt(gameParameters[0]);
					sendUserIdToClient(sessionUserId);
				}
				if(s.startsWith("NEW_GAME$")){
					sendGameIdToClients(GameManager.getUniqueInstance().getGameSession());
					System.out.println("Sending gamesession: " + GameManager.getUniqueInstance().getGameSession());
					sendMessageToGame("CONNECTION_ID$" + GameManager.getUniqueInstance().getGameSession());
				}
			} catch (Exception e){
				e.printStackTrace();
				
			}
		}
	}
	
	public synchronized void sendMessageToGame(String message) throws IOException{
		message += "?";
		this.out.writeBytes(message);
	}	
	
	private void stopGame(){
		System.out.println("game stopped");
		
		for(Integer key: ClientManager.getUniqueInstance().getClients().keySet()){
			try{
				ClientManager.getUniqueInstance().getClients().get(key).sendMessageToClient("game stopped");
			} catch(Exception e){
				e.printStackTrace();
			}
			
		}
		ClientManager.getUniqueInstance().getClients().clear();
		GameManager.getUniqueInstance().setGameThread(null);
	}
	
	private void sendUserIdToClient(int sessionUserId){
		for(ClientThread ct: ClientManager.getUniqueInstance().getClients().values()){
			System.out.println("looking at user...");
			if(ct.getSessionUserId() == sessionUserId && ct.getClient().isOpen()){
				System.out.println("SendUserIdToClient(): " + "SUCCESS$"+sessionUserId+"#"+GameManager.getUniqueInstance().getGameSession());
				ct.sendMessageToClient("SUCCESS$"+sessionUserId+"#"+GameManager.getUniqueInstance().getGameSession());
			}
		}
	}
	
	private void sendGameIdToClients(String sessionGameId){
		for(ClientThread ct: ClientManager.getUniqueInstance().getClients().values()){
			if(ct.getClient().isOpen()){
				ct.sendMessageToClient("GAME_SESSION_CHANGED$"+sessionGameId+"#"+GameManager.getUniqueInstance().getGameSession());
			}
		}
	}
	
	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public GameServer getMainServer() {
		return mainServer;
	}

	public void setMainServer(GameServer mainServer) {
		this.mainServer = mainServer;
	}

	
}
