package command;

import world.Player;

public class ScoreCommand extends Command {

	public ScoreCommand() {
		super("score", ": displays the players current status/information.");
	}

	@Override
	public synchronized void execute(Player player) {
		player.sendToPlayer(player.getStats());
	}

}
