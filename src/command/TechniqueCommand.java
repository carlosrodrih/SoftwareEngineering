package command;

import util.PlayerMover;
import world.CombatManager;
import world.Direction;
import world.Dreadnaught;
import world.Gunner;
import world.Movable;
import world.Player;
import world.Room;
import world.World;

public class TechniqueCommand extends Command {
	private String _target;

	public TechniqueCommand(String target) {
		super("technique", "<mob>: initializes a technique attack on a MOB, depending on the your class");
		_target = target;
	}

	@Override
	public synchronized void execute(Player player) {		
		if (player.getFighting() == true) {
			player.sendToPlayer("You cannot attack because you are already in battle");
		}
		for (Movable i : ((Room) player.getLocation()).listMovables()) {
			if (i.getName().equalsIgnoreCase(_target)) {

				if (i.getFighting()) {
					player.sendToPlayer(i.getName() + " is already in battle");
					return;
				}
				
				player.technique(i);
				return;
			}

		}
		player.sendToPlayer("Player, " + _target
				+ " was not found in the room.");

	}

}
