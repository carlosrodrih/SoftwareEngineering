package util;

import world.*;

public class ExitSetter {
	public static void setExits (Room target, Room north, Room south, Room east, Room west, Room up, Room down) {
		target.setExitDestination(Direction.NORTH, north);
		target.setExitDestination(Direction.SOUTH, south);
		target.setExitDestination(Direction.EAST, east);
		target.setExitDestination(Direction.WEST, west);
		target.setExitDestination(Direction.UP, up);
		target.setExitDestination(Direction.DOWN, down);
	}
}
