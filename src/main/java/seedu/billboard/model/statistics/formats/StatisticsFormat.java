package seedu.billboard.model.statistics.formats;

import seedu.billboard.commons.exceptions.IllegalValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the supported statistics formats.
 */
public enum StatisticsFormat {
    TIMELINE("timeline"),
    BREAKDOWN("breakdown");

    private static Map<String, StatisticsFormat> nameToFormatMap;
    private final String name;

    static {
        nameToFormatMap = new HashMap<>();

        for (StatisticsFormat format : StatisticsFormat.values()) {
            nameToFormatMap.put(format.getName(), format);
        }
    }

    StatisticsFormat(String name) {
        this.name = name;
    }

    /**
     * Returns the correct {@code StatisticsFormat} corresponding to the input name
     * @param name String name of a {@code StatisticsFormat}
     * @return The corresponding format.
     * @throws IllegalValueException if the input string does not have a corresponding {@code StatisticsFormat}.
     */
    public static StatisticsFormat formatFromName(String name) throws IllegalValueException {

        StatisticsFormat format = nameToFormatMap.get(name);
        if (format == null) {
            throw new IllegalValueException("StatisticsFormat corresponding to input name does not exist");
        }

        return format;
    }

    /**
     * Returns the name of the {@code StatisticsFormat}.
     */
    public String getName() {
        return name;
    }
}
