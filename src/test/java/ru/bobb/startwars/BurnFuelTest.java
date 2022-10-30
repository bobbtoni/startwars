package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

public class BurnFuelTest {
	
	@Test
	public void burnFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 100);
		final ICommand checkFuelCmd = new BurnFuelCommand(object);
		checkFuelCmd.execute();
		assertEquals(100, object.getProperty("fuelReserve"));
	}
	
}
