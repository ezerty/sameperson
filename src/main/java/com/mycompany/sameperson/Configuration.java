package com.mycompany.sameperson;

import java.io.IOException;
import java.util.Properties;

public enum Configuration {
    INSTANCE;

    private final Properties properties = new Properties();

    private Configuration() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDateDelimiters() {
        return properties.getProperty("date.delimiters");
    }

    public String getNameDelimiters() {
        return properties.getProperty("name.parts.delimiters");
    }
}
