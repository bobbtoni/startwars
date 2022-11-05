package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

public class TurnCommandTest {
	@Test
	public void turnTest() {
		final UObject object = new UObject();
		object.setProperty("direction", 1);
		object.setProperty("angularVelocity", 20);
		object.setProperty("directionsNumber", 5);
		object.setProperty("velocity", new Vector(-7, 3));
		
		final ICommand turnable = new TurnCommand(object);
		turnable.execute();
		
		assertEquals(20, object.getProperty("direction"));
		assertEquals(new Vector(-5, -5), object.getProperty("velocity"));
	}
}
