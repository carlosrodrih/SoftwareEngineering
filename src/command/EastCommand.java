package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class EastCommand extends CommandWithShortcut {

	public EastCommand() {
		super("east", "e", ": moves the player east.");
	}

	@Override
	public synchronized void execute(Player player) {
		PlayerMover.move(player, Direction.EAST);
	}

}
