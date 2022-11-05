package ru.bobb.startwars.ioc;

import ru.bobb.startwars.ICommand;

public abstract class IoC {
	
	private static final String GLOBAL = "GLOBAL";
	
	private static ThreadLocal<Scope> currentScope = new ThreadLocal<>();
	private static final ScopeContainer scopeContainer = new ScopeContainer();
	
	static {
		
		scopeContainer.scope(GLOBAL).dependencies().put("IoC.Register", new ObjectFactory() {
			
			@Override
			public Object resolve(Object... args) {
				final String nameDependency = (String) args[0];
				final ObjectFactory objectFactory = (ObjectFactory) args[1];
				return (ICommand) () -> {
					final Scope scope = currentScope.get();
					if (scope != null) {
						scope.dependencies().put(nameDependency, objectFactory);
					} else {
						scopeContainer.scope(GLOBAL).dependencies().put(nameDependency, objectFactory);
					}
				};
			}
		});
		
		IoC.<ICommand>resolve("IoC.Register", "Scopes.Current", (ObjectFactory) args -> {
			final String scopeName = (String) args[0];
			return (ICommand) () -> {
				final Scope scope = scopeContainer.scope(scopeName);
				currentScope.set(scope);
			};
		}).execute();
	}
	
	
	private IoC() {
		// factory
	}
	
	public static <T> T resolve(String key, Object... params) {
		final Scope scope = currentScope.get();
		ObjectFactory factory;
		if (scope != null && scope.dependencies().containsKey(key)) {
			factory = scope.dependencies().get(key);
		} else {
			factory = scopeContainer.scope(GLOBAL).dependencies().get(key);
		}
		if (factory == null) {
			throw new MissingDependencyException(key);
		}
		return (T) factory.resolve(params);
	}
	
}
