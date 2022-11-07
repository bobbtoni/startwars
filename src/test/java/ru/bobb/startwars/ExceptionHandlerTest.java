package ru.bobb.startwars;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ru.bobb.startwars.ExceptionHandler.Action;

public class ExceptionHandlerTest {
	
	@Test
	public void oneCommandAndOneExceptionIsOneAction() {
		final ExceptionHandler exceptionHandler = new ExceptionHandler();
		
		final TestCommand command = new TestCommand();
		
		final Action<TestCommand, IllegalStateException> firstAction = mock(Action.class);
		final Action<TestCommand, IllegalStateException> secondAction = mock(Action.class);
		
		exceptionHandler.setup(TestCommand.class, IllegalStateException.class, firstAction);
		exceptionHandler.setup(TestCommand.class, IllegalStateException.class, secondAction);
		
		exceptionHandler.handle(command, new IllegalStateException());
		
		verify(firstAction, times(0)).generateCommand(any(TestCommand.class), any(IllegalStateException.class));
		verify(secondAction, times(1)).generateCommand(any(TestCommand.class), any(IllegalStateException.class));
	}
	
	@Test
	public void logCommandTest() throws InterruptedException {
		final LogCommand logCommand = mock(LogCommand.class);
		final ICommand testCommand = mock(ICommand.class);
		doThrow(IllegalArgumentException.class).when(testCommand).execute();
		
		QueueCommand queue = new QueueCommand();
		queue.push(testCommand);
		queue.getExceptionHandler().setup(testCommand.getClass(), IllegalArgumentException.class, (c, e) -> logCommand);
		queue.start();
		Thread.sleep(1000L);
		verify(logCommand, times(1)).execute();
	}
	
	@Test
	public void retryExecuteCommandTest() {
		final ICommand testCommand = mock(ICommand.class);
		doThrow(IllegalArgumentException.class).when(testCommand).execute();
		
		try {
			QueueCommand queue = new QueueCommand();
			queue.push(testCommand);
			queue.getExceptionHandler().setup(testCommand.getClass(), IllegalArgumentException.class, (c, e) -> new FirstRetryCommand(c));
			queue.start();
			Thread.sleep(1000L);
		} catch (Exception e) {
			// ignore
		}
		
		verify(testCommand, times(2)).execute();
	}
	
	@Test
	public void twoRetryAndWriteToLogTest() throws InterruptedException {
		final ICommand testCommand = mock(ICommand.class);
		final LogCommand logCommand = mock(LogCommand.class);
		doThrow(IllegalArgumentException.class).when(testCommand).execute();
		

		QueueCommand queue = new QueueCommand();
		queue.push(testCommand);
		queue.getExceptionHandler().setup(testCommand.getClass(), IllegalArgumentException.class, (c, e) -> new FirstRetryCommand(c));
		queue.getExceptionHandler().setup(FirstRetryCommand.class, IllegalArgumentException.class, (c, e) -> new SecondRetryCommand(c));
		queue.getExceptionHandler().setup(SecondRetryCommand.class, IllegalArgumentException.class, (c, e) -> logCommand);
		queue.start();
		Thread.sleep(1000L);
		
		verify(logCommand, times(1)).execute();
		verify(testCommand, times(3)).execute();
		
	}
	
	@Test
	public void retryAndWriteToLogTest() throws InterruptedException {
		final ICommand testCommand = mock(ICommand.class);
		final LogCommand logCommand = mock(LogCommand.class);
		doThrow(IllegalArgumentException.class).when(testCommand).execute();
		

		QueueCommand queue = new QueueCommand();
		queue.push(testCommand);
		queue.getExceptionHandler().setup(testCommand.getClass(), IllegalArgumentException.class, (c, e) -> new FirstRetryCommand(c));
		queue.getExceptionHandler().setup(FirstRetryCommand.class, IllegalArgumentException.class, (c, e) -> logCommand);
		queue.start();
		Thread.sleep(1000L);
		
		verify(logCommand, times(1)).execute();
		verify(testCommand, times(2)).execute();
		
	}
 	
	public static class TestCommand implements ICommand {

		@Override
		public void execute() {
			// noting to do
		}
		
	}
}
