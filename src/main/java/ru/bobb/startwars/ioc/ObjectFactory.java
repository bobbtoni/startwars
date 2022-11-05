package ru.bobb.startwars.ioc;

@FunctionalInterface
public interface ObjectFactory {
	Object resolve(Object... args);
}
