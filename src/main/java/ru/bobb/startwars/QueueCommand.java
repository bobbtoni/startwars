package ru.bobb.startwars;

import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;

public class QueueCommand {
	private ConcurrentLinkedQueue<ICommand> queue = new ConcurrentLinkedQueue<>();
	@Getter
	private ExceptionHandler exceptionHandler = new ExceptionHandler();
	
	public void push(ICommand command) {
		queue.add(command);
	}
	
	public void start() {
		while (!queue.isEmpty()) {
			final ICommand command = queue.poll();
			if (command != null) {
				try {
					command.execute();
				} catch (Exception e) {
					this.push(exceptionHandler.handle(command, e));
				}
			}
		}
	}
}
