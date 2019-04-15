package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class DropCommand extends Command {
	private String _itemName;

	public DropCommand(String item) {
		super("drop", "<item>: drops an item from your inventory to the room.");
		_itemName = item.toLowerCase();
	}

	@Override
	public synchronized void execute(Player player) {
		player.dropGear(_itemName);
	}

}
