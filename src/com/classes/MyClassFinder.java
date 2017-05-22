package com.classes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

public class MyClassFinder {
    public static List<Class> findClasses(String path) throws IOException {
        List<Class> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration resources = classLoader.getResources(path.replace(".","/"));
        List<File> files = new ArrayList<>();
        while(resources.hasMoreElements()){
            URL url = (URL)resources.nextElement();
            files.add(new File(url.getFile()));
        }
        files.stream()
                .filter(file -> file.exists())
                .forEach(file -> classes.addAll(searchClasses(file, path)));
        return classes;
    }

    private static List<Class> searchClasses(File file, String path){
        List classes = new ArrayList();
        Stream.of(file.listFiles())
                .forEach(searchingFile -> {
                    if(searchingFile.isDirectory()){
                        classes.addAll(searchClasses(searchingFile,path + "." + searchingFile.getName()));
                    }
                    else if(searchingFile.getName().endsWith(".class")){
                        try {
                            classes.add(Class.forName(path + "." + searchingFile.getName().substring(0, searchingFile.getName().length() - 6)));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return classes;
    }
}
