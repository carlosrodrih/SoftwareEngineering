package command;

import world.*;

public abstract class CommandWithShortcut extends Command{
	protected String shortcut;
	
	protected CommandWithShortcut(String n, String sc, String hd) {
		super(n, hd);
		shortcut = sc;
	}
	
	public String helpText() {
		return "- " + name + "/" + shortcut + " " + helpDesc;
	}
}
