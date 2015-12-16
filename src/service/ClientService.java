package service;

import model.ClientThread;
import Connections.ClientManager;

public class ClientService {
	
	public static void addOrReplaceClient(ClientThread ct){
		ClientManager.getUniqueInstance().addOrReplaceClient(ct);
	}
	
	public static void removeClient(int key){
		if(ClientManager.getUniqueInstance().getClients().containsValue(key )){
			ClientManager.getUniqueInstance().getClients().remove(key);
		}
	}
	

	
}
