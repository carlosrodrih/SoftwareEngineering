package command;

import util.PlayerMover;
import world.Direction;
import world.Player;

public class UseCommand extends Command {
	private String _itemName;

	public UseCommand(String item) {
		super("use", "<item>: executes the item's default behavior.");
		_itemName = item.toLowerCase();
	}

	@Override
	public synchronized void execute(Player player) {
		player.use(_itemName);
	}

}
