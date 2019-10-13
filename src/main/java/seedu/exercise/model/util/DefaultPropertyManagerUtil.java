package seedu.exercise.model.util;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.exercise.model.exercise.Calories.PROPERTY_CALORIES;
import static seedu.exercise.model.exercise.Date.PROPERTY_DATE;
import static seedu.exercise.model.exercise.Muscle.PROPERTY_MUSCLE;
import static seedu.exercise.model.exercise.Name.PROPERTY_NAME;
import static seedu.exercise.model.exercise.Quantity.PROPERTY_QUANTITY;
import static seedu.exercise.model.exercise.Unit.PROPERTY_UNIT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.PropertyManager;

/**
 * Contains utility methods for initialising a default {@code PropertyManager}.
 */
public class DefaultPropertyManagerUtil {

    /**
     * Creates a new {@code PropertyManager} that contains the default short names, default full names and
     * an empty list of default custom properties.
     */
    public static PropertyManager getDefaultPropertyManager() {
        Set<Prefix> defaultPrefixes = getDefaultPrefixes();
        Set<String> defaultFullNames = getDefaultFullNames();
        List<CustomProperty> defaultCustomProperties = getDefaultCustomProperties();
        return new PropertyManager(defaultPrefixes, defaultFullNames, defaultCustomProperties);
    }

    /**
     * Creates a new {@code Set<Prefix>} that contains all the short names of the default exercise properties.
     */
    private static Set<Prefix> getDefaultPrefixes() {
        Set<Prefix> defaultPrefixes = new HashSet<>();
        defaultPrefixes.add(PREFIX_NAME);
        defaultPrefixes.add(PREFIX_DATE);
        defaultPrefixes.add(PREFIX_CALORIES);
        defaultPrefixes.add(PREFIX_QUANTITY);
        defaultPrefixes.add(PREFIX_MUSCLE);
        defaultPrefixes.add(PREFIX_UNIT);
        return defaultPrefixes;
    }

    /**
     * Creates a new {@code Set<String>} that contains all the full names of the default exercise properties.
     */
    private static Set<String> getDefaultFullNames() {
        Set<String> defaultFullNames = new HashSet<>();
        defaultFullNames.add(PROPERTY_NAME);
        defaultFullNames.add(PROPERTY_DATE);
        defaultFullNames.add(PROPERTY_CALORIES);
        defaultFullNames.add(PROPERTY_QUANTITY);
        defaultFullNames.add(PROPERTY_MUSCLE);
        defaultFullNames.add(PROPERTY_UNIT);
        return defaultFullNames;
    }

    /**
     * Creates a new empty {@code List<CustomProperty>}
     */
    private static List<CustomProperty> getDefaultCustomProperties() {
        return new ArrayList<>();
    }
}
