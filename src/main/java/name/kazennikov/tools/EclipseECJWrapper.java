package name.kazennikov.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

public class EclipseECJWrapper {
	public static class MemorySource extends SimpleJavaFileObject {
		private String src;

		public MemorySource(String name, String src) {
			super(URI.create("file:///" + name + ".java"), Kind.SOURCE);
			this.src = src;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return src;
		}

		@Override
		public OutputStream openOutputStream() {
			throw new IllegalStateException();
		}

		@Override
		public InputStream openInputStream() {
			return new ByteArrayInputStream(src.getBytes());
		}
	}

	public static class SpecialJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
		private SpecialClassLoader xcl;

		public SpecialJavaFileManager(StandardJavaFileManager sjfm, SpecialClassLoader xcl) {
			super(sjfm);
			this.xcl = xcl;
		}

		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String name,
				JavaFileObject.Kind kind, FileObject sibling) throws IOException {
			MemoryByteCode mbc = new MemoryByteCode(name);
			xcl.addClass(name, mbc);
			return mbc;
		}

		@Override
		public ClassLoader getClassLoader(Location location) {
			return xcl;
		}
	}

	public static class MemoryByteCode extends SimpleJavaFileObject {
		private ByteArrayOutputStream baos;

		public MemoryByteCode(String name) {
			super(URI.create("byte:///" + name + ".class"), Kind.CLASS);
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			throw new IllegalStateException();
		}

		@Override
		public OutputStream openOutputStream() {
			baos = new ByteArrayOutputStream();
			return baos;
		}

		@Override
		public InputStream openInputStream() {
			throw new IllegalStateException();
		}

		public byte[] getBytes() {
			return baos.toByteArray();
		}
	}

	public static class SpecialClassLoader extends ClassLoader {
		private Map<String, MemoryByteCode> m = new HashMap<String, MemoryByteCode>();

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			MemoryByteCode mbc = m.get(name);
			if (mbc == null) {
				mbc = m.get(name.replace(".", "/"));
				if (mbc == null) {
					return super.findClass(name);
				}
			}
			return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);
		}

		public void addClass(String name, MemoryByteCode mbc) {
			m.put(name, mbc);
		}
	}

	private static JavaCompiler javac = new EclipseCompiler();
	private static SpecialClassLoader cl = new SpecialClassLoader();

	public synchronized static Class<?> compileClass(String code, String className, Writer compilerOut) throws ClassNotFoundException {
		StandardJavaFileManager sjfm = javac.getStandardFileManager(null, null, null);

		SpecialJavaFileManager fileManager = new SpecialJavaFileManager(sjfm, cl);
		List<String> options = Collections.emptyList();

		List<MemorySource> compilationUnits = Arrays.asList(new MemorySource(className, code));
		DiagnosticListener<JavaFileObject> dianosticListener = null;
		Iterable<String> classes = null;
		JavaCompiler.CompilationTask compile = javac.getTask(compilerOut, fileManager,
				dianosticListener, options, classes, compilationUnits);

		return compile.call()? cl.findClass(className) : null;

	}
}
