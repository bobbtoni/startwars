package ru.bobb.startwars;

public abstract class QueueFactory {
	private static Queue queue;
	
	public static synchronized Queue resolve() {
		if (queue == null) {
			queue = new Queue();
		}
		return queue;
	}
}
