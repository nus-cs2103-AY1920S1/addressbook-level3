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
    private Path testOutputPath = Paths.get("DukeAcademy/tests");
    private Path dataPath = Paths.get("DukeAcademy/data");
    private Path loadPath = Paths.get("DukeAcademy/newQuestions");
    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public Path getTestOutputPath() {
        return testOutputPath;
    }

    public Path getLoadPath() {
        return loadPath;
    }

    public void setTestOutputPath(Path testOutputPath) {
        this.testOutputPath = testOutputPath;
    }

    public Path getDataPath() {
        return dataPath;
    }

    public void setDataPath(Path dataPath) {
        this.dataPath = dataPath;
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
                && Objects.equals(testOutputPath, o.testOutputPath)
                && Objects.equals(dataPath, o.dataPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, testOutputPath, dataPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : ").append(logLevel);
        sb.append("Tests output path : ").append(testOutputPath);
        sb.append("Data path : ").append(dataPath);
        return sb.toString();
    }

}
