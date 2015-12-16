package Connections;

import java.util.Random;

import model.GameThread;

public class GameManager {
	
	private GameThread gameThread;
	private String gameSession;
	
	private static GameManager uniqueInstance;
	
	public static GameManager getUniqueInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new GameManager();
		}
		return uniqueInstance;
	}
	
	private GameManager(){
		gameThread = null;
	}

	public GameThread getGameThread() {
		return gameThread;
	}

	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}

	public String getGameSession() {
		return gameSession;
	}

	public void setGameSession(String gameSession) {
		this.gameSession = gameSession;
	}
	
}
