package world;

import java.util.*;
import command.*;
import util.ClassSetter;
import util.CommandInvoker;

/**
 * Interpreter is the class that will handle all of the commands that are sent
 * over the network. It will contain a HashMap that will hold String's as the
 * key which are the commands and the commands' descriptions as the value. It
 * has a world instance variable that is a reference to the World.getInstance()
 * singleton. The Interpreter class is also a singleton itself, having a private
 * constructor, private static instance variable of itself, and a public static
 * getInstance method.
 * 
 * @author Matt Turner, Ross Bottorf, Zach Boe, Jonathan Perrine
 * 
 */
public class Interpreter {
	private static Command[] commandsList = {
			new LookCommand(),
			new NorthCommand(),
			new SouthCommand(),
			new EastCommand(),
			new WestCommand(),
			new UpCommand(),
			new DownCommand(),
			new SayCommand(null),
			new TellCommand(null, null),
			new RudeCommand(),
			new ScoreCommand(),
			new GiveCommand(null, null),
			new GetCommand(null),
			new InventoryCommand(),
			new DropCommand(null),
			new UseCommand(null),
			new ShutdownCommand(),
			new SaveCommand(),
			new DescribemeCommand(null),
			new HelpCommand(),
			new InspectCommand(null),
			new AttackCommand(null),
			new TechniqueCommand(null),
			new SetClassCommand(null)
	};
	private static List<String> commandHelp = new ArrayList<String>();
	private static List<String> commandKeys = new ArrayList<String>();
	private static Interpreter instance = new Interpreter();
	private World world;

	// This private constructor initializes the command list, it is private so
	// Interpreter can keep its singleton status.
	private Interpreter() {

		this.world = World.getInstance();

		for (Command i : commandsList) {
			commandHelp.add(i.helpText());
			commandKeys.add(i.key());
		}
	}

	/**
	 * processCommand(Player,Sting) will take in a player that is the player
	 * that called the command and a String that will hold the text that the
	 * player entered. It will take the String and search the keys of the
	 * HashMap and if it is found will run the command value associated with it.
	 * If not it will send it to the room that the player is in and search its
	 * HashMap for the command.
	 * 
	 * @param player
	 *            Player object that represents the player that sent the command
	 * @param textCommand
	 *            The string that the player entered
	 */
	public synchronized void processCommand(Player player, String textCommand) {
		Scanner scanner = new Scanner(textCommand);
		if (scanner.hasNext()) {
			String command = scanner.next().trim().toLowerCase();

			// Say
			if (command.equals("say")) {
				if (scanner.hasNextLine()) {
					CommandInvoker.setCommand(new SayCommand(scanner.nextLine().trim()));
				} else {
					player.sendToPlayer("Say what?");
				}

				// Emote
			} /*else if (command.equals("emote")) {
				if (scanner.hasNextLine()) {
					((Room) player.getLocation()).sendToRoom(player.getName()
							+ " " + scanner.nextLine().trim());
				} else {
					player.sendToPlayer("Emote what?");
				}

				// Social Commands
			}*/ else if (command.equals("rude")) {
				if (scanner.hasNextLine()) {
					CommandInvoker.setCommand(new RudeCommand(scanner.next()));
				} else {
					CommandInvoker.setCommand(new RudeCommand());
				}

				// Commands
			} else if (command.equals("commands")) {
				CommandInvoker.setCommand(new HelpCommand());
				
				// Who
			} else if (command.equals("who")) {
				CommandInvoker.setCommand(new WhoCommand());

				// Save
			} else if (command.equals("save")) {
				CommandInvoker.setCommand(new SaveCommand());

				// Describe me
			} else if (command.equals("describeme")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new DescribemeCommand(scanner.nextLine().trim()));
				} else {
					player.sendToPlayer("The describeme command should be followed by a description of yourself.");
				}
				
				// Tell
			} else if (command.equals("tell")) {
				if (scanner.hasNext()) {
					String otherPlayerName = scanner.next().trim();
					if (scanner.hasNextLine()) {
						CommandInvoker.setCommand(new TellCommand(otherPlayerName, scanner.nextLine().trim()));
					} else
						player.sendToPlayer("Tell them what?");
				} else
					player.sendToPlayer("Tell who... What?");

				// Look
			} else if (command.equals("look")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new LookCommand(scanner.nextLine().trim()));
				} else {
					CommandInvoker.setCommand(new LookCommand());
				}
				
				// Shutdown
			} else if (command.equals("inspect")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new InspectCommand(scanner.nextLine().trim()));
				} else {
					player.sendToPlayer("Inspect what?");
				}

			} else if (command.equals("shutdown")) {
				CommandInvoker.setCommand(new ShutdownCommand());

				// Inventory
			} else if (command.equals("inventory") || command.equals("i")) {
				CommandInvoker.setCommand(new InventoryCommand());

				// Score
			} else if (command.equals("score")) {
				CommandInvoker.setCommand(new ScoreCommand());

				// Drop
			} else if (command.equals("drop")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new DropCommand(scanner.next().trim()));
				} else {
					player.sendToPlayer("Drop what?");
				}

				// Use
			} else if (command.equals("use")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new UseCommand(scanner.next().trim()));
				} else {
					player.sendToPlayer("Use what?");
				}
				
				// Get
			} else if (command.equals("get")) {
				if (scanner.hasNext()) {
					String itemName = scanner.next().trim().toLowerCase();
					if (scanner.hasNext()) {
						CommandInvoker.setCommand(new GetCommand(itemName, scanner.next().trim().toLowerCase()));
					} else {
						CommandInvoker.setCommand(new GetCommand(itemName));
					}
				} else {
					player.sendToPlayer("Get what?");
				}

				// Give
			} else if (command.equals("give")) {
				if (scanner.hasNext()) {
					String itemName = scanner.next().trim().toLowerCase();
					if (scanner.hasNext()) {
						CommandInvoker.setCommand(new GiveCommand(itemName, scanner.next().trim().toLowerCase()));
					} else {
						player.sendToPlayer("Give " + itemName
								+ " to who or what?");
					}
				} else {
					player.sendToPlayer("Give what?");
				}

				// Set Class
			} else if (command.equals("setclass")) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new SetClassCommand(scanner.next().trim().toLowerCase()));
				} else {
					player.sendToPlayer(ClassSetter.getWrongInputText());
				}

				// Movement
			} else if (command.equals("north") || command.equals("n")) {
				CommandInvoker.setCommand(new NorthCommand());
			} else if (command.equals("south") || command.equals("s")) {
				CommandInvoker.setCommand(new SouthCommand());
			} else if (command.equals("east") || command.equals("e")) {
				CommandInvoker.setCommand(new EastCommand());
			} else if (command.equals("west") || command.equals("w")) {
				CommandInvoker.setCommand(new WestCommand());
			} else if (command.equals("up") || command.equals("u")) {
				CommandInvoker.setCommand(new UpCommand());
			} else if (command.equals("down") || command.equals("d")) {
				CommandInvoker.setCommand(new DownCommand());

				// Combat related.
			} else if ((command.equals("attack"))) {
				if (scanner.hasNext()) {
					CommandInvoker.setCommand(new AttackCommand(scanner.next().trim()));
				} else {
					player.sendToPlayer("Who do you want to attack? (Attack <victim>)");
				}

			} else {
				player.sendToPlayer(command + " is not understood.");
			}
			
			CommandInvoker.execute(player);
		}
	}

	/**
	 * This method returns the Interpreter's world instance variable. Can be
	 * used by the server.Client (when we make all methods in the world package
	 * (except this one and processCommand) package only).
	 * 
	 * @return - A World object that represents the current Mud World.
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * getCommands will return a List of the available commands.
	 * 
	 * 
	 * @return - A List<String> of the available commands.
	 */
	public List<String> getCommands() {
		return commandKeys;
	}

	/**
	 *getInstance() returns a static reference to this Interpreter following
	 * the Singleton pattern. This will be used by server.Client to gain a
	 * reference to the interpreter to which it is sending commands.
	 * 
	 * @return a reference to the Singleton Interpreter.
	 */
	public static Interpreter getInstance() {
		return instance;
	}
	
	public static String commandDescriptions() {
		String ret = "";
		
		for (String i : commandHelp)
			ret += String.format("%s%n", i);
		
		return ret;
	}

	
}
