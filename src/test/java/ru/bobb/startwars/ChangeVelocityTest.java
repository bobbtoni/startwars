package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChangeVelocityTest {
	
	@Test
	public void changeVelosityTest() {
		final UObject object = new UObject();
		object.setProperty("velocity", new Vector(-7, 3));
		object.setProperty("direction", 1);
		
		final ICommand changeCommand = new ChangeVelocityCommand(object);
		changeCommand.execute();
		
		assertEquals(new Vector(-6, -4), object.getProperty("velocity"));
	}
}
