package command;

import util.PlayerMover;
import world.Direction;
import world.Gear;
import world.GearContainer;
import world.Player;
import world.Room;
import world.World;

public class GetCommand extends Command {
	private String _itemName;
	private String _target;
	private World world;

	public GetCommand(String item) {
		super("get", "<item>: gets item from room. Also: get <item> <target>: gets item from player/MOB/item.");
		_itemName = item.toLowerCase();
		_target = null;
	}
	
	public GetCommand(String item, String target) {
		super("get", "get <item>: gets item from room. Also: get <item> <target>: gets item from player/MOB/item.");
		_itemName = item.toLowerCase();
		_target = target;
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		if (_target == null) {
			for (Gear roomItem : ((Room) player.getLocation())
					.listGear()) {
				if (roomItem.getName().equalsIgnoreCase(_itemName)) {
					if (roomItem instanceof GearContainer
							&& !((GearContainer) roomItem)
									.canBeCarried()) {
						player.sendToPlayer(_itemName
								+ " cannot be carried.");
						return;
					}
					player.addGear(roomItem);
					((Room) player.getLocation()).remove(roomItem);
					return;
				}
			}
			player.sendToPlayer(_itemName + " is not in the room.");
		}
		else {
			if (world.playerExists(_target) && world.playerIsLoggedOn(_target)) {
				if (!world.getPlayer(_target).giveGear(
						world.getPlayer(_target), _itemName,
						player.getName())) {
					player.sendToPlayer(_target
							+ " does not have that item.");
				}
				return;
			}
			if (world.mobileExists(_target)) {
				if (!world.getMobile(_target).giveGear(
						world.getMobile(_target), _itemName,
						player.getName())) {
					player.sendToPlayer(_target
							+ " does not have that item.");
				}
				return;
			}
			for (Gear roomItem : ((Room) player.getLocation())
					.listGear()) {
				if (roomItem instanceof GearContainer
						&& _target.equals(roomItem.getName()
								.toLowerCase())) {
					if (((GearContainer) roomItem).giveGear(null,
							_itemName, player.getName())) {
						return;
					} else {
						player.sendToPlayer("Does " + _target
								+ " have that item?");
						return;
					}
				}
			}
		}
	}

}
