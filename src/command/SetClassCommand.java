package command;

import util.ClassSetter;
import util.PlayerMover;
import world.CharacterClass;
import world.Direction;
import world.Interpreter;
import world.Player;

public class SetClassCommand extends Command {
	private String _class;

	public SetClassCommand(String className) {
		super("setclass", "<class>: changes your class into the one specified by your input.");
		_class = className;
	}

	@Override
	public synchronized void execute(Player player) {
		CharacterClass set = ClassSetter.setCharacterClass(player, _class);
		if (set == null)
			set = player.getCharacterClass();
		player.setCharacterClass(set);
	}

}
