package io.xpire.commons.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a version with major, minor and patch number
 */
public class Version implements Comparable<Version> {

    public static final String VERSION_REGEX = "V(\\d+)\\.(\\d+)\\.(\\d+)(ea)?";

    private static final String EXCEPTION_STRING_NOT_VERSION = "String is not a valid Version. %s";

    private static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);

    private final int major;
    private final int minor;
    private final int patch;
    private final boolean isEarlyAccess;

    public Version(int major, int minor, int patch, boolean isEarlyAccess) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.isEarlyAccess = isEarlyAccess;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getPatch() {
        return this.patch;
    }

    public boolean isEarlyAccess() {
        return this.isEarlyAccess;
    }

    /**
     * Parses a version number string in the format V1.2.3.
     * @param versionString version number string
     * @return a Version object
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
        return String.format("V%d.%d.%d%s", this.major, this.minor, this.patch, this.isEarlyAccess ? "ea" : "");
    }

    @Override
    public int compareTo(Version other) {
        if (this.major != other.major) {
            return this.major - other.major;
        } else if (this.minor != other.minor) {
            return this.minor - other.minor;
        } else if (this.patch != other.patch) {
            return this.patch - other.patch;
        } else if (this.isEarlyAccess == other.isEarlyAccess()) {
            return 0;
        } else if (this.isEarlyAccess) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Version)) {
            return false;
        } else {
            Version other = (Version) obj;
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        String hash = String.format("%03d%03d%03d", this.major, this.minor, this.patch);
        if (!this.isEarlyAccess) {
            hash = "1" + hash;
        }
        return Integer.parseInt(hash);
    }
}
