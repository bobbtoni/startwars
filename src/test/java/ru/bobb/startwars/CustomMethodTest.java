package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;
import ru.bobb.startwars.ioc.ObjectFactory;

public class CustomMethodTest {
	
	@Test
	public void generateCustomMethodTest() {
		IoC.<ICommand>resolve("IoC.Register", "IUnderTest.Calculate", (ObjectFactory) args -> {
			return (int) args[0] + (int) args[1];
		}).execute();
		final IUnderTest testAdapter = IoC.resolve("Adapter", IUnderTest.class, null);
		assertEquals(3, (int) testAdapter.calculate(1, 2));
	}
}
