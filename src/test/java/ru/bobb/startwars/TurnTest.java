package ru.bobb.startwars;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class TurnTest {
	
	@Test
	public void turnTest() {
		final UObject object = new UObject();
		object.setProperty("direction", 1);
		object.setProperty("angularVelocity", 20);
		object.setProperty("directionsNumber", 5);
		
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		final ICommand turnable = new TurnableCommand(turnableObject);
		turnable.execute();
		
		assertEquals(20, object.getProperty("direction"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingDirectionTest() {
		final UObject object = new UObject();
		object.setProperty("angularVelocity", 20);
		object.setProperty("directionsNumber", 5);
		
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		final ICommand turnable = new TurnableCommand(turnableObject);
		turnable.execute();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingAngularVelocityTest() {
		final UObject object = new UObject();
		object.setProperty("direction", 20);
		object.setProperty("directionsNumber", 5);
		
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		final ICommand turnable = new TurnableCommand(turnableObject);
		turnable.execute();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void missingDirectionNumberTest() {
		final UObject object = new UObject();
		object.setProperty("angularVelocity", 20);
		object.setProperty("direction", 5);
		
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		final ICommand turnable = new TurnableCommand(turnableObject);
		turnable.execute();
	}
	
	@Test(expected = IllegalStateException.class)
	public void cannotChangeDirectionTest() {
		final UObject object = mock(UObject.class);
		when(object.getProperty("direction")).thenReturn(1);
		when(object.getProperty("angularVelocity")).thenReturn(20);
		when(object.getProperty("directionsNumber")).thenReturn(5);
		doThrow(IllegalStateException.class).when(object).setProperty(eq("direction"), any(Object.class));
		
		final ITurnable turnableObject = IoC.resolve("Adapter", ITurnable.class, object);
		final ICommand turnable = new TurnableCommand(turnableObject);
		turnable.execute();
	}

}
