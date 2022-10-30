package ru.bobb.startwars;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class UObject {
	
	private final Map<String, Object> properties = new ConcurrentHashMap<>();

	public Object getProperty(String nameProperty) {
		return properties.compute(nameProperty, (k, v) -> {
			if (v == null) {
				throw new IllegalArgumentException(String.format("Object doesn't have property %s", nameProperty));
			} else {
				return v;
			}
		});
	}

	public void setProperty(String nameProperty, Object value) {
		properties.put(nameProperty, value);
	}
	
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		for (Entry<String, Object> entry : properties.entrySet()) {
			stringBuilder.append(entry.getKey() + " = " + entry.getValue() + "\n");
		}
		return stringBuilder.toString();
	}

}
