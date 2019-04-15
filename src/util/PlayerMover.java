package util;

import world.*;

public class PlayerMover {
	private static World world;
	
	public static void move(Player player, Direction dir) {
		world = World.getInstance();
		
		Room destination = ((Room) player.getLocation())
				.getExitDestination(dir);
		if (destination == null) {
			player.sendToPlayer("You can't go that way.");
			return;
		}

		((Room) world.getDatabaseObject(player.getRoomId())).sendToRoom(""
				+ player.getName() + " exits " + dir.toString().toLowerCase()
				+ ".", player);

		player.moveToRoom(destination);

		String from = "";
		if (dir == Direction.NORTH) {
			from = "south";
		} else if (dir == Direction.EAST) {
			from = "west";
		} else if (dir == Direction.SOUTH) {
			from = "north";
		} else if (dir == Direction.WEST) {
			from = "east";
		} else if (dir == Direction.DOWN) {
			from = "above";
		} else if (dir == Direction.UP) {
			from = "below";
		}

		((Room) world.getDatabaseObject(player.getRoomId())).sendToRoom(""
				+ player.getName() + " enters from " + from + ".", player);
	}
}
