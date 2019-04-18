package util;

import world.*;

public class PlayerFactory {
	public static Player createPlayer(String playerName, String pw) {
		if (World.getInstance().playerExists(playerName))
			return null;

		Player temp = new Player(playerName);
		temp.setPassword(pw);
		temp.setLocation((Room) World.getInstance().getDatabaseObject(1));
		
		if (World.getInstance().registerPlayer(temp.getName().toLowerCase(), temp) == null) {
			World.getInstance().addToWorld(temp);
		}

		return temp;
	}
}
