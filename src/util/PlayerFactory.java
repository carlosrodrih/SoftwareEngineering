package util;

import world.*;

public class PlayerFactory {
	private static CharacterClass[] availableClasses = {Gunner.getInstance(), Dreadnaught.getInstance()};
	private static CharacterClass currentClass;
	private static String name;
	private static String password;
	
	public static void parseInput(String playerName, String playerClass, String pw) {
		name = playerName;
		password = pw;
		
		for (CharacterClass c : availableClasses){
			if (currentClass != null)
				continue;
			else {
				if (playerClass.equalsIgnoreCase(c.toString()))
					currentClass = c;
			}
		}
	}
	
	public static Player createPlayer() {
		Player ret = new Player(name);
		ret.setCharacterClass(currentClass);
		ret.setPassword(password);
		
		return ret;
	}
}
