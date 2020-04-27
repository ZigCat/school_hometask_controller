package com.github.zigcat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zigcat.jackson.UserDeserializer;
import com.github.zigcat.jackson.UserSerializer;
import com.github.zigcat.ormlite.models.User;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Main {
    public static void main(String[] args){
        Javalin app = Javalin.create();
        app.config = new JavalinConfig().enableDevLogging();
        app.start(34567);

        ObjectMapper om = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(User.class, new UserSerializer());
        sm.addDeserializer(User.class, new UserDeserializer());
        om.registerModule(sm);

        app.get("user/", ctx -> User.userControl.getAll(ctx, om));
        app.post("user/", ctx -> User.userControl.create(ctx, om));
    }
}
