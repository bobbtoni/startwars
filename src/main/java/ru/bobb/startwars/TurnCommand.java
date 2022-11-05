package ru.bobb.startwars;

public class TurnCommand implements ICommand {
	
	private final MacroCommand macro;
	
	public TurnCommand(UObject object) {
		this.macro = new MacroCommand(new TurnableAdapter(object), new ChangeVelocityCommand(object));
	}

	@Override
	public void execute() {
		macro.execute();
	}
	
}
