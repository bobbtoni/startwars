package ru.bobb.startwars.codegenerator.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import ru.bobb.startwars.ICommand;
import ru.bobb.startwars.UObject;
import ru.bobb.startwars.ioc.IoC;

public class AdapterGenerator {
	
	public static <T> T generateInstance(Class<T> interfaceClass, UObject object) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, new Handler(object));
	}
	
	@AllArgsConstructor
	private static class Handler implements InvocationHandler {
		private final UObject object;

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			final String interfaceName = proxy.getClass().getInterfaces()[0].getSimpleName();
			final String methodName = method.getName();
			if (methodName.startsWith("get")) {
				final String dependency = MessageFormat.format("{0}.{1}.Get", interfaceName, methodName.replaceFirst("get", ""));
				return IoC.resolve(dependency, object);
			} else if (methodName.startsWith("set")) {
				final String dependency = MessageFormat.format("{0}.{1}.Set", interfaceName, methodName.replaceFirst("set", ""));
				IoC.<ICommand>resolve(dependency, Stream.of(new Object[] { object }, args).flatMap(Stream::of).toArray()).execute();
			} else {
				final String dependency = MessageFormat.format("{0}.{1}", interfaceName, methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
				if (method.getReturnType() == null) {
					IoC.<ICommand>resolve(dependency, Stream.of(new Object[] { object }, args).flatMap(Stream::of).toArray()).execute();
				} else {
					return IoC.<ICommand>resolve(dependency, args);
				}
			}
			return null;
		}
		
	}
}
