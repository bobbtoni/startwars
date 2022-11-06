package ru.bobb.startwars.codegenerator;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

public class AdapterInterfaceParser {
	public Map.Entry<String, String> parse(Class clazz) {
		final StringBuilder javaCode = new StringBuilder();
		javaCode.append("package ru.bobb.startwars.generated;\n");
		javaCode.append("\n");
		// javaCode.append("import ").append(clazz.getSimpleName()).append(";/n");
		javaCode.append("import ru.bobb.startwars.*;\n");
		javaCode.append("import ru.bobb.startwars.ioc.*;\n");
		javaCode.append("\n");
		
		final String interfaceName = clazz.getSimpleName();
		final String adapterName = interfaceName + "Adapter";
		javaCode.append("public class ").append(adapterName).append(" implements ").append(interfaceName).append(" {\n");
		javaCode.append("private final UObject object;\n");
		javaCode.append("public ").append(adapterName).append("(UObject object) {\n");
		javaCode.append("this.object = object;\n");
		javaCode.append("}\n");
		
		final Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
		for (Method method : methods) {
			final String methodImpl;
			final String methodName = method.getName();
			if (methodName.startsWith("get")) {
				final String getProperty = "@Override public {2} get{1}() '{' return IoC.<{2}>resolve(\"{0}.{1}.Get\", object); '}'";
				methodImpl = MessageFormat.format(getProperty, interfaceName, methodName.replaceFirst("get", ""), method.getReturnType().getSimpleName());
			} else if (methodName.startsWith("set")) {
				final String setProperty = "@Override public void set{1}({2} newValue) '{' IoC.<ICommand>resolve(\"{0}.{1}.Set\", object, newValue).execute(); '}'";
				methodImpl = MessageFormat.format(setProperty, interfaceName, methodName.replaceFirst("set", ""), method.getParameters()[0].getType().getSimpleName());
			} else {
				Class returnType = method.getReturnType();
				Class[] parameters = method.getParameterTypes();
				
				final StringBuilder customMethodCode = new StringBuilder();
				customMethodCode.append("@Override public ");
				customMethodCode.append(returnType.getSimpleName()).append(" ");
				
				customMethodCode.append(method.getName());
				customMethodCode.append("(");
				int i = 0;
				for (Class parameterClass : parameters) {
					customMethodCode.append(parameterClass.getSimpleName()).append(" p").append(i++).append(",");
				}
				if (i > 0) {
					customMethodCode.deleteCharAt(customMethodCode.length()- 1);
				}
				customMethodCode.append(") {\n");
				
				if (!"void".equals(returnType.getSimpleName())) {
					customMethodCode.append("return IoC.<").append(returnType.getSimpleName()).append(">");
				} else {
					customMethodCode.append("IoC.<ICommand>");
				}
				customMethodCode.append("resolve(\"").append(interfaceName).append(".").append(methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
				customMethodCode.append("\"");
				customMethodCode.append(", new Object[] {");
				for (int j = 0; j < i; j++) {
					customMethodCode.append("p").append(j).append(",");
				}
				if (i > 0) {
					customMethodCode.deleteCharAt(customMethodCode.length()- 1);
				}
				customMethodCode.append("})");
				if ("void".equals(returnType.getSimpleName())) {
					customMethodCode.append(".execute()");
				}
				customMethodCode.append(";\n}");
				methodImpl = customMethodCode.toString();
			}
			javaCode.append(methodImpl).append("\n");
		}
		
		javaCode.append("}");
		
		return Map.entry("ru.bobb.startwars.generated." + adapterName, javaCode.toString());
		
	}
}
