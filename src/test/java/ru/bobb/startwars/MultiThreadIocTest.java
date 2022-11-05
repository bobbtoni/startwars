package ru.bobb.startwars;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.junit.Test;

import ch.qos.logback.core.util.ExecutorServiceUtil;
import ru.bobb.startwars.ioc.IoC;
import ru.bobb.startwars.ioc.ObjectFactory;

public class MultiThreadIocTest {
	
	@Test
	public void switchScopesTest() throws InterruptedException, ExecutionException {
		IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new MoveAdapter((UObject) args[0])).execute();
		
		IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
		IoC.<ICommand>resolve("IoC.Register", "Test", (ObjectFactory) (args) -> new TurnableAdapter((UObject) args[0])).execute();
		
		final ExecutorService executorService = ExecutorServiceUtil.newExecutorService();
		final Future<ICommand> future1 = executorService.submit(() -> {
			IoC.<ICommand>resolve("Scopes.Current", "scope1").execute();
			return IoC.resolve("Test", new UObject());
		});
		final Future<ICommand> future2 = executorService.submit(() -> {
			IoC.<ICommand>resolve("Scopes.Current", "scope2").execute();
			return IoC.resolve("Test", new UObject());
		});
		
		assertEquals(MoveAdapter.class, future1.get().getClass());
		assertEquals(TurnableAdapter.class, future2.get().getClass());
	}
}
