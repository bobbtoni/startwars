package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BurnFuelCommand implements ICommand {

	private final IFuelable object;
	
	@Override
	public void execute() {
		final int fuelReserve = object.getFuelReserve();
		final int fuelSpent = object.getFuelSpent();
		
		final int fuelRemain = fuelReserve - fuelSpent;
		
		object.setFuelReserve(fuelRemain);
	}
}
