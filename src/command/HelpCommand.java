package command;

import util.PlayerMover;
import world.Direction;
import world.Interpreter;
import world.Player;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("commands", ": lists all the commands useable by a player.");
	}

	@Override
	public synchronized void execute(Player player) {
		player.sendToPlayer(Interpreter.commandDescriptions());
	}

}
