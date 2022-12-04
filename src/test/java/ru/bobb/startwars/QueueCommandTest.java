package ru.bobb.startwars;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import ru.bobb.startwars.ioc.IoC;

public class QueueCommandTest {
	
	@Test
	public void startTest() throws InterruptedException {
		final ICommand command = mock(ICommand.class);
		
		IoC.<ICommand>resolve("Queue.Push", command).execute();
		IoC.<ICommand>resolve("Queue.Start").execute();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void softStopTest() throws InterruptedException {
		final AtomicInteger counter = new AtomicInteger(0);
		final int amountCommands = 100;
		for (int i = 0; i < amountCommands; i++) {
			final ICommand command = new ICommand() {
				
				@Override
				public void execute() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// skip
					}
					counter.getAndIncrement();
				}
			};
			IoC.<ICommand>resolve("Queue.Push", command).execute();
		}
		
		IoC.<ICommand>resolve("Queue.Start").execute();
		Thread.sleep(100);
		IoC.<ICommand>resolve("Queue.SoftStop").execute();
		
		Thread.sleep(1000);
		assertFalse(counter.get() == amountCommands);
		
		Thread.sleep(20000);
		assertTrue(counter.get() == amountCommands);
	}
	
	@Test
	public void hardStopTest() throws InterruptedException {
		final AtomicInteger counter = new AtomicInteger(0);
		final int amountCommands = 100;
		for (int i = 0; i < amountCommands; i++) {
			final ICommand command = new ICommand() {
				
				@Override
				public void execute() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// skip
					}
					counter.getAndIncrement();
				}
			};
			IoC.<ICommand>resolve("Queue.Push", command).execute();
		}
		
		IoC.<ICommand>resolve("Queue.Start").execute();
		Thread.sleep(100);
		IoC.<ICommand>resolve("Queue.HardStop").execute();
		
		Thread.sleep(1000);
		assertTrue(counter.get() < amountCommands);
		
		Thread.sleep(20000);
		assertTrue(counter.get() < amountCommands);
	}
	
	@Test
	public void restartAfterSoftStopTest() throws InterruptedException {
		
		IoC.<ICommand>resolve("Queue.Start").execute();
		IoC.<ICommand>resolve("Queue.SoftStop").execute();
		
		final ICommand command = mock(ICommand.class);
		IoC.<ICommand>resolve("Queue.Push", command).execute();
		IoC.<ICommand>resolve("Queue.Start").execute();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void restartAfterHardStopTest() throws InterruptedException {
		
		IoC.<ICommand>resolve("Queue.Start").execute();
		IoC.<ICommand>resolve("Queue.HardStop").execute();
		
		final ICommand command = mock(ICommand.class);
		IoC.<ICommand>resolve("Queue.Push", command).execute();
		IoC.<ICommand>resolve("Queue.Start").execute();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void addingHotCommandTest() throws InterruptedException {
		IoC.<ICommand>resolve("Queue.Start").execute();
		Thread.sleep(1000);
		final ICommand command = mock(ICommand.class);
		IoC.<ICommand>resolve("Queue.Push", command).execute();
		Thread.sleep(1000);
		verify(command, times(1)).execute();
	}
	
}
