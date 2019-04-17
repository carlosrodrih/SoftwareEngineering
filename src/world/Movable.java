package world;

/**
 * Movable is an interface which assures that any object capable of moving from
 * room to room has certain commands. It also acts as a marker for both Player
 * and Mobile objects. It extends the DatabaseItem and GearList interfaces, as
 * movables are database items and gear holders and share much in common with
 * GearContainers. The are a GearList, in that respect as well as have a
 * GearList instance variables.
 */
public interface Movable extends GearList, Character{	
	/**
	 * The moveToRoom command handles the upkeep of a room movement by shiftign
	 * the Mobile object from one room to another, and then forms the String to
	 * be given to the Mobile after changing rooms. Mobiles need this less than
	 * players, but other behavior might depend on keywords in room
	 * descriptions, or a reaction to other DatabaseObjects in the room.
	 * 
	 * @param destination
	 *            This is a room reference for the destination of the move.
	 */
	public void moveToRoom(Room destination);

	/**
	 * use will be called whenever the movable wants to an item that they
	 * currently have. If a player does attempt to use something that they
	 * currently don't have the method will not do anything.
	 * 
	 * @param itemName
	 *            The name of the gear to be used
	 */
	public void use(String itemName);
	
	public void technique(Movable target);
}

