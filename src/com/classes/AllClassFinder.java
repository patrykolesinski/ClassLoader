package com.classes;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Patryk on 21.05.2017.
 */
public class AllClassFinder {

    public static List<Class> findClasses() {

        List<Class> classes = new ArrayList<>();
        URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        List<File> files = new ArrayList<>();
        Stream.of(urlClassLoader.getURLs())
                .forEach(url -> files.add(new File(url.getFile())));
        List<File> directories = files.stream()
                .filter(file -> file.isDirectory())
                .collect(Collectors.toList());
        directories.forEach(directory -> classes.addAll(searchClasses(directory, directory.getPath())));

        return classes;
    }

    private static String extractClassName(String defaultPath, String filePath) {
        filePath = filePath.replace(defaultPath, "");
        filePath = filePath.replace("\\", ".");
        return filePath.substring(1, filePath.length() - 6);
    }

    private static List<Class> searchClasses(File file, String defaultPath) {
        List classes = new ArrayList();
        Stream.of(file.listFiles())
                .forEach(searchingFile -> {
                    if (searchingFile.isDirectory()) {
                        classes.addAll(searchClasses(searchingFile, defaultPath));
                    } else if (searchingFile.getName().endsWith(".class")) {
                        try {
                            classes.add(Class.forName(extractClassName(defaultPath, searchingFile.getPath())));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return classes;
    }
}

