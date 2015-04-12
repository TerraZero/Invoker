package TZ.sys.invoker.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author Terra
 * @created 13.02.2015
 * 
 * @file BootLoader.java
 * @project G7C
 * @identifier TZ.Reflect.Boot
 *
 */
public class SysLoader {
	
	private static SysLoader sysloader;
	private static URLClassLoader loader;
	private static Method addMethod;
	private static URL defaultURL;
	
	public static SysLoader sysloader() {
		if (SysLoader.sysloader == null) {
			SysLoader.sysloader = new SysLoader();
		}
		return SysLoader.sysloader;
	}
	
	public static URLClassLoader loader() {
		if (SysLoader.loader == null) {
			SysLoader.loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			SysLoader.defaultURL = SysLoader.loader.getURLs()[0];
		}
		return SysLoader.loader;
	}
	
	public static void addLoaderSource(File file) {
		try {
			SysLoader.addLoaderSource(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addLoaderSource(URL url) {
		if (SysLoader.addMethod == null) {
			try {
				SysLoader.addMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			SysLoader.addMethod.setAccessible(true);
		}
		try {
			SysLoader.addMethod.invoke(SysLoader.loader(), new Object[] {url});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static URL defaultURL() {
		if (SysLoader.defaultURL == null) {
			SysLoader.loader();
		}
		return SysLoader.defaultURL;
	}
	
	public static String defaultPath() {
		return SysLoader.defaultURL().getPath();
	}
	
	protected List<SysFile> sysfiles;
	
	private SysLoader() {
		
	}
	
	public List<SysFile> load() {
		return this.load(false);
	}
	
	public List<SysFile> load(boolean force) {
		if (force || this.sysfiles == null) {
			this.init();
		}
		return this.sysfiles;
	}
	
	public void init() {
		this.sysfiles = new ArrayList<SysFile>(1024);
		try {
			for (URL url : SysLoader.loader().getURLs()) {
				String file = url.getFile();
				
				if (file.endsWith(".jar")) {
					ZipInputStream zip = new ZipInputStream(url.openStream());
					this.loadZip(this.sysfiles, zip);
					zip.close();
				} else {
					this.loadFile(this.sysfiles, file, "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadZip(List<SysFile> boots, ZipInputStream zip) throws IOException {
		ZipEntry entry = null;
		
		while ((entry = zip.getNextEntry()) != null) {
			if (entry.getName().endsWith(".class")) {
				boots.add(new SysFile(entry));
			}
		}
	}
	
	public void loadFile(List<SysFile> boots, String path, String internpath) {
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				this.loadFile(boots, path + "/" + f.getName(), internpath + "/" + f.getName());
			} else if (f.isFile() && f.getName().endsWith(".class")) {
				boots.add(new SysFile(f, (internpath.length() == 0 ? "" : internpath.substring(1))));
			}
		}
	}
	
}
