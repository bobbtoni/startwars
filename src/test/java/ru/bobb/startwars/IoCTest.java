package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;
import ru.bobb.startwars.ioc.MissingDependencyException;
import ru.bobb.startwars.ioc.ObjectFactory;

public class IoCTest {
	
	@Test
	public void registerDependencyTest() {
		IoC.<ICommand>resolve("IoC.Register", "Move", (ObjectFactory) (args) -> new MoveCommand((IMovable) args[0])).execute();
		final ICommand command = IoC.resolve("Move", IoC.<IMovable>resolve("Adapter", IMovable.class, new UObject()));
		assertEquals(MoveCommand.class, command.getClass());
	}
	
	@Test(expected = MissingDependencyException.class)
	public void missingDependencyTest() {
		IoC.resolve("Move", new UObject());
	}
	
	@Test
	public void switchScopeTest() {
		IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new MoveCommand((IMovable) args[0])).execute();
		
		IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new TurnableCommand((ITurnable) args[0])).execute();
		
		IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
		final ICommand testCommandFromScope1 = IoC.resolve("Test", IoC.<IMovable>resolve("Adapter", IMovable.class, new UObject()));
		assertEquals(MoveCommand.class, testCommandFromScope1.getClass());
		
		IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
		final ICommand testCommandFromScope2 = IoC.resolve("Test", IoC.<IMovable>resolve("Adapter", ITurnable.class, new UObject()));
		assertEquals(TurnableCommand.class, testCommandFromScope2.getClass());
	}
	
	
	
}
