package thinlet.drafts;

import java.lang.reflect.*;
import thinlet.*;

/**
 *
 */
public class ClassExplorer {
	
	/**
	 *
	 */
	public void initClass(Thinlet thinlet, Object combobox, Object tree) {
		Class thinletclass = thinlet.getClass();
		int n = 0;
		for (Class c = thinletclass; c != null; c = c.getSuperclass()) {
			Object choice = thinlet.create("choice");
			thinlet.setString(choice, "text", c.getName());
			thinlet.add(combobox, choice, 0);
			n++;
		}
		thinlet.setInteger(combobox, "selected", n - 1);
		
		loadClass(thinlet, thinletclass.getName(), tree);
	}
	
	/**
	 *
	 */
	public void loadClass(Thinlet thinlet, String classname, Object tree) {
		thinlet.removeAll(tree);
		try {
			Class aclass = Class.forName(classname);
			Object classnode = thinlet.create("node");
			thinlet.setString(classnode, "text", aclass.getName());
			try {
				Method[] methods = aclass.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					Object methodnode = thinlet.create("node");
					thinlet.setString(methodnode, "text",
						Modifier.toString(methods[i].getModifiers()) + " " +
							getNameOf(methods[i].getReturnType()) + " " + methods[i].getName());
					thinlet.add(classnode, methodnode);
					
					Class[] parameters = methods[i].getParameterTypes();
					if (parameters.length > 0) {
						for (int j = 0; j < parameters.length; j++) {
							Object parameternode = thinlet.create("node");
							thinlet.setString(parameternode, "text", getNameOf(parameters[j]));
							thinlet.add(methodnode, parameternode);
						}
						thinlet.setBoolean(methodnode, "expanded", false);
					}
				}
			} catch (SecurityException se) {
				Object exceptionnode = thinlet.create("node");
				thinlet.setString(exceptionnode, "text", se.getMessage());
				thinlet.add(classnode, exceptionnode);
			} 
			thinlet.add(tree, classnode);
		} catch (ClassNotFoundException cnfe) { /*never*/}
	}
	
	private static final String getNameOf(Class aclass) {
		return (aclass.isArray()) ? (getNameOf(aclass.getComponentType()) + "[]") : aclass.getName();
	}
}