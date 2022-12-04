package ru.bobb.startwars;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class CheckFuelTest {
	
	@Test
	public void successCheckFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 200);
		object.setProperty("fuelSpent", 100);
		final IFuelable fuelableObject = IoC.resolve("Adapter", IFuelable.class, object);
		final ICommand checkFuelCmd = new CheckFuelCommand(fuelableObject);
		checkFuelCmd.execute();
	}
	
	@Test(expected = RuntimeException.class)
	public void failCheckFuelTest() {
		final UObject object = new UObject();
		object.setProperty("fuelReserve", 2);
		object.setProperty("fuelSpent", 11);
		final IFuelable fuelableObject = IoC.resolve("Adapter", IFuelable.class, object);
		final ICommand checkFuelCmd = new CheckFuelCommand(fuelableObject);
		checkFuelCmd.execute();
	}
}
