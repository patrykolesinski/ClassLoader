package com.classes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassFinder {

    public static Class[] getClasses(String packageName)

            throws ClassNotFoundException, IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');

        Enumeration resources = classLoader.getResources(path);

        List dirs = new ArrayList();

        while (resources.hasMoreElements()) {

            URL resource = (URL) resources.nextElement();

            dirs.add(new File(resource.getFile()));

        }

        ArrayList classes = new ArrayList();

        for (Object directory : dirs) {

            classes.addAll(findClasses((File)directory, packageName));

        }

        return (Class[]) classes.toArray(new Class[classes.size()]);

    }

    /**

     * Recursive method used to find all classes in a given directory and subdirs.

     *

     * @param directory   The base directory

     * @param packageName The package name for classes found inside the base directory

     * @return The classes

     * @throws ClassNotFoundException

     */

    public static List findClasses(File directory, String packageName) throws ClassNotFoundException {

        List classes = new ArrayList();

        if (!directory.exists()) {

            return classes;

        }

        File[] files = directory.listFiles();

        for (File file : files) {

            if (file.isDirectory()) {

                //assert !file.getName().contains(".");

                classes.addAll(findClasses(file, packageName + "." + file.getName()));

            } else if (file.getName().endsWith(".class")) {

                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));

            }

        }

        return classes;

    }

}
