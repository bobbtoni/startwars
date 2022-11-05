package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckFuelCommand implements ICommand {
	
	private final UObject object;

	@Override
	public void execute() {
		final int fuelSpent = (int) object.getProperty("fuelSpent");
		final int fuelReserve = (int) object.getProperty("fuelReserve");
		final int fuelRemain = fuelReserve - fuelSpent;
		
		if (fuelRemain < 0) {
			throw new RuntimeException("Недостаточно топлива");
		}
	}

}
