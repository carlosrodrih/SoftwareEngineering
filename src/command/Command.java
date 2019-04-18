package command;

import world.*;

public abstract class Command {
	protected String name;
	protected String helpDesc;
	
	protected Command(String n, String hd) {
		name = n;
		helpDesc = hd;
	}
	
	public abstract void execute(Player player);
	
	public boolean hasShortcut() {
		return false;
	}
	
	public String key() {
		return name;
	}
	
	public String helpText() {
		return "- " + name + " " + helpDesc;
	}
}
