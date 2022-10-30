package ru.bobb.startwars;

public class FirstRetryCommand implements ICommand {
	
	private final ICommand command;
	
	public FirstRetryCommand(final ICommand command) {
		this.command = command;
	}

	@Override
	public void execute() {
		this.command.execute();
	}
}
