package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class DescribemeCommand extends Command {
	private String _description;
	
	public DescribemeCommand(String description) {
		super("describeme", "<description>: sets your (the player's) description.");
		_description = description;
	}

	@Override
	public synchronized void execute(Player player) {
		player.setDescription(_description);
	}

}
