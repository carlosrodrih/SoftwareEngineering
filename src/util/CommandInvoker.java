package util;

import command.Command;
import world.Player;

public class CommandInvoker {
	private static volatile Command command;
	
	public static synchronized void setCommand(Command c) {
		command = c;
	}
	
	public static synchronized void execute(Player player) {
		command.execute(player);
	}
}
