package ru.bobb.startwars;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class MoveTest {
	
	@Test
	public void moveTest() {
		final UObject object = new UObject();
		object.setProperty("position", new Vector(12, 5));
		object.setProperty("velocity", new Vector(-7, 3));
		
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand move = new MoveCommand(movableObject);
		move.execute();
		
		assertEquals(new Vector(5, 8), object.getProperty("position"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingPosistionTest() {
		final UObject object = new UObject();
		object.setProperty("velocity", new Vector(-7, 3));
		
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand move = new MoveCommand(movableObject);
		move.execute();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingVelocityTest() {
		final UObject object = new UObject();
		object.setProperty("position", new Vector(-7, 3));
		
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand move = new MoveCommand(movableObject);
		move.execute();
	}
	
	@Test(expected = IllegalStateException.class)
	public void cannotChangePositionTest() {
		final UObject object = mock(UObject.class);
		doThrow(IllegalStateException.class).when(object).setProperty(eq("position"), any(Object.class));
		when(object.getProperty("position")).thenReturn(new Vector(12, 5));
		when(object.getProperty("velocity")).thenReturn(new Vector(-7, 3));
		
		final IMovable movableObject = IoC.resolve("Adapter", IMovable.class, object);
		final ICommand move = new MoveCommand(movableObject);
		move.execute();
	}
}
