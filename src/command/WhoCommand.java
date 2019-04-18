package command;

import util.PlayerMover;
import world.Direction;
import world.Player;
import world.World;

public class WhoCommand extends Command {
	private World world;

	public WhoCommand() {
		super("who", ": lists all the players that are logged in.");
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();

		String result = "chat Right now, ";
		for (String name : world.getPlayersLoggedOn()) {
			result += " " + name + ",";
		}
		result = result.substring(0, result.length()-1);
		if (world.getPlayersLoggedOn().size() > 1) {
			result += " are connected.";
		} else {
			result += " is connected.";
		}

		player.sendToPlayer(result);
	}

}
