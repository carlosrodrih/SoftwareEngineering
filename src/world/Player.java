package world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

/**
 * Players are an object in the world, which acts as an avatar for those
 * connected to the MUD, and stores their character. It extends DatabaseObject
 * and implements the Movable interface.
 * 
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrine
 * 
 */
public class Player extends DatabaseObject implements Movable {

	private static final long serialVersionUID = 1L;
	private CharacterClass gameClass;
	private int agility;
	private int hitPoints;
	private int intellect;
	private int maxHitPoints;
	private int maxTechnique;
	private int strength;
	private int technique;
	private int toughness;
	private transient server.Client client;
	private String password;
	private GearList gearList;
	private int roomId;
	private Weapon weaponEquipped;
	private Armor armorEquipped;
	private Player mySelf;
	private boolean isFighting;

	/**
	 * The Player constructor takes a name to set the name of the player.
	 * Default stats, weapon, and equipment are added here as well.
	 * 
	 * @param name
	 *            - The String that represents the players name.
	 */
	public Player(String name) {
		super(name);
		this.roomId = 1;
		this.agility = 3;
		this.intellect = 3;
		this.strength = 3;
		this.toughness = 3;

		setCharacterClass(Gunner.getInstance());

		this.maxHitPoints = toughness * 10;
		this.hitPoints = maxHitPoints;
		this.maxTechnique = intellect * 5;
		this.technique = maxTechnique;
		this.isFighting = false;

		this.gearList = new GearContainer(name + "'s gear", name + "'s gear:",
				20, false);
		this.gearList.setLocation(this);

		// This is the default _default_ gear.
		weaponEquipped = new Weapon("Fist",
				"It's a fist, at least you know how to make one", 1, 1);
		armorEquipped = new Armor(
				"Jumpsuit",
				"basic clothes. You wouldn't like to be fighting in just these.",
				1, 'L');

		// Default gear. not equipped, but available.
		world.World
				.getInstance()
				.addGearToWorld(
						new HealthOrb(
								"Life Orb",
								"(A health orb, adds 5 hit points when used. Regenerates every 10 seconds. Dropping it would be a bad idea.)",
								5), this);

		world.World.getInstance().addGearToWorld(
				new Weapon("Light Pistol", "(Not a Knife.)", 1, 3), this);

		world.World.getInstance().addGearToWorld(
				new Weapon("Combat Knife", "(THIS is a Knife.)", 1, 3), this);

		world.World.getInstance().addGearToWorld(
				new Armor("Light Combat Armor", "(Better than a flightsuit.)"),
				this);
		this.getArmor().setArmorType('M');

		mySelf = this;
	}

	@Override
	public void moveToRoom(Room destination) {

		if (this.getLocation() != null) {
			((Room) this.getLocation()).remove(this.getName());
		}
		this.setLocation(destination);
		destination.add(this);

		this.roomId = ((Room) this.getLocation()).getDatabaseRef();
		sendToPlayer(((Room) this.getLocation()).generateDescription());
	}

	@Override
	public void setStat(int value, Trait stat) {
		if (stat == Trait.AGILITY)
			agility = value;
		else if (stat == Trait.HITPOINTS) {
			if (value > this.maxHitPoints) {
				hitPoints = this.maxHitPoints;
			} else {
				hitPoints = value;
			}
		} else if (stat == Trait.INTELLECT)
			intellect = value;
		/*else if (stat == Trait.MAXHITPOINTS)
			maxHitPoints = value;
		else if (stat == Trait.TECHNIQUE)
			technique = value;
		else if (stat == Trait.MAXTECHNIQUE)
			maxTechnique = value;*/
		else if (stat == Trait.STRENGTH)
			strength = value;
		else {
			toughness = value;
			this.maxHitPoints = toughness * 10;
			hitPoints = maxHitPoints;
		}

	}

	@Override
	public void use(String itemName) {
		if (this.gearList.getGear(itemName) != null) {
			this.gearList.getGear(itemName).getDefaultBehavior(this);
		} else
			this.sendToPlayer("You don't have " + itemName);
	}

	@Override
	public int getStat(Trait stat) {
		if (stat == Trait.AGILITY)
			return agility;
		else if (stat == Trait.HITPOINTS)
			return hitPoints;
		else if (stat == Trait.INTELLECT)
			return intellect;
		/*else if (stat == Trait.MAXHITPOINTS)
			return maxHitPoints;
		else if (stat == Trait.TECHNIQUE)
			return technique;
		else if (stat == Trait.MAXTECHNIQUE)
			return maxTechnique;*/
		else if (stat == Trait.STRENGTH)
			return strength;
		else
			return toughness;

	}

	@Override
	public void sendToPlayer(String message) {
		if (client != null) {
			client.sendReply(message);
		}
	}

	@Override
	public void attack(Movable enemy) {
		// Determine Hit
		int attackRoll = (int) (Math.random() * 10) + this.agility
				- enemy.getStat(Trait.AGILITY);

		// Miss Conditions
		if (attackRoll < 3) {

			this.sendToPlayer("You miss.");
			enemy.sendToPlayer(this.getName() + " misses you.");

			// Attack hits!
		} else {
			int damage = 0;

			// Versus Mobiles
			if (enemy instanceof Mobile) {
				damage = Math.max(0, (int) (this.strength
						+ this.getWeapon().getDamage() - enemy
						.getStat(Trait.TOUGHNESS)));
			}
			// Versus Players
			if (enemy instanceof Player) {
				damage = Math
						.max(1, (int) (
						// Add Str,
								this.strength
								// Add Weapon Damage,
										+ this.getWeapon().getDamage()
										// Minus a third of player's Toughness
										- (((Player) enemy)
												.getStat(Trait.TOUGHNESS) / 3)
								// Minus armor's DR
								- ((Player) enemy).getArmor()
										.getDamageReduction()));

			}

			int newHP = enemy.getStat(Trait.HITPOINTS) - damage;
			enemy.setStat(newHP, Trait.HITPOINTS);
			this.sendToPlayer("You hit " + ((DatabaseObject) enemy).getName()
					+ " for " + damage + " hitpoints!");
			this.sendToPlayer(enemy.getName() + " HP: "
					+ ((Movable) enemy).getStat(Trait.HITPOINTS));
			enemy.sendToPlayer(this.getName() + " damages you for " + damage
					+ " hitpoints!");
		}

	}

	@Override
	public int getRoomId() {
		return this.roomId;
	}

	@Override
	public boolean addGear(Gear item) {
		return this.addGear(this, item);
	}

	@Override
	public boolean addGear(Movable movableToNotify, Gear gear) {
		return this.gearList.addGear(this, gear);
	}

	@Override
	public boolean giveGear(Movable movableToNotify, String itemName,
			String otherName) {
		if (itemName.equalsIgnoreCase("fist")
				|| itemName.equalsIgnoreCase("jumpsuit")) {
			if (World.getInstance().playerExists(otherName)) {
				World.getInstance().getPlayer(otherName).sendToPlayer(
						"You may not take that piece of equipment.");
				return false;
			}

		}
		boolean giveSuccess = this.gearList.giveGear(movableToNotify, itemName,
				otherName);
		if (giveSuccess) {
			if (this.armorEquipped.getName().equalsIgnoreCase(itemName)) {
				this.armorEquipped = new Armor(
						"Jumpsuit",
						"basic clothes. You wouldn't like to be fighting in just these.",
						1, 'L');
			} else if (this.weaponEquipped.getName().equalsIgnoreCase(itemName)) {
				this.weaponEquipped = new Weapon("Fist",
						"It's a fist, at least you know how to make one", 1, 1);
			} else {
				// Gear already removed, wasn't equipped by the player but in
				// there bag
			}
		}
		return giveSuccess;
	}

	@Override
	public Gear getGear(String itemName) {
		return this.gearList.getGear(itemName);
	}

	@Override
	public boolean canBeCarried() {
		return this.gearList.canBeCarried();
	}

	@Override
	public int getMaxGearCount() {
		return this.gearList.getMaxGearCount();
	}

	@Override
	public void setMaxGearCount(int max) {
		this.gearList.setMaxGearCount(max);

	}

	@Override
	public List<Gear> listGear() {
		return this.gearList.listGear();
	}

	@Override
	public String inspect() {
		return this.gearList.inspect();
	}

	@Override
	public void dropGear(String itemName, Room room, Movable movableToNotify) {
		this.gearList.dropGear(itemName, room, this);
	}

	/**
	 * The toString method overrides Object's toString method. This String is
	 * the way that the player will be seen in the room, and uses the name
	 * inherited from Database Object.
	 * 
	 * @return String of text from the room that the Mobile sees.
	 */
	@Override
	public String toString() {
		return this.getName() + " (DB:" + this.getDatabaseRef() + ")";
	}

	/**
	 * This method drops a player's gear item with the specified name.
	 * 
	 * @param itemName
	 *            - The name of the gear to drop.
	 */
	public void dropGear(String itemName) {
		this.dropGear(itemName, (Room) this.getLocation(), this);
	}

	/**
	 * getStats() will return a multiple-line report of the user's current
	 * statistics.
	 * 
	 * 
	 * @return String to be displayed to Player to show current status.
	 */
	public String getStats() {
		String result = this.getName() + ", the "
				+ this.getCharacterClass().toString() + "\n";
		result += "Agility: " + this.agility + "\n";
		result += "Intellect: " + this.intellect + "\n";
		result += "Strength: " + this.strength + "\n";
		result += "Toughness: " + this.toughness + "\n";
		result += "HitPoints: " + this.hitPoints + "/" + this.maxHitPoints
				+ "\n";
		result += "Technique: " + this.technique + "/" + this.maxTechnique
				+ "\n";
		result += "Weapon in Hand: " + this.getWeapon().getName() + "(Damage: "
				+ this.getWeapon().getDamage() + ")\n";
		result += "Armor Worn: " + this.getArmor().getName() + "(Rating: "
				+ this.getArmor().getDamageReduction() + ")\n";
		return result;
	}

	/**
	 * This method sets a player's password.
	 * 
	 * @param password
	 *            - A String that represents the players password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method validates a player's password.
	 * 
	 * @param password
	 * @return a boolean, true if player password matches passed parameter
	 *         password.
	 */
	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	/**
	 * setClient() associates a Client object with the Player object, allowing
	 * methods to send text back to the player.
	 * 
	 * @param client
	 *            The Client object to associate with the player object.
	 */
	public void setClient(server.Client client) {
		this.client = client;
	}

	/**
	 * This method sets the Armor that the player will equip.
	 * 
	 * @param armorToEquip
	 *            - The Armor to equip.
	 */
	public void setArmor(Gear armorToEquip) {
		if (armorToEquip instanceof Armor) {

			if (this.listGear().contains(armorToEquip)) {
				this.armorEquipped = (Armor) armorToEquip;
				this.sendToPlayer("You have equipped " + armorToEquip.getName()
						+ " armor.");

			} else {
				this
						.sendToPlayer("You do not have that armor in your inventory");
			}
		} else {
			this.sendToPlayer("That is a not a armor item.");

		}
	}

	/**
	 * This method returns the Armor the player has equipped.
	 * 
	 * @return - An Armor object that represents the Armor the player has
	 *         equipped.
	 */
	public Armor getArmor() {
		return this.armorEquipped;
	}

	/**
	 * The method sets the Weapon the player will equip.
	 * 
	 * @param weaponToEquip
	 *            - The Weapon object the player will equip.
	 */
	public void setWeapon(Weapon weaponToEquip) {
		if (weaponToEquip instanceof Weapon) {

			if (this.listGear().contains(weaponToEquip)) {
				this.weaponEquipped = (Weapon) weaponToEquip;
				this.sendToPlayer("You decided to use the "
						+ weaponToEquip.getName() + " weapon.");

			} else {
				this
						.sendToPlayer("You do not have that weapon in your inventory");
			}
		} else {
			this.sendToPlayer("That is a not a weapon item.");

		}
	}

	/**
	 * This method gets the Player's equipped Weapon.
	 * 
	 * @return - The Weapon the player has equipped.
	 */
	public Weapon getWeapon() {
		if (this.weaponEquipped != null) {
			return weaponEquipped;
		}
		return null;
	}

	/**
	 * not implemented yet.
	 * 
	 * setClass will be used whenever a new player is created. When the user
	 * selects a class for there character setClass will be called.
	 * 
	 * @param classToSet
	 *            The class that is selected by the user
	 */
	public void setCharacterClass(CharacterClass classToSet) {
		if (gameClass != null) {
			this.setStat(strength - gameClass.getMod(Trait.STRENGTH),
					Trait.STRENGTH);
			this.setStat(agility - gameClass.getMod(Trait.AGILITY),
					Trait.AGILITY);
			;
			this.setStat(toughness - gameClass.getMod(Trait.TOUGHNESS),
					Trait.TOUGHNESS);
			;
			this.setStat(intellect - gameClass.getMod(Trait.INTELLECT),
					Trait.INTELLECT);
			;
		}

		gameClass = classToSet;

		strength = strength + gameClass.getMod(Trait.STRENGTH);
		agility = agility + gameClass.getMod(Trait.AGILITY);
		toughness = toughness + gameClass.getMod(Trait.TOUGHNESS);
		intellect = intellect + gameClass.getMod(Trait.INTELLECT);
		this.sendToPlayer("Your class has been set to "
				+ this.getCharacterClass().toString());
	}

	/**
	 * not implemented yet.
	 * 
	 * getCharacterClass will return the class of the player object.
	 * 
	 * @return The class of the current character
	 */
	public CharacterClass getCharacterClass() {
		return this.gameClass;

	}

	/**
	 * not implemented yet.
	 * 
	 * setRace will be used whenever a new character is created. When the user
	 * selects a race for there character setRace will be called.
	 * 
	 * @param raceToSet
	 *            Race that was selected by the user
	 */

	/**
	 * not implemented yet.
	 * 
	 * getRace will return the race of the player object.
	 * 
	 * @return The race of the current character
	 */

	/**
	 * respawn will be called whenever a player is killed during combat. It will
	 * place the player back at the landing pad, where new player's spawn. it
	 * will set both there hit points and technique to max.
	 */
	public void respawn() {
		mySelf.setLocation(World.getInstance().getDatabaseObject(1));
		mySelf.moveToRoom((Room)World.getInstance().getDatabaseObject(1));
		mySelf.setStat(maxHitPoints, Trait.HITPOINTS);
		//mySelf.setStat(maxTechnique, Trait.TECHNIQUE);
	}

	@Override
	public boolean getFighting() {
		return this.isFighting;
	}

	@Override
	public void setFighting(boolean fighting) {
		this.isFighting = fighting;
	}
	
	public void technique(Movable target) {
		gameClass.technique(this, target);
	}

}
