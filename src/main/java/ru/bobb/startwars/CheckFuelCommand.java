package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckFuelCommand implements ICommand {
	
	private final UObject object;

	@Override
	public void execute() {
		final Vector velocity = (Vector) object.getProperty("velocity");
		final int fuelRate = (int) object.getProperty("fuelRate");
		final int fuelReserve = (int) object.getProperty("fuelReserve");
		
		final int distance = (int) (Math.sqrt(velocity.getX()*velocity.getX() + velocity.getY()*velocity.getY()) * 100);
		final int fuelSpent = fuelRate * distance;
		final int fuelRemain = fuelReserve - fuelSpent;
		
		if (fuelRemain < 0) {
			throw new RuntimeException("Недостаточно топлива");
		}
	}

}
