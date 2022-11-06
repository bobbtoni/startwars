package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class BurnFuelTest {
	
	@Test
	public void burnFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 100);
		final IFuelable fuelableObject = IoC.resolve("Adapter", IFuelable.class, object);
		final ICommand checkFuelCmd = new BurnFuelCommand(fuelableObject);
		checkFuelCmd.execute();
		assertEquals(100, object.getProperty("fuelReserve"));
	}
	
}
