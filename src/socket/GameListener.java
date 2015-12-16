package socket;

import java.io.BufferedReader; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameThread;
import service.GameService;
import view.Mainframe;

public class GameListener extends Thread{
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	private int port;
	private Mainframe mainframe;
	
	public GameListener(int port, Mainframe mainframe){
		this.port = port;
		this.mainframe = mainframe;
	}

	@Override
	public void run() {
		
		try {
			welcomeSocket = new ServerSocket(port);
			while(true) {
				connectionSocket = welcomeSocket.accept();
				GameThread gt = new GameThread(
						new BufferedReader(
								new InputStreamReader(connectionSocket.getInputStream()))
						, new DataOutputStream(connectionSocket.getOutputStream()));
				mainframe.getTxaConsole().append("\na game has been started");
				gt.start();
				GameService.setGame(gt);
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}	
}
