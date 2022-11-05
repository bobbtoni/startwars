package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;
import ru.bobb.startwars.ioc.MissingDependencyException;
import ru.bobb.startwars.ioc.ObjectFactory;

public class IoCTest {
	
	@Test
	public void registerDependencyTest() {
		IoC.<ICommand>resolve("IoC.Register", "Move", (ObjectFactory) (args) -> new MoveAdapter((UObject) args[0])).execute();
		final ICommand command = IoC.resolve("Move", new UObject());
		assertEquals(MoveAdapter.class, command.getClass());
	}
	
	@Test(expected = MissingDependencyException.class)
	public void missingDependencyTest() {
		IoC.resolve("Move", new UObject());
	}
	
	@Test
	public void switchScopeTest() {
		IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new MoveAdapter((UObject) args[0])).execute();
		
		IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new TurnableAdapter((UObject) args[0])).execute();
		
		IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
		final ICommand testCommandFromScope1 = IoC.resolve("Test", new UObject());
		assertEquals(MoveAdapter.class, testCommandFromScope1.getClass());
		
		IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
		final ICommand testCommandFromScope2 = IoC.resolve("Test", new UObject());
		assertEquals(TurnableAdapter.class, testCommandFromScope2.getClass());
	}
	
	
	
}
