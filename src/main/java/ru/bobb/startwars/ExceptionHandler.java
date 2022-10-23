package ru.bobb.startwars;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

public class ExceptionHandler {
	
	private final Map<Key, Action> actions = new HashMap<>();
	
	public void handle(ICommand command, Exception exception) {
		final Action action = actions.get(new Key(command.getClass(), exception.getClass()));
		if (action != null) {
			action.execute(command, exception);
		}
	}
	
	public <C extends ICommand, E extends Exception> void setup(Class<C> commandClass, Class<E> exeptionClass, Action<C, E> action) {
		actions.put(new Key(commandClass, exeptionClass), action);
	}
	
	@FunctionalInterface
	public static interface Action<C extends ICommand, E extends Exception> {
		void execute(ICommand command, Exception exception);
	}
	
	@AllArgsConstructor
	@EqualsAndHashCode
	private static class Key {
		private Class<? extends ICommand> classCommand;
		private Class<? extends Exception> classException;
	}
	
}
