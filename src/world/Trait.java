package world;

/**
 * The Trait enum is designed to reduce the number of setter and getter methods
 * required to service all of Player and Mobile's statistic instance variables.
 * Any value which can be set using an int is represented here.
 * 
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrine
 * 
 */
public enum Trait {
	AGILITY, 			//player-only trait for damage, evasion and accuracy
	STRENGTH,			//player-only trait for damage, toughness and healthpoints
	INTELLECT, 			//player-only trait for damage and technique
	HITPOINTS,			//current hp
	TOUGHNESS, MAXHITPOINTS, DAMAGE, ACCURACY, EVASION, TECHNIQUE
}
