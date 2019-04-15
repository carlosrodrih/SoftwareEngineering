package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class InventoryCommand extends CommandWithShortcut {

	public InventoryCommand() {
		super("inventory", "i", "lists the items that you are carrying.");
	}

	@Override
	public synchronized void execute(Player player) {
		player.sendToPlayer(player.inspect());
	}

}
