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
		
		verify(firstAction, times(0)).execute(any(TestCommand.class), any(IllegalStateException.class));
		verify(secondAction, times(1)).execute(any(TestCommand.class), any(IllegalStateException.class));
	}
	
	@Test
	public void logCommandTest() {
		final ExceptionHandler exceptionHandler = new ExceptionHandler();
		final LogCommand logCommand = mock(LogCommand.class);
		final ICommand testCommand = mock(ICommand.class);
		when(testCommand.getClass()).thenCallRealMethod();
		doThrow(IllegalArgumentException.class).when(testCommand).execute();
		exceptionHandler.setup(testCommand.getClass(), IllegalArgumentException.class, (c, e) -> logCommand.execute());
		try {
			testCommand.execute();
		} catch (Exception e) {
			exceptionHandler.handle(testCommand, e);
		}
		verify(logCommand, times(1)).execute();
	}
 	
	public static class TestCommand implements ICommand {

		@Override
		public void execute() {
			// noting to do
		}
		
	}
}
