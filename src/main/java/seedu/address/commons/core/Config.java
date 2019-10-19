package seedu.address.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private Path calendarUserPrefsFilePath = Paths.get("calendarpreferences.json");
    private Path financeUserPrefsFilePath = Paths.get("financepreferences.json");
    private Path capUserPrefsFilePath = Paths.get("capmodulelog.json");

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public Path getCalendarUserPrefsFilePath() {
        return calendarUserPrefsFilePath;
    }

    public Path getFinanceUserPrefsFilePath() {
        return financeUserPrefsFilePath;
    }

    public void setCalendarUserPrefsFilePath(Path calendarUserPrefsFilePath) {
        this.calendarUserPrefsFilePath = calendarUserPrefsFilePath;
    }

    public Path getCapUserPrefsFilePath() {
        return capUserPrefsFilePath;
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
                && Objects.equals(calendarUserPrefsFilePath, o.calendarUserPrefsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logLevel, calendarUserPrefsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nPreference file Location : " + calendarUserPrefsFilePath);
        return sb.toString();
    }

}
