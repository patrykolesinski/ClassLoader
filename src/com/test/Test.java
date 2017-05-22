package com.test;


import com.classes.AdnotationInvoker;
import com.classes.AllClassFinder;

import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        AdnotationInvoker.invokeAdnotationClasses(AllClassFinder.findClasses());
    }
}
