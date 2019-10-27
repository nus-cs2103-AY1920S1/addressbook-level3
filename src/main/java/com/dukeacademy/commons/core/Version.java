package com.dukeacademy.commons.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a version with major, minor and patch number
 */
public class Version implements Comparable<Version> {

    /**
     * The constant VERSION_REGEX.
     */
    private static final String VERSION_REGEX = "V(\\d+)\\.(\\d+)\\.(\\d+)(ea)?";

    private static final String EXCEPTION_STRING_NOT_VERSION = "String is not a valid Version. %s";

    private static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);

    private final int major;
    private final int minor;
    private final int patch;
    private final boolean isEarlyAccess;

    /**
     * Instantiates a new Version.
     *
     * @param major         the major
     * @param minor         the minor
     * @param patch         the patch
     * @param isEarlyAccess the is early access
     */
    public Version(int major, int minor, int patch, boolean isEarlyAccess) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.isEarlyAccess = isEarlyAccess;
    }

    /**
     * Gets major.
     *
     * @return the major
     */
    public int getMajor() {
        return major;
    }

    /**
     * Gets minor.
     *
     * @return the minor
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Gets patch.
     *
     * @return the patch
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Is early access boolean.
     *
     * @return the boolean
     */
    public boolean isEarlyAccess() {
        return isEarlyAccess;
    }

    /**
     * Parses a version number string in the format V1.2.3.
     *
     * @param versionString version number string
     * @return a Version object
     * @throws IllegalArgumentException the illegal argument exception
     */
    @JsonCreator
    public static Version fromString(String versionString) throws IllegalArgumentException {
        Matcher versionMatcher = VERSION_PATTERN.matcher(versionString);

        if (!versionMatcher.find()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_STRING_NOT_VERSION, versionString));
        }

        return new Version(Integer.parseInt(versionMatcher.group(1)),
                Integer.parseInt(versionMatcher.group(2)),
                Integer.parseInt(versionMatcher.group(3)),
                versionMatcher.group(4) == null ? false : true);
    }

    @JsonValue
    public String toString() {
        return String.format("V%d.%d.%d%s", major, minor, patch, isEarlyAccess ? "ea" : "");
    }

    @Override
    public int compareTo(Version other) {
        if (major != other.major) {
            return major - other.major;
        }
        if (minor != other.minor) {
            return minor - other.minor;
        }
        if (patch != other.patch) {
            return patch - other.patch;
        }
        if (isEarlyAccess == other.isEarlyAccess()) {
            return 0;
        }
        if (isEarlyAccess) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        final Version other = (Version) obj;

        return compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        String hash = String.format("%03d%03d%03d", major, minor, patch);
        if (!isEarlyAccess) {
            hash = "1" + hash;
        }
        return Integer.parseInt(hash);
    }
}
