package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BurnFuelCommand implements ICommand {

	private final UObject object;
	
	@Override
	public void execute() {
		final int fuelReserve = (int) object.getProperty("fuelReserve");
		final int fuelSpent = (int) object.getProperty("fuelSpent");
		
		final int fuelRemain = fuelReserve - fuelSpent;
		
		object.setProperty("fuelReserve", fuelRemain);
	}
}
