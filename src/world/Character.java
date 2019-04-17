package world;

public interface Character extends DatabaseItem{
	/**
	 * sendToPlayer() is used to send text to the client of the player's
	 * controller directly if the movable is a player or send text to the mob if
	 * the movable is a mobile. If a mobile, the mobile can react the the text.
	 * 
	 * @param message
	 *            The message to be sent to the Player's console or the mob.
	 */
	public void sendToPlayer(String message);

	/**
	 * Attack is the method called when a Mobile's turn comes up in a combat. A
	 * Mobile looks to it's strategy in order to determine an appropriate
	 * response - For many mobs, this response is to call resolveAttack, and
	 * deal damage to another Movable.
	 * 
	 * @enemy - The Movable to attack.
	 */
	public void attack(Character attackerTwo);

	
	/**
	 * setStat is a universal setter. It accepts an integer and a Trait enum,
	 * which it then uses to change the appropriate private variable
	 * representing the requested stat to be changed. This cuts down on the
	 * number of methods required to set all instance variables.
	 * 
	 * @param value
	 *            The integer value one wishes to set.
	 * @param stat
	 *            The enum member representing the stat the caller wishes
	 *            modified.
	 */

	public void setStat(int value, Trait stat);

	/**
	 * getStat is a universal getter. It accepts a Trait enum, which it then
	 * uses to get the appropriate private variable representing the requested
	 * stat requested. This cuts down on the number of methods required to get
	 * all instance variables.
	 * 
	 * @return int - An int that represents the value of the requested stat.
	 */
	public int getStat(Trait stat);
	
	/**
	 * This method insures the correct roomId of the movable, in case there is a
	 * synch or serialization issue.
	 * 
	 * @return An int that represents the movables object reference id in the
	 *         database.
	 */
	public int getRoomId();
	
	/**
	 * getFighting is a small method that is used to prevent a player from
	 * getting into more than one fight at once. A MOB, however, can be attacked
	 * by multiple human players.
	 * 
	 * @return True if fighting, false otherwise
	 */
	public boolean getFighting();

	/**
	 * setFighting will be used once a player enters combat. It pass in a true
	 * value to set to the player's isFighting variable.
	 * 
	 * @param fighting
	 *            Ture if entering a fight, false if ending combat
	 */
	public void setFighting(boolean fighting);
	
	public void setRoomId(int id);
}
