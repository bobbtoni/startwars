package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bobb.startwars.codegenerator.AdapterGenerator;

public class AdapterGeneratorTest {
	
	@Test
	public void test() {
		UObject object = new UObject();
		object.setProperty("velocity", new Vector(1, 1));
		
		IMovable instance = AdapterGenerator.generateInstance(IMovable.class, object);
		
		assertEquals(new Vector(1, 1), instance.getVelocity());
	}
}
