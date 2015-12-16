package Connections;

import java.util.HashMap;
import java.util.Map;

import model.ClientThread;

public class ClientManager {

	private int uniqueId;
	
	private Map<Integer, ClientThread> clients;

	private static ClientManager uniqueInstance;
	
	public static ClientManager getUniqueInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new ClientManager();
		}
		return uniqueInstance;
	}
	
	private ClientManager(){
		this.clients = new HashMap<Integer, ClientThread>();
		this.uniqueId = 0;
	}

	public Map<Integer, ClientThread> getClients() {
		return this.clients;
	}

	public void setClients(Map<Integer, ClientThread> clients) {
		this.clients = clients;
	}
	
	public int addOrReplaceClient(ClientThread client){
		int clientId = -1;
		
		for(ClientThread c: clients.values()){
			if(c.getRemoteAddress().equals(client.getRemoteAddress())){
				System.out.println("client exists!");
				clientId = c.getSessionUserId();
			}
		}
		
		if(clientId != -1){
			this.clients.put(clientId, client);
			client.setPlayerInstantiated(true);
			client.setSessionUserId(clientId);
			
		} else {
			this.uniqueId++;
			this.clients.put(uniqueId, client);
			client.setSessionUserId(uniqueId);
		}
		
		return client.getSessionUserId();
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
	
}
