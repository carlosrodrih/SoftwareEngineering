package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import world.Armor;
import world.Gear;
import world.Mobile;
import world.Weapon;

public class MobileFactory {
	private static List<Mobile> availableMonsters; 
	private static BufferedReader inStream;
	
	public static void loadMobiles(BufferedReader in) {
	try {
		if(loadLine("title") != "Mobile Database")
			throw new IOException();
		//add mobile parameters to pass to mobile constructor
		int num;
		do {
			num = Integer.parseInt(loadLine("ref"));
			String name = loadLine("name");
			int level = Integer.parseInt(loadLine("level"));
			int maxHealth = Integer.parseInt(loadLine("maxHealth"));
			int damage = Integer.parseInt(loadLine("damage"));
			int toughness = Integer.parseInt(loadLine("toughness"));
			int accuracy = Integer.parseInt(loadLine("accuracy"));
			int evasion = Integer.parseInt(loadLine("evasion"));
			String strat = loadLine("strat");
			String desc = loadLine("desc");
			Gear gear;
			
			if(loadLine("weapon") != "")
				gear = new Weapon("", ""); // add weapon construction
			else
			    gear = new Armor("", ""); // add armor construction
			
			availableMonsters.add(new Mobile(name, level, maxHealth, gear, toughness, accuracy, evasion, damage, strat, desc));
		} while (num != 0);
	}
	
	catch(Exception e) {
		System.out.print("Wrong File!");
	}
}
	
	private static String loadLine(String prefix) throws IOException{
		String words;
		String line = inStream.readLine().trim();
		words = line.substring(prefix.length()+1).trim();
		
		return words;
	}
	
	public static Mobile getMobile(String mobileName) {
		Mobile m = null;
		
		for(Mobile i : availableMonsters) {
			if(m != null)
				continue;
			else
				m = i.cloneMe();
		}
		return m;
	}
}
