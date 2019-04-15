package command;

import util.PlayerMover;
import world.Direction;
import world.Player;
import world.Room;
import world.World;

public class SayCommand extends Command {
	private String _message;
	private World world;
	
	public SayCommand(String message) {
		super("say", "<message>: sends a message to all players in the same room as the player executing the command.");
		_message = message;
	}

	@Override
	public synchronized void execute(Player player) {
		world = World.getInstance();
		
		((Room) this.world.getDatabaseObject(player.getRoomId()))
			.sendToRoom("chat " + player.getName() + " says: " + _message);
		
	}

}
