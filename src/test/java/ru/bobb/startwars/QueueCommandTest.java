package ru.bobb.startwars;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueCommandTest {
	
	@Test
	public void startTest() {
		final QueueCommand queueCommand = new QueueCommand();
		assertTrue(queueCommand.start());
		assertFalse(queueCommand.start());
	}
	
	@Test
	public void stopTest() {
		final QueueCommand queueCommand = new QueueCommand();
		assertTrue(queueCommand.start());
		queueCommand.stop();
		assertTrue(queueCommand.start());
	}
}
