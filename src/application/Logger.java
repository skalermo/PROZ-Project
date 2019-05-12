package application;

import org.apache.log4j.LogManager;

public class Logger {
    public static void info(String message) {
        LogManager.getRootLogger().info(message);
    }

    public static void debug(String message) {
        LogManager.getRootLogger().debug(message);
    }

    public static void error(String message) {
        LogManager.getRootLogger().error(message);
    }
}
