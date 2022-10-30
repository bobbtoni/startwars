package ru.bobb.startwars;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogCommand implements ICommand {
	
	private final ICommand command;
	private final Exception exception;
	
	public LogCommand(final ICommand command, final Exception exception) {
		this.command = command;
		this.exception = exception;
	}

	@Override
	public void execute() {
		log.error("Command: {} Exception: {}", command, exception);
	}
	
}
