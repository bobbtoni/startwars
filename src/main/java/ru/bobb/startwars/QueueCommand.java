package ru.bobb.startwars;

import java.lang.Thread.State;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Getter;

public class QueueCommand {
	private ConcurrentLinkedQueue<ICommand> queue = new ConcurrentLinkedQueue<>();
	@Getter
	private ExceptionHandler exceptionHandler = new ExceptionHandler();
	
	private final AtomicBoolean softStopFlag = new AtomicBoolean(false);
	
	private Thread executThread = null;
	
	public QueueCommand() {
		initThread();
	}
	
	public void push(ICommand command) {
		queue.add(command);
		synchronized(executThread) {
			executThread.notifyAll();
		}
	}
	
	public void start() {
		if (executThread.getState() == State.WAITING || executThread.getState() == State.TIMED_WAITING) {
			try {
				softStop();
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				// не получилось - идем дальше
			}
		}
		if (executThread.getState() != State.NEW) {
			initThread();
		}
		softStopFlag.set(false);
		executThread.start();
	}
	
	private void initThread() {
		executThread = new Thread(() -> {
			try {
				while(!softStopFlag.get()) {
					while (!queue.isEmpty() && !executThread.isInterrupted()) {
						final ICommand command = queue.poll();
						if (command != null) {
							try {
								command.execute();
							} catch (Exception e) {
								this.push(exceptionHandler.handle(command, e));
							}
						}
					}
					synchronized(executThread) {
						executThread.wait(1000);
					}
				}
			} catch (InterruptedException e) {
				// hard stop
			}
		});
	}

	public void hardStop() {
		softStopFlag.set(true);
		executThread.stop();
	}
	
	public void softStop() {
		softStopFlag.set(true);
	}
}
