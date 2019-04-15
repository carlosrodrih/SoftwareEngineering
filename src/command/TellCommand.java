package command;

import util.PlayerMover;
import world.Direction;
import world.Player;
import world.World;

public class TellCommand extends Command {
	private String _otherPlayerName, _message;
	private World world;
	
	public TellCommand(String otherPlayer, String message) {
		super("tell", "<player> <message>: sends a message to only the player targeted.");
		_otherPlayerName = otherPlayer;
		_message = message;
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		if (world.playerIsLoggedOn(_otherPlayerName)) {
			Player otherPlayer = world.getPlayer(_otherPlayerName);
			if (otherPlayer != null) {
				otherPlayer.sendToPlayer("chat " + player.getName()
						+ " whispers : " + _message);
				player.sendToPlayer("chat You whisper to " + otherPlayer.getName()
						+ ": " + _message);
			} 
			else
				player.sendToPlayer("Player does not exist.");
		} 
		else {
			player.sendToPlayer(_otherPlayerName
					+ " is not logged on.");
		}
		
	}

}
