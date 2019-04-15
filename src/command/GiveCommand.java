package command;

import util.PlayerMover;
import world.Direction;
import world.Gear;
import world.GearContainer;
import world.Player;
import world.Room;
import world.World;

public class GiveCommand extends Command {
	private String _itemName;
	private String _target;
	
	public GiveCommand(String item, String target) {
		super("give", "<item> <player>: gives item in your inventory to player/MOB.");
		_itemName = item.toLowerCase();
		_target = target;
	}

	@Override
	public synchronized void execute(Player player) {
		player.giveGear(player, _itemName, _target);
	}

}
