package ru.bobb.startwars.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Scope {
	private final Map<String, ObjectFactory> dependencies = new ConcurrentHashMap<>();
	
	Map<String, ObjectFactory> dependencies() {
		return this.dependencies;
	}
}
