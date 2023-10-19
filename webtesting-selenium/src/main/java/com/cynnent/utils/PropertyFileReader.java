package com.cynnent.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
    private Properties properties;

    public PropertyFileReader(String filePath) {
        properties = loadPropertiesFromFile(filePath);
    }

    private Properties loadPropertiesFromFile(String filePath) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

