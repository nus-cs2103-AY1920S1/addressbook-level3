package com.dukeacademy.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    /**
     * The constant DEFAULT_CONFIG_FILE.
     */
    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private Mode mode = Mode.PRODUCTION;
    private Path userPrefsFilePath = Paths.get("preferences.json");

    /**
     * Gets log level.
     *
     * @return the log level
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets log level.
     *
     * @param logLevel the log level
     */
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Gets user prefs file path.
     *
     * @return the user prefs file path
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Sets mode.
     *
     * @param mode the mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets user prefs file path.
     *
     * @return the user prefs file path
     */
    public Path getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    /**
     * Sets user prefs file path.
     *
     * @param userPrefsFilePath the user prefs file path
     */
    public void setUserPrefsFilePath(Path userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, userPrefsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nApp mode: + " + mode.toString());
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        return sb.toString();
    }

}
