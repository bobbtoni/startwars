package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MoveCommand implements ICommand {
	
	private final IMovable movableObject;

	@Override
	public void execute() {
		final Vector velocity = movableObject.getVelocity();
		final Vector currentPos = movableObject.getPosition();
		
		final Vector newPos = currentPos.add(velocity);
		movableObject.setPosition(newPos);
	}
	
}
