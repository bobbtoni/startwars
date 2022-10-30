package ru.bobb.startwars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeVelocityCommand implements ICommand {
	
	private final UObject object;

	@Override
	public void execute() {
		final Vector velocity = (Vector) object.getProperty("velocity");
		final int d = (int) object.getProperty("direction");
		
		final int x = velocity.getX();
		final int y = velocity.getY();
		final double cos = Math.cos(d);
		final double sin = Math.sin(d);    
		final double nx = x * cos - y * sin;
		final double ny = x * sin + y * cos;
		object.setProperty("velocity", new Vector((int)nx, (int)ny));
	}

}
