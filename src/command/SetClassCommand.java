package command;

import util.PlayerMover;
import world.Direction;
import world.Interpreter;
import world.Player;

public class SetClassCommand extends Command {

	public SetClassCommand() {
		super("setclass", "<class>: lists all the commands useable by a player.");
	}

	@Override
	public synchronized void execute(Player player) {
		player.sendToPlayer(Interpreter.getInstance().commandDescriptions());
	}

}
