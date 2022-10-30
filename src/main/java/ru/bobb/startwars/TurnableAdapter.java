package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TurnableAdapter implements ITurnable {
	
	private final UObject object;

	@Override
	public void execute() {
		final int d = (int) object.getProperty("direction");
		final int da = (int) object.getProperty("angularVelocity");
		final int n = (int) object.getProperty("directionsNumber");
		object.setProperty("direction", (d+da)/n*n);
	}

}
