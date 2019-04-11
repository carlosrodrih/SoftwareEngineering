package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MobileFactory {
	private static List<Mobile> availableMonsters; 
	private static BufferedReader inStream;
	public MobileFactory(BufferedReader in) {
		inStream = in;
		loadMobiles();
	}
	
	private static void loadMobiles() {
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
			int armor = Integer.parseInt(loadLine("armor"));
			String trait = loadLine("trait");
			String strat = loadLine("strat");
			String desc = loadLine("desc");
			Gear gear;
			
			if(loadLine("weapon") != "")
				gear = new Weapon("", ""); 
			else
			    gear = new Armor("", "");
			
			availableMonsters.add(new Mobile(name, level, maxHealth, gear, armor, damage, trait, strat, desc));
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
