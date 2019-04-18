package util;

import world.*;

public class ClassSetter {
	
	public static CharacterClass[] availableClasses = {
		Gunner.getInstance(),
		Dreadnaught.getInstance()
	};
	
	public static String getWrongInputText() {
		String wrongInputText;
		int i = 0;
		
		wrongInputText = "Please type a class name:";
		
		for (; i < availableClasses.length - 1; ++i) {
			wrongInputText += " " + availableClasses[i] + ",";
		}
		
		wrongInputText += " or " + availableClasses[i] + ".";
		
		return wrongInputText;
	}
	
	public static String getClassOptions() {
		String list = "";
		int i = 0;
		
		for (; i < availableClasses.length - 1; ++i) {
			list += " " + availableClasses[i] + ",";
		}
		
		list += " or " + availableClasses[i];
		
		return list;
	}
	
	public static CharacterClass setCharacterClass(Player player, String input) {
		CharacterClass ret = null;
		
		for (CharacterClass i : availableClasses) {
			if (ret != null)
				continue;
			else {
				if (input.equalsIgnoreCase(i.toString()))
					ret = i;
			}
		}
		
		if (!ret.equals(null)) 
			return ret;
		
		player.sendToPlayer(input + " is not a character class.");
		return ret;
	}
}
