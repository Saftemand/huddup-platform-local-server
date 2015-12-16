package app;

import Connections.GameManager;
import socket.ClientListener;
import socket.GameListener;
import view.Mainframe;
import webservice.utils.WebserviceConnector;

public class App {
	
	//TODO:
	//Lav et unity-spil som kan koble sig på serveren
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int clientPort = 2222;
		int gamePort = 3333;
		Mainframe mf = new Mainframe(clientPort);
		
		GameListener gl = new GameListener(gamePort, mf);
		ClientListener cl = new ClientListener(clientPort, mf);
		gl.start();
		cl.start();
		mf.showLocalIpInLabel();
		GameManager.getUniqueInstance().setGameSession(WebserviceConnector.getConnectionId(clientPort));
	}
}
