package ru.bobb.startwars;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueCommand {
	private AtomicBoolean launched = new AtomicBoolean(false);
	private ConcurrentLinkedQueue<ICommand> queue = new ConcurrentLinkedQueue<>();
	private ExceptionHandler exceptionHandler;
	
	public QueueCommand() {
		
	}
	
	public void push(ICommand command) {
		queue.add(command);
	}
	
	public boolean start() {
		if (launched.get()) {
			return false;
		} else {
			launched.set(true);
		}
		new Thread(() -> {
			while(launched.get()) {
				final ICommand command = queue.poll();
				if (command != null) {
					try {
						command.execute();
					} catch (Exception e) {
						exceptionHandler.handle(command, e);
					}
				}
			}
		}).start();
		return true;
	}
	
	public void stop() {
		launched.set(false);
	}
}
