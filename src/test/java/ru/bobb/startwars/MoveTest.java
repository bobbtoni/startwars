package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;


public class MoveTest {
	
	@Test
	public void moveTest() {
		final UObject object = new UObject();
		object.setProperty("position", new Vector(12, 5));
		object.setProperty("velocity", new Vector(-7, 3));
		
		final IMove move = new MoveCommand(object);
		move.execute();
		
		assertEquals(object.getProperty("position"), new Vector(5, 8));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingPosistionTest() {
		final UObject object = new UObject();
		object.setProperty("velocity", new Vector(-7, 3));
		
		final IMove move = new MoveCommand(object);
		move.execute();
	}
}
