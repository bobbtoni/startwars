package ru.bobb.startwars.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ScopeContainer {
	private Map<String, Scope> scopes = new ConcurrentHashMap<>();
	
	public Scope scope(String nameScope) {
		return scopes.computeIfAbsent(nameScope, name -> new Scope());
	}
}
