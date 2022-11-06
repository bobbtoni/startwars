package ru.bobb.startwars.codegenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class ClassGenerator {
	
	public <T> Class<T> generate(String name, String body) throws ClassNotFoundException, IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final SimpleJavaFileObject simpleJavaFileObject
	      = new SimpleJavaFileObject(URI.create(name.replace(".", "/") + ".java"), Kind.SOURCE) {

	        @Override
	        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
	            return body;
	        }

	       @Override
	       public OutputStream openOutputStream() throws IOException {
	           return byteArrayOutputStream;
	       }
		};
		
		try (final JavaFileManager javaFileManager = new ForwardingJavaFileManager( ToolProvider.getSystemJavaCompiler().
		    getStandardFileManager(null, null, null)) {

		        @Override
		        public JavaFileObject getJavaFileForOutput(
		            Location location,String className,
		            JavaFileObject.Kind kind,
		            FileObject sibling) {
		           return simpleJavaFileObject;
		        }
		}) {
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
			ToolProvider.getSystemJavaCompiler().getTask(null, javaFileManager, diagnostics, null, null, Arrays.asList(simpleJavaFileObject)).call();
			for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
			      System.out.format("Error on line %d in %s%n",
			              diagnostic.getLineNumber(),
			              diagnostic.getSource());
		     }	
		}
		
		
		final byte[] byteCode = byteArrayOutputStream.toByteArray();
		
		final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		return (Class<T>) new InMemoryClassLoader(classLoader, byteCode).findClass(name);
	}
	
	class InMemoryClassLoader extends ClassLoader {
		final byte[] byteCode;
		
		public InMemoryClassLoader(ClassLoader classLoader, final byte[] byteCode) {
			super(classLoader);
			this.byteCode = byteCode;
		}
		
		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			return defineClass(name, byteCode, 0, byteCode.length);
		}
	}
	
}
