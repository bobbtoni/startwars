package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckFuelCommand implements ICommand {
	
	private final IFuelable object;

	@Override
	public void execute() {
		final int fuelSpent = object.getFuelSpent();
		final int fuelReserve = object.getFuelReserve();
		final int fuelRemain = fuelReserve - fuelSpent;
		
		if (fuelRemain < 0) {
			throw new RuntimeException("Недостаточно топлива");
		}
	}

}
