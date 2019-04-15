package command;

import world.*;

public class LookCommand extends Command {
	private String _objName;
	private World world;

	public LookCommand() {
		super("look", "look: shows description of the room that the player is in, "
				+ "or if an argument is provided, such as an item/player/MOB in the room, "
				+ "it should provide the description of said item/player/MOB). "
				+ "This command gives a 360 degree report of the environment "
				+ "(The player is not assumed to be looking in a specific Direction.");
	}
	
	public LookCommand(String objName) {
		super("look", "look: shows description of the room that the player is in, "
				+ "or if an argument is provided, such as an item/player/MOB in the room, "
				+ "it should provide the description of said item/player/MOB). "
				+ "This command gives a 360 degree report of the environment "
				+ "(The player is not assumed to be looking in a specific Direction.");
		_objName = objName;
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		if (_objName == null) {
				player.sendToPlayer(((Room) player.getLocation()).generateDescription());
				return;
		}
		
		for (DatabaseObject item : world.getDatabaseObjects()) {
			if (item.getName().toLowerCase().equalsIgnoreCase(
					_objName)) {
				player.sendToPlayer(item.getDescription());
				return;
			}
		}

		player.sendToPlayer(_objName + " is not here.");

	}

}
