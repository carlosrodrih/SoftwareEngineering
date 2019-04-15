package command;

import util.PlayerMover;
import world.DatabaseObject;
import world.Direction;
import world.GearList;
import world.Player;
import world.World;

public class InspectCommand extends Command {
	private String _toInspect;
	private World world;

	public InspectCommand(String toInspect) {
		super("inspect", "<player,mob,item>: lists all the items being held or contained in a player, mob, or other item.");
		_toInspect = toInspect.toLowerCase().trim();
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		for (DatabaseObject item : world.getDatabaseObjects()) {
			if (item.getName().toLowerCase().equals(
					_toInspect)
					&& (item instanceof world.GearList)) {
				player.sendToPlayer(((GearList) item).inspect());
				return;
			}
		}

		player
				.sendToPlayer(_toInspect
						+ " does not exist or cannot be inspected.");
	}

}
