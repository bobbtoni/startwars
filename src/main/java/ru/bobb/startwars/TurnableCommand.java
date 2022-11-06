package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TurnableCommand implements ICommand {
	
	private final ITurnable object;

	@Override
	public void execute() {
		final int d = object.getDirection();
		final int da = object.getAngularVelocity();
		final int n = object.getDirectionsNumber();
		object.setDirection((d+da)/n*n);
	}

}
