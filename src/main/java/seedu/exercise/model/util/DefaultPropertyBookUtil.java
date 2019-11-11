package seedu.exercise.model.util;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;
import static seedu.exercise.model.property.Calories.PROPERTY_CALORIES;
import static seedu.exercise.model.property.Date.PROPERTY_DATE;
import static seedu.exercise.model.property.Muscle.PROPERTY_MUSCLE;
import static seedu.exercise.model.property.Name.PROPERTY_NAME;
import static seedu.exercise.model.property.Quantity.PROPERTY_QUANTITY;
import static seedu.exercise.model.property.Unit.PROPERTY_UNIT;

import java.util.HashSet;
import java.util.Set;

import seedu.exercise.logic.parser.Prefix;

/**
 * Contains utility methods for initialising a default {@code PropertyBook}.
 */
public class DefaultPropertyBookUtil {

    /**
     * Creates a new {@code Set<Prefix>} that contains all the prefixes of the default exercise properties.
     */
    public static Set<Prefix> getDefaultPrefixes() {
        Set<Prefix> defaultPrefixes = new HashSet<>();
        defaultPrefixes.add(PREFIX_NAME);
        defaultPrefixes.add(PREFIX_DATE);
        defaultPrefixes.add(PREFIX_CALORIES);
        defaultPrefixes.add(PREFIX_QUANTITY);
        defaultPrefixes.add(PREFIX_MUSCLE);
        defaultPrefixes.add(PREFIX_UNIT);
        defaultPrefixes.add(PREFIX_INDEX);
        defaultPrefixes.add(PREFIX_CATEGORY);
        return defaultPrefixes;
    }

    /**
     * Creates a new {@code Set<String>} that contains all the full names of the default exercise properties.
     */
    public static Set<String> getDefaultFullNames() {
        Set<String> defaultFullNames = new HashSet<>();
        defaultFullNames.add(PROPERTY_NAME);
        defaultFullNames.add(PROPERTY_DATE);
        defaultFullNames.add(PROPERTY_CALORIES);
        defaultFullNames.add(PROPERTY_QUANTITY);
        defaultFullNames.add(PROPERTY_MUSCLE);
        defaultFullNames.add(PROPERTY_UNIT);
        return defaultFullNames;
    }
}
