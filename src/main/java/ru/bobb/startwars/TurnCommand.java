package ru.bobb.startwars;

import ru.bobb.startwars.ioc.IoC;

public class TurnCommand implements ICommand {
	
	private final MacroCommand macro;
	
	public TurnCommand(UObject object) {
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		this.macro = new MacroCommand(new TurnableCommand(turnableObject), new ChangeVelocityCommand(object));
	}

	@Override
	public void execute() {
		macro.execute();
	}
	
}
