package world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.Timer;

/**
 * Mobile is a class which represents Mobiles, or any non-player object with
 * behavior similar to that of a player (such as the ability to fight, or a set
 * of stats.) Like a player, it has combat stats, implements the Movable
 * interface and extends DatabaseObject.
 * 
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrine
 * 
 */
public class Mobile extends DatabaseObject implements PlayerInterface{

	private static final long serialVersionUID = 1L;
	/*private int maxTechnique;
	private int roomId;
	private Mobile mySelf;
	private GearList gearList;
	private Room startingLoc;
	private transient boolean isFighting;*/
	private int maxHealth;
	private int currentHealth;
	private int agility;
	private int strength;
	private int intellect;
	private int damage;
	private int armor;
	private Trait mainTrait;
	private Strategy strategy;
	private int lvl;
	private Gear gear;
	private String desc;

	/**
	 * A Mobile needs only a String when constructed (which becomes the Mobile's
	 * name) as default values are placed for other relevant information.
	 * Ideally, a Mobile should be further customized using the various setters.
	 * The constructor does create some default values for the mobiles stats.
	 * 
	 * It is worth noting that Mobile and Player stats appear identical, but can
	 * be different. Players rely on armor and armor - Mobs only use
	 * armor to reduce damage, so this might be higher. Similarly, strength
	 * might be higher to account for a lack of weapons.
	 * 
	 * 
	 * @param name
	 *            A String that represents the name of the new Mobile.
	 * 
	 */
	public Mobile(String name, int level, int maxHealth, Gear gear, int armor, int damage, String trait, String strat, String desc) {
		super(name);
		this.gear = gear;
		this.desc = desc;
		this.lvl = level;
		this.mainTrait = Trait.valueOf(trait);
		if(strat == "Agressive")
			setStrategy(new Agressive());
		else
			setStrategy(new PassiveAgressive());
		setStat(maxHealth, Trait.HITPOINTS);
		setStat(damage, Trait.DAMAGE);	
		setStat(armor, Trait.ARMOR);
		setTraits(level, mainTrait);
		currentHealth = this.maxHealth;
		//myStrategy = new Greets();
		//this.gearList = new GearContainer(name + "'s gear", name + "'s gear:",
		//		20, false);
		/*this.maxHitPoints = 30;
		this.maxTechnique = 4;
		this.gearList.setLocation(this);
		startingLoc = null;
		mySelf = this;
		this.isFighting = false;*/
	}

/*	@Override
	public synchronized void moveToRoom(Room destination) {

		if (this.getLocation() != null) {
			((Room) this.getLocation()).remove(this.getName());
		}

		destination.add(this);
		this.roomId = destination.getDatabaseRef();
		this.setLocation(destination);
		sendToPlayer(((Room) destination).generateDescription());
	}

	@Override
	public void use(String itemName) {
		this.gearList.getGear(itemName).getDefaultBehavior(this);
	}*/

	@Override
	public void setTraits(int lvl, Trait mainTrait) {
		int points =  3 * lvl;
		int addPoints = 6 * lvl;
		setStat(points, Trait.AGILITY);
		setStat(points, Trait.STRENGTH);
		setStat(points, Trait.INTELLECT);
		setStat(addPoints, mainTrait);
	}
	
	@Override
	public void setStat(int value, Trait stat) {
		switch (stat) {
		case AGILITY:
			this.agility = value;
			break;
		case INTELLECT:
			this.intellect = value;
			break;
		case STRENGTH:
			this.strength = value;
			break;
		case HITPOINTS:
			this.maxHealth = value * this.strength;
			break;
		case DAMAGE:
			this.damage = value * this.intellect;
			break;
		case ARMOR:
			this.armor = value * this.agility;
			break;
		}
		/*if (stat == Trait.AGILITY)
			agility = value;
		else if (stat == Trait.HITPOINTS)
			hitPoints = value;
		else if (stat == Trait.INTELLECT)
			intellect = value;
		else if (stat == Trait.MAXHITPOINTS) {
			maxHitPoints = value;
				
		else if (stat == Trait.TECHNIQUE)
			technique = value;
		} else if (stat == Trait.MAXTECHNIQUE) {
			maxTechnique = value;
			technique = maxTechnique;
		} else if (stat == Trait.STRENGTH)
			strength = value;

		else
			armor = value;*/
	}

	@Override
	public int getStat(Trait stat) {
		int value = 0;
		switch (stat) {
		case AGILITY:
			value = this.agility;
			break;
		case INTELLECT:
			value = this.intellect;
			break;
		case STRENGTH:
			value = this.strength;
			break;
		case HITPOINTS:
			value = this.maxHealth;
			break;
		case DAMAGE:
			value = this.damage;
			break;
		case ARMOR:
			value = this.armor;
			break;
		}
		return value;
	}

	public int getLvl() {
		return this.lvl;
	}
	
	public int getCurrHealth() {
		return this.currentHealth;
	}

	public Gear getGear() {
		return this.gear;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public void sendToPlayer(String message) {
		if (strategy != null)
			strategy.reactToSend(message, this);
	}

	@Override
	public void attack(Movable enemy) {
		strategy.attackBehavior(this, enemy);
	}

	/*
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
		return this.gearList.giveGear(movableToNotify, itemName, otherName);
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
	public String inspect() {
		return this.gearList.inspect();
	}

	@Override
	public void dropGear(String itemName, Room room, Movable movableToNotify) {
		this.gearList.dropGear(itemName, room, this);
	}

	public int getRoomId() {
		return this.roomId;
	}

	@Override
	public List<Gear> listGear() {
		return this.gearList.listGear();
	}
*/
	@Override
	/*
	 * The toString method overrides Object's toString method. This String is
	 * the way that the Mobile will be seen in the room, and uses the name
	 * inherited from Database Object.
	 * 
	 * @return String of text from the room that the Mobile sees.
	 */
	public String toString() {
		return this.getName() + " (DB:" + this.getDatabaseRef() + ")";
	}

	/**
	 * resolveAttack runs calculations to determine whether or not the Mobile
	 * hits an opponent in combat. This is done in two stages - An attack roll
	 * and a damage roll.
	 * 
	 * @param enemy
	 *            - The Movable to attack.
	 */
	public void resolveAttack(Movable enemy) {

		// Attack Roll
		int attackRoll = (int) (Math.random() * 10) + this.agility
				- enemy.getStat(Trait.AGILITY);
		if (attackRoll < 3) {
			this.sendToPlayer("You miss by " + (5 - attackRoll));
			enemy.sendToPlayer(this.getName() + " misses you.");
		}

		// Damage Roll
		else {
			int damage = Math.max(1, this.strength
					- (((Player) enemy).getStat(Trait.ARMOR)/3)
					- ((Player) enemy).getArmor().getDamageReduction());
			int newHP = enemy.getStat(Trait.HITPOINTS) - damage;
			enemy.setStat(newHP, Trait.HITPOINTS);
			enemy.sendToPlayer(this.getName() + " damages you for " + damage
					+ " hitpoints!");
		}

	}

	/**
	 * This method sets the strategy of the Mobile. A Strategy is used to
	 * determine how the mobile interacts with the world.
	 * 
	 * @param myStrategy
	 *            - A Strategy object used to determine the mobile's behavior.
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * This method returns the strategy of the mobile.
	 * 
	 * @return - The mobile's Strategy objectg used to determine how it behaves
	 *         with the world.
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * waitForRespawn is called whenever a player(s) kill a MOB. It will start a
	 * timer that will wait ten seconds then it will call a deadTimerListener.
	 * The listener will move the MOB back to wherever it was spawned when the
	 * server started. It will also reset the hp back to its maximum.
	 */
/*	public void waitForRespawn() {
		((Room) mySelf.getLocation()).refreshPlayers();
		((Room) mySelf.getLocation()).refreshMobiles();
		Timer deadTimer = new Timer(10000, new DeadTimerListener());
		deadTimer.setRepeats(false);
		deadTimer.start();
	}*/

	/**
	 * DeadTimerListener is the action listener for the timer that is created in
	 * the waitForRespawn method. It will call all the required classes that are
	 * needed to place a MOB back into the world and for setting there HP.
	 */
/*	private class DeadTimerListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			startingLoc.add((Mobile) mySelf);
			mySelf.moveToRoom(startingLoc);
			mySelf.setStat(maxHitPoints, Trait.HITPOINTS);
			((Room) mySelf.getLocation()).refreshPlayers();
			((Room) mySelf.getLocation()).refreshMobiles();
			((Room)mySelf.getLocation()).sendToRoom(mySelf.getName() + " has respawned");
		}
	}*/

	/**
	 * setStart is called once whenever a MOB is created for the first time. It
	 * will set a Room variable to the first location the MOB is set at. This
	 * will be used for repawning MOBs if they die.
	 * 
	 * @param startLoc
	 *            Room MOB is first placed
	 */
//	public void setStart(Room startLoc) {
//		this.startingLoc = startLoc;
//	}

	/**
	 * Not used, MOB's can be attacked by mulitple players
	 */
//	public void setFighting(boolean fighting) {
//		// Not used, MOBs can be attacked by multiple players
//	}

	/**
	 * Not used, MOB's can be attacked by mulitple players
	 */
//	public boolean getFighting() {
//		return false;
//	}
	
	
	public Mobile cloneMe() {
		return this;
	}
}
