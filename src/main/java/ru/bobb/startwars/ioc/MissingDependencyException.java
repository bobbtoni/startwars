package ru.bobb.startwars.ioc;

public class MissingDependencyException extends RuntimeException {
	public MissingDependencyException(String name) {
		super("Не удалось разрешить зависимость " + name);
	}

}
