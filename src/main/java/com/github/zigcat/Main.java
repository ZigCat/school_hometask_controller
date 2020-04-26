package com.github.zigcat;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Main {
    public static void main(String[] args){
        Javalin app = Javalin.create();
        app.config = new JavalinConfig().enableDevLogging();
        app.start(34567);

    }
}
