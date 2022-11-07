package ru.bobb.startwars;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class QueueCommandTest {
	
	@Test
	public void startTest() throws InterruptedException {
		final ICommand command = mock(ICommand.class);
		final QueueCommand queueCommand = new QueueCommand();
		queueCommand.push(command);
		queueCommand.start();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void softStopTest() throws InterruptedException {
		final AtomicInteger counter = new AtomicInteger(0);
		final QueueCommand queueCommand = new QueueCommand();
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
			queueCommand.push(command);
		}
		
		queueCommand.start();
		Thread.sleep(100);
		queueCommand.softStop();
		
		Thread.sleep(1000);
		assertFalse(counter.get() == amountCommands);
		
		Thread.sleep(10000);
		assertTrue(counter.get() == amountCommands);
	}
	
	@Test
	public void hardStopTest() throws InterruptedException {
		final AtomicInteger counter = new AtomicInteger(0);
		final QueueCommand queueCommand = new QueueCommand();
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
			queueCommand.push(command);
		}
		
		queueCommand.start();
		Thread.sleep(100);
		queueCommand.hardStop();
		
		Thread.sleep(1000);
		assertTrue(counter.get() < amountCommands);
		
		Thread.sleep(10000);
		assertTrue(counter.get() < amountCommands);
	}
	
	@Test
	public void restartAfterSoftStopTest() throws InterruptedException {
		
		final QueueCommand queueCommand = new QueueCommand();
		
		queueCommand.start();
		queueCommand.softStop();
		
		final ICommand command = mock(ICommand.class);
		queueCommand.push(command);
		queueCommand.start();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void restartAfterHardStopTest() throws InterruptedException {
		
		final QueueCommand queueCommand = new QueueCommand();
		
		queueCommand.start();
		queueCommand.hardStop();
		
		final ICommand command = mock(ICommand.class);
		queueCommand.push(command);
		queueCommand.start();
		
		Thread.sleep(1000);
		
		verify(command, times(1)).execute();
	}
	
	@Test
	public void addingHotCommandTest() throws InterruptedException {
		final QueueCommand queueCommand = new QueueCommand();
		queueCommand.start();
		Thread.sleep(1000);
		final ICommand command = mock(ICommand.class);
		queueCommand.push(command);
		Thread.sleep(1000);
		verify(command, times(1)).execute();
	}
	
}
