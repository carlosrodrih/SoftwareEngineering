package world;

/**
 * Mobile is a class which represents Mobiles, or any non-player object with
 * behavior similar to that of a player (such as the ability to fight, or a set
 * of stats.) Like a player, it has combat stats, implements the Movable
 * interface and extends DatabaseObject.
 * 
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrine
 * 
 */
public class Mobile extends DatabaseObject implements Character{

	private static final long serialVersionUID = 1L;
	/*private int maxTechnique;
	private Mobile mySelf;
	private GearList gearList;
	private Room startingLoc;
	private transient boolean isFighting;*/
	private int maxHealth;
	private int currentHealth;
	private int damage;
	private int toughness;
	private int accuracy;
	private int evasion;
	private Strategy strategy;
	private int lvl;
	private Gear gear;
	private int roomId;
	private String desc;
	private boolean isFighting;

	/**
	 * A Mobile needs only a String when constructed (which becomes the Mobile's
	 * name) as default values are placed for other relevant information.
	 * Ideally, a Mobile should be further customized using the various setters.
	 * The constructor does create some default values for the mobiles stats.
	 * 
	 * It is worth noting that Mobile and Player stats appear identical, but can
	 * be different. Players rely on TOUGHNESS and TOUGHNESS - Mobs only use
	 * TOUGHNESS to reduce damage, so this might be higher. Similarly, strength
	 * might be higher to account for a lack of weapons.
	 * 
	 * 
	 * @param name
	 *            A String that represents the name of the new Mobile.
	 * 
	 */
	public Mobile(String name, int level, int maxHealth, Gear gear, int toughness, int accuracy, int evasion, int damage, String strat, String desc) {
		super(name);
		this.gear = gear;
		this.desc = desc;
		this.lvl = level;
		if(strat == "Agressive")
			setStrategy(new Agressive());
		else
			setStrategy(new PassiveAgressive());
		setStat(maxHealth, Trait.HITPOINTS);
		setStat(damage, Trait.DAMAGE);	
		setStat(toughness, Trait.TOUGHNESS);
		setStat(accuracy, Trait.ACCURACY);
		setStat(evasion, Trait.EVASION);
		this.currentHealth = this.maxHealth;
		isFighting = false;
	}
	
	@Override
	public void setStat(int value, Trait stat) {
		value *= this.lvl;
		switch (stat) {
		case INTELLECT:
		case STRENGTH:
		case AGILITY:
		case TECHNIQUE:
			return;
		case MAXHITPOINTS:
			this.maxHealth = value;
			break;
		case HITPOINTS:
			this.currentHealth = value;
			break;
		case DAMAGE:
			this.damage = value;
			break;
		case TOUGHNESS:
			this.toughness = value;
			break;
		case ACCURACY:
			this.accuracy = value;
			break;
		case EVASION:
			this.evasion = value;
			break;
		}
	}

	@Override
	public int getStat(Trait stat) {
		switch (stat) {
		case INTELLECT:
		case STRENGTH:
		case AGILITY:
		case TECHNIQUE:
			return 0;
		case MAXHITPOINTS:
			return this.maxHealth;
		case HITPOINTS:
			return this.currentHealth;
		case DAMAGE:
			return this.damage;
		case TOUGHNESS:
			return this.toughness;
		case ACCURACY:
			return this.accuracy;
		case EVASION:
			return this.evasion;
		default: return 0;
		}
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
	public void attack(Character enemy) {
		strategy.attackBehavior(this, enemy);
	}

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
	public void resolveAttack(Character enemy) {

		// Attack Roll
		int attackRoll = (int) (Math.random() * 10) + this.accuracy
				- enemy.getStat(Trait.EVASION);
		if (attackRoll < 3) {
			this.sendToPlayer("You miss by " + (5 - attackRoll));
			enemy.sendToPlayer(this.getName() + " misses you.");
		}

		// Damage Roll
		else {
			int damage = Math.max(1, this.damage
					- (((Player) enemy).getStat(Trait.TOUGHNESS)/3));
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

	@Override
	public boolean getFighting() {
		return this.isFighting;
	}

	@Override
	public void setFighting(boolean fighting) {
		this.isFighting = fighting;
	}
	
	
	public Mobile cloneMe() {
		return this;
	
	}

	@Override
	public int getRoomId() {
		return this.roomId;
	}

	@Override
	public void setRoomId(int id) {
		this.roomId = id;
	}
}
