package ru.bobb.startwars.ioc;

import ru.bobb.startwars.ICommand;
import ru.bobb.startwars.UObject;
import ru.bobb.startwars.Vector;
import ru.bobb.startwars.codegenerator.AdapterGenerator;

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
		
		IoC.<ICommand>resolve("IoC.Register", "Adapter", (ObjectFactory) args -> {
			final Class interfaceClass = (Class) args[0];
			final UObject object = (UObject) args[1];
			return AdapterGenerator.generateInstance(interfaceClass, object);
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "ITurnable.Direction.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Integer) object.getProperty("direction");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "ITurnable.AngularVelocity.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Integer) object.getProperty("angularVelocity");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "ITurnable.DirectionsNumber.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Integer) object.getProperty("directionsNumber");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "ITurnable.Direction.Set", (ObjectFactory) args -> {
			return (ICommand) () -> {
				final UObject object = (UObject) args[0];
				final Integer newValue = (Integer) args[1];
				object.setProperty("direction", newValue);
			};
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IFuelable.FuelReserve.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Integer) object.getProperty("fuelReserve");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IFuelable.FuelSpent.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Integer) object.getProperty("fuelSpent");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IFuelable.FuelReserve.Set", (ObjectFactory) args -> {
			return (ICommand) () -> {
				final UObject object = (UObject) args[0];
				final Integer newValue = (Integer) args[1];
				object.setProperty("fuelReserve", newValue);
			};
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IMovable.Position.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Vector) object.getProperty("position");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IMovable.Position.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Vector) object.getProperty("position");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IMovable.Velocity.Get", (ObjectFactory) args -> {
			final UObject object = (UObject) args[0];
			return (Vector) object.getProperty("velocity");
		}).execute();
		
		IoC.<ICommand>resolve("IoC.Register", "IMovable.Position.Set", (ObjectFactory) args -> {
			return (ICommand) () -> {
				final UObject object = (UObject) args[0];
				final Vector newValue = (Vector) args[1];
				object.setProperty("position", newValue);
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
