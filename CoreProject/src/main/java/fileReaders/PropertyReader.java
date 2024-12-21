package fileReaders;


import logging.Log;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class PropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static Set<String> pathSet = new TreeSet<>();

    private static boolean propertySet = false;

    public static HashMap<String, String> setProperty(String propertyPath) {
        HashMap<String, String> propertyMap = new HashMap<>();
        Properties properties = new Properties();
        String jenkins_build = System.getenv("BUILD_URL");
        if (!pathSet.contains(propertyPath)) {
            pathSet.add(propertyPath);
            if (jenkins_build == null) {
                logger.info("Local Execution, Need to read from local property " + propertyPath);
                try {
                    File propFile = FileUtils.getFile(propertyPath);
                    if (propFile.exists()) {
                        InputStream inputStream = new FileInputStream(propFile);
                        properties.load(inputStream);
                        for (String key : properties.stringPropertyNames()) {
                            propertyMap.put(key, properties.getProperty(key));
                            if (System.getProperty(key) == null || System.getProperty(key).isEmpty()) {
                                System.setProperty(key, properties.getProperty(key));
                            }
                        }
                    } else
                        Assert.fail("Please set the path of property File correctly");
                } catch (Exception e) {
                    Log.warn("Exception happened during setting system properties");
                    e.printStackTrace();
                }
            } else {
                logger.info("CI Execution, No need to read from local property");
            }
        } else
            logger.info("Property already set");
        return propertyMap;
    }

    public static void setProperty(HashMap<String, String> propertyMap) {
        if (!propertySet) {
            for (String key : propertyMap.keySet()) {
                if (System.getProperty(key) == null || System.getProperty(key).isEmpty()) {
                    System.setProperty(key, propertyMap.get(key));
                }
            }
            propertySet = true;
        }


    }


    public static HashMap<String, String> setMandatoryProperty(String propertyPath) {
        HashMap<String, String> propertyMap = new HashMap<>();
        Properties properties = new Properties();
        try {
            File propFile = FileUtils.getFile(propertyPath);
            if (propFile.exists()) {
                InputStream inputStream = new FileInputStream(propFile);
                properties.load(inputStream);
                for (String key : properties.stringPropertyNames()) {
                    propertyMap.put(key, properties.getProperty(key));
                    if (System.getProperty(key) == null || System.getProperty(key).isEmpty()) {
                        System.setProperty(key, properties.getProperty(key));
                    }
                }
            } else
                Assert.fail("Please set the path of property File correctly");
        } catch (Exception e) {
            Log.warn("Exception happened during setting system properties");
            e.printStackTrace();
        }
        return propertyMap;
    }
}
