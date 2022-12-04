package ru.bobb.startwars.codegenerator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.bobb.startwars.UObject;

public abstract class AdapterGenerator {
	private static Map<Class, Class> generatedClasses = new ConcurrentHashMap<>();
	
	private AdapterGenerator() {
		// utility class
	}
	
	public static <T> T generateInstance(Class<T> interfaceClass, UObject object) {
		try {
			if (!generatedClasses.containsKey(interfaceClass)) {
				Map.Entry<String, String> entry = new AdapterInterfaceParser().parse(interfaceClass);
				generatedClasses.put(interfaceClass, new ClassGenerator().generate(entry.getKey(), entry.getValue()));
			}
			final Class<? extends T> adapterClass = generatedClasses.get(interfaceClass);
			return adapterClass.getConstructor(UObject.class).newInstance(object);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
