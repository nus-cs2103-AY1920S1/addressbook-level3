package seedu.billboard.model.statistics.formats;

import static seedu.billboard.logic.parser.CliSyntax.PREFIX_GROUPING;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.billboard.logic.parser.Prefix;

/**
 * Enum representing the supported statistics formats.
 */
public enum StatisticsFormat {
    TIMELINE("timeline", PREFIX_GROUPING, PREFIX_INTERVAL),
    BREAKDOWN("breakdown", PREFIX_GROUPING),
    HEAT_MAP("heatmap", PREFIX_GROUPING);

    private static Map<String, StatisticsFormat> nameToFormatMap;
    private final String name;
    private final Prefix[] optionsPrefixes;

    static {
        nameToFormatMap = new HashMap<>();
        for (StatisticsFormat format : StatisticsFormat.values()) {
            nameToFormatMap.put(format.getName(), format);
        }
    }

    StatisticsFormat(String name, Prefix... optionsPrefixes) {
        this.name = name;
        this.optionsPrefixes = optionsPrefixes;
    }

    /**
     * Returns the correct {@code StatisticsFormat} corresponding to the input name.
     *
     * @param name String name of a {@code StatisticsFormat}
     * @return Optional wrapper around the corresponding {@code StatisticsFormat} or an empty optional if no
     * corresponding format exists.
     */
    public static Optional<StatisticsFormat> formatFromName(String name) {
        return Optional.ofNullable(nameToFormatMap.get(name));
    }

    /**
     * Returns the name of the {@code StatisticsFormat}.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the supported prefixes for options of the {@code StatisticsFormat}.
     */
    public Prefix[] getOptionsPrefixes() {
        return optionsPrefixes;
    }
}
