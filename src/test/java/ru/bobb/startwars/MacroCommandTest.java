package ru.bobb.startwars;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class MacroCommandTest {
	
	@Test
	public void successMacroCommandTest() {
		ICommand first = mock(ICommand.class);
		ICommand second = mock(ICommand.class);
		ICommand third = mock(ICommand.class);
		
		ICommand macros = new MacroCommand(first, second, third);
		macros.execute();
		
		verify(first, times(1)).execute();
		verify(second, times(1)).execute();
		verify(third, times(1)).execute();
	}
	
	@Test
	public void abortMacroCommandTest() {
		ICommand first = mock(ICommand.class);
		ICommand second = mock(ICommand.class);
		doThrow(IllegalArgumentException.class).when(second).execute();
		ICommand third = mock(ICommand.class);
		
		try {
			ICommand macros = new MacroCommand(first, second, third);
			macros.execute();
		} catch(IllegalArgumentException ex) {
			// ignore
		}
		
		verify(first, times(1)).execute();
		verify(second, times(1)).execute();
		verify(third, times(0)).execute();
	}
	
	@Test
	public void movingInLineTest() {
		final UObject object = new UObject();
		object.setProperty("position", new Vector(12, 5));
		object.setProperty("velocity", new Vector(-7, 3));
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 100);
		
		final IFuelable fuelableObject = IoC.resolve("Adapter", IFuelable.class, object);
		final ICommand checkCommand = new CheckFuelCommand(fuelableObject);
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand moveCommand = new MoveCommand(movableObject);
		final ICommand burnCommand = new BurnFuelCommand(fuelableObject);
		
		final ICommand moveInLineCommand = new MacroCommand(checkCommand, moveCommand, burnCommand);
		moveInLineCommand.execute();
		
		assertEquals(100, object.getProperty("fuelReserve"));
		assertEquals(new Vector(5, 8), object.getProperty("position"));
	}
	
	@Test
	public void failedMovingInLineTest() {
		final UObject object = new UObject();
		object.setProperty("position", new Vector(12, 5));
		object.setProperty("velocity", new Vector(-7, 3));
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 300);
		
		final IFuelable fuelableObject = IoC.resolve("Adapter", IFuelable.class, object);
		final ICommand checkCommand = new CheckFuelCommand(fuelableObject);
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand moveCommand = new MoveCommand(movableObject);
		final ICommand burnCommand = new BurnFuelCommand(fuelableObject);
		
		try {
			final ICommand moveInLineCommand = new MacroCommand(checkCommand, moveCommand, burnCommand);
			moveInLineCommand.execute();
		} catch (Exception e) {
			// ignore
		}
		
		assertEquals(200, object.getProperty("fuelReserve"));
		assertEquals(new Vector(12, 5), object.getProperty("position"));
	}
	
}
