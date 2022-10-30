package ru.bobb.startwars;

public class SecondRetryCommand implements ICommand {
	
	private final ICommand command;
	
	public SecondRetryCommand(final ICommand command) {
		this.command = command;
	}

	@Override
	public void execute() {
		this.command.execute();
	}
}
