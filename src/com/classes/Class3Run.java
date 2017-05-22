package com.classes;

import com.adnotation.ImRunning;
import com.adnotation.RunMe;

/**
 * Created by Patryk on 19.05.2017.
 */
@RunMe
public class Class3Run implements ClassRun {

    @ImRunning
    @Override
    public void run() {
        System.out.println("Class3Run its me!");
    }
    @Override
    public void notRun() {
        System.out.println("Class3Run its me!!");
    }
}
