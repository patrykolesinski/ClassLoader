package com.classes;

import com.adnotation.ImRunning;
import com.adnotation.RunMe;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Patryk on 21.05.2017.
 */
public class AdnotationInvoker {
    public static void invokeAdnotationClasses(List<Class> classes) throws IOException {
        classes.stream()
                .filter(cl -> cl.isAnnotationPresent(RunMe.class))
                .forEach(cl -> {
                    Stream.of(cl.getDeclaredMethods())
                            .filter(method -> method.isAnnotationPresent(ImRunning.class))
                            .forEach(method -> {
                                try {
                                    method.invoke(cl.newInstance(), null);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
                            });
                });
    }
}
