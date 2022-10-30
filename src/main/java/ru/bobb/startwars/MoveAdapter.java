package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MoveAdapter implements IMovable {
	
	private final UObject object;

	@Override
	public void execute() {
		final Vector velocity = (Vector) object.getProperty("velocity");
		final Vector currentPos = (Vector) object.getProperty("position");
		
		final Vector newPos = currentPos.add(velocity);
		object.setProperty("position", newPos);
	}
	
}
