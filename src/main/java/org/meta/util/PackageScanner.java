package org.meta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author mark
 */
public class PackageScanner {

	public static Set<Class<?>> getClasses(String packageName,boolean recursive) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return getClasses(loader, packageName,recursive);
	}

	private static Set<Class<?>> getClasses(ClassLoader loader, String packageName,boolean recursive) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources;
		try {
			resources = loader.getResources(path);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		if (resources != null) {
			while (resources.hasMoreElements()) {
				String filePath = resources.nextElement().getFile();
				// WINDOWS HACK
				if(filePath.indexOf("%20") > 0)
					filePath = filePath.replaceAll("%20", " ");
				if (filePath != null) {
					if ((filePath.indexOf("!") > 0) & (filePath.indexOf(".jar") > 0)) {
						String jarPath = filePath.substring(0, filePath.indexOf("!")).substring(filePath.indexOf(":") + 1);
					// WINDOWS HACK
						if (jarPath.indexOf(":") >= 0)
							jarPath = jarPath.substring(1);
						classes.addAll(getFromJARFile(jarPath, path,recursive));
					} else {
						getFromDirectory(classes,new File(filePath), packageName,recursive);
					}
				}
			}
		}
		return classes;
	}

	private static void getFromDirectory(Set<Class<?>> set,File directory, String packageName,boolean recursive) {
		if (directory.exists()) {
			for (File file:directory.listFiles()) {
				if(file.isFile()) {
					String fileName=file.getName();
					if (fileName.endsWith(".class")) {
						String name = packageName + '.' + Utils.stripExtension(fileName);
						Class<?> clazz;
						try {
							clazz = Class.forName(name);
						} catch (ClassNotFoundException ex) {
							throw new RuntimeException(ex);
						}
						set.add(clazz);
					}
				}
				if(file.isDirectory() && recursive) {
					String newPackName = packageName + '.' + file.getName();
					getFromDirectory(set, file, newPackName, recursive);
				}
			}
		} else {
			throw new RuntimeException("no directory");
		}
	}

	private static Set<Class<?>> getFromJARFile(String jar, String packageName,boolean recursive) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		JarInputStream jarFile;
		try {
			jarFile = new JarInputStream(new FileInputStream(jar));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		JarEntry jarEntry;
		do {
			try {
				jarEntry = jarFile.getNextJarEntry();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			if (jarEntry != null) {
				String className = jarEntry.getName();
				if (className.endsWith(".class")) {
					className = Utils.stripExtension(className);
					if (className.startsWith(packageName)) {
						try {
							classes.add(Class.forName(className.replace('/', '.')));
						} catch (ClassNotFoundException ex) {
							throw new RuntimeException(ex);
						}
					}
				}
			}
		} while (jarEntry != null);
		return classes;
	}
}