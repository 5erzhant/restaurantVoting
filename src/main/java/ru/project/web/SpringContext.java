package ru.project.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
            "spring/spring-app.xml", "spring/spring-db.xml");

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
