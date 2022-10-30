package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

public class BurnFuelTest {
	
	@Test
	public void burnFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelRate", 1);
		object.setProperty("velocity", new Vector(1, 1));
		final ICommand burnFuelCmd = new BurnFuelCommand(object);
		burnFuelCmd.execute();
		assertEquals(59, object.getProperty("fuelReserve"));
	}
	
}
