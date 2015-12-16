package service;

import model.GameThread;
import Connections.GameManager;

public class GameService {
	
	public static void setGame(GameThread gt){
		GameManager.getUniqueInstance().setGameThread(gt);
	}
	
	public static GameThread getGame(){
		return GameManager.getUniqueInstance().getGameThread();
	}
	
}
