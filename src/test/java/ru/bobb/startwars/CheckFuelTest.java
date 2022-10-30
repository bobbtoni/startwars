package ru.bobb.startwars;

import org.junit.Test;

public class CheckFuelTest {
	
	@Test
	public void successCheckFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 100);
		final ICommand checkFuelCmd = new CheckFuelCommand(object);
		checkFuelCmd.execute();
	}
	
	@Test(expected = RuntimeException.class)
	public void failCheckFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 2);
		object.setProperty("fuelSpent", 11);
		final ICommand checkFuelCmd = new CheckFuelCommand(object);
		checkFuelCmd.execute();
	}
}
