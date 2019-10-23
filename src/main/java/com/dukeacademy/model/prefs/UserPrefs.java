package com.dukeacademy.model.prefs;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents User's preferences.
 */
public class UserPrefs extends ReadOnlyUserPrefs {
    private final Path appDirectoryPath;

    @JsonCreator
    public UserPrefs(@JsonProperty("appDirectoryPath") String appDirectoryPath) {
        requireAllNonNull(appDirectoryPath);
        this.appDirectoryPath = Paths.get(appDirectoryPath);
    }

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs(Path appDirectoryPath) {
        requireAllNonNull(appDirectoryPath);
        this.appDirectoryPath = appDirectoryPath;
    }

    @Override
    public Path getAppDirectoryPath() {
        return this.appDirectoryPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;
        return o.appDirectoryPath.equals(this.appDirectoryPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App directory path : " + appDirectoryPath);
        return sb.toString();
    }

}
