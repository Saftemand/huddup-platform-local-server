package socket;

import java.io.IOException;
import java.net.InetSocketAddress;

import model.ClientThread;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import service.ClientService;
import view.Mainframe;
import Connections.ClientManager;

public class ClientListener extends WebSocketServer {
	
	private ClientThread currentClient;
	private Mainframe mainframe;
	
	public ClientListener(int port, Mainframe mainframe){
		super(new InetSocketAddress(port));
		this.mainframe = mainframe;
	}
	
	 @Override
	  public void onOpen(WebSocket conn,
	      ClientHandshake handshake) {
		  mainframe.getTxaConsole().append("\nnew user has logged on");
		  ClientThread ct = new ClientThread(conn);
		  ClientService.addOrReplaceClient(ct);
	  }
	 
	  @Override
	  public void onMessage(WebSocket conn,
	      String message) {
		  
		  for(ClientThread ct: ClientManager.getUniqueInstance().getClients().values()){
			  if(ct.getClient().getRemoteSocketAddress().equals(conn.getRemoteSocketAddress())){
				  currentClient = ct;
			  }
		  }
		  
		  try {
			currentClient.sendMessageToGame(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	 
	  @Override
	  public void onClose(WebSocket conn, int code,
	      String reason, boolean remote) {
		  
	  }
	 
	  @Override
	  public void onError(WebSocket conn,
	      Exception exc) {
	    //Handle error during transport here
	  }
	
}
