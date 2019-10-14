package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.model.exercise.PropertyManager.getCustomProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.util.StringUtil;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.exercise.Calories;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Muscle;
import seedu.exercise.model.exercise.Name;
import seedu.exercise.model.exercise.ParameterType;
import seedu.exercise.model.exercise.Quantity;
import seedu.exercise.model.exercise.Unit;
import seedu.exercise.model.regime.RegimeName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code Collection<String> indexes} into a {@code ArrayList<Index>}.
     */
    public static ArrayList<Index> parseIndexes(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);
        final ArrayList<Index> indexSet = new ArrayList<>();
        for (String index : indexes) {
            indexSet.add(parseIndex(index));
        }
        return indexSet;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code RegimeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static RegimeName parseRegimeName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!RegimeName.isValidRegimeName(trimmedName)) {
            throw new ParseException(RegimeName.MESSAGE_CONSTRAINTS);
        }
        return new RegimeName(trimmedName);
    }

    /**
     * Parses a {@code String unit} into a {@code Unit}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Unit parseUnit(String unit) throws ParseException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();
        if (!Unit.isValidUnit(trimmedUnit)) {
            throw new ParseException(Unit.MESSAGE_CONSTRAINTS);
        }
        return new Unit(trimmedUnit);
    }

    /**
     * Parses a {@code String calories} into a {@code Calories}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calories} is invalid.
     */
    public static Calories parseCalories(String calories) throws ParseException {
        requireNonNull(calories);
        String trimmedCalories = calories.trim();
        if (!Calories.isValidCalories(trimmedCalories)) {
            throw new ParseException(Calories.MESSAGE_CONSTRAINTS);
        }
        return new Calories(trimmedCalories);
    }

    /**
     * Parses a {@code String quantity} into an {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String muscle} into a {@code Muscle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code muscle} is invalid.
     */
    public static Muscle parseMuscle(String muscle) throws ParseException {
        requireNonNull(muscle);
        String trimmedMuscle = muscle.trim();
        if (!Muscle.isValidMuscleName(trimmedMuscle)) {
            throw new ParseException(Muscle.MESSAGE_CONSTRAINTS);
        }
        return new Muscle(trimmedMuscle);
    }

    /**
     * Parses {@code Collection<String> muscles} into a {@code Set<Muscle>}.
     */
    public static Set<Muscle> parseMuscles(Collection<String> muscles) throws ParseException {
        requireNonNull(muscles);
        final Set<Muscle> muscleSet = new HashSet<>();
        for (String muscleName : muscles) {
            muscleSet.add(parseMuscle(muscleName));
        }
        return muscleSet;
    }

    /**
     * Parses a {@code String category} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid
     */
    public static String parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!trimmedCategory.equals("exercise") && !trimmedCategory.equals("regime")) {
            throw new ParseException("Category can only be \'exercise\' or \'regime\'");
        }
        return trimmedCategory;
    }

    /**
     * Parses and trims all of the keys in {@code Map<String, String> customProperties}.
     *
     * @throws ParseException if any of the keys present in {@code customProperties} is invalid.
     */
    static Map<String, String> parseCustomProperties(Map<String, String> customProperties)
        throws ParseException {
        requireNonNull(customProperties);
        List<CustomProperty> allCustomProperties = getCustomProperties();
        final Map<String, String> customPropertiesMap = new HashMap<>();
        for (CustomProperty property : allCustomProperties) {
            String propertyName = property.getFullName();
            if (customProperties.containsKey(propertyName)) {
                String rawResult = customProperties.get(propertyName);
                String trimmedResult = parseCustomProperty(property, rawResult);
                customPropertiesMap.put(propertyName, trimmedResult);
            }
        }
        return customPropertiesMap;
    }

    /**
     * Trims, validates and formats the full name of a {@code String fullName}.
     *
     * @param fullName the full name of a custom property
     * @return a trimmed full name of a custom property in Start Case style.
     * @throws ParseException if the given full name is invalid.
     */
    static String parseFullName(String fullName) throws ParseException {
        requireNonNull(fullName);
        String trimmedFullName = fullName.trim();
        if (!CustomProperty.isValidFullName(trimmedFullName)) {
            throw new ParseException(CustomProperty.FULL_NAME_CONSTRAINTS);
        }
        return toStartCase(trimmedFullName);
    }

    /**
     * Parses and trims the leading and trailing whitespaces of {@code String shortName}.
     *
     * @param shortName the intended short name for a custom property
     * @return a {@code Prefix} object containing the trimmed short name for a custom property
     * @throws ParseException if the given short name is invalid
     */
    static Prefix parseShortName(String shortName) throws ParseException {
        requireNonNull(shortName);
        String trimmedShortName = shortName.trim();
        System.out.println(trimmedShortName);
        if (!CustomProperty.isValidShortName(trimmedShortName)) {
            throw new ParseException(CustomProperty.SHORT_NAME_CONSTRAINTS);
        }
        return new Prefix(shortName + "/");
    }

    /**
     * Parses a {@code String parameterType} into a {@code ParameterType}.
     *
     * @param parameterType the intended parameter type for a custom property
     * @return a {@code ParameterType} that corresponds with the intended parameter type
     * @throws ParseException if the intended parameter type is invalid
     */
    static ParameterType parseParameterType(String parameterType) throws ParseException {
        requireNonNull(parameterType);
        String trimmedParameterType = parameterType.trim();

        if (!ParameterType.isValidParameterType(trimmedParameterType)) {
            throw new ParseException(ParameterType.PARAMETER_CONSTRAINTS);
        }

        String numParam = ParameterType.NUMBER.getParameterName();
        String textParam = ParameterType.TEXT.getParameterName();

        if (trimmedParameterType.equals(numParam)) {
            return ParameterType.NUMBER;
        } else if (trimmedParameterType.equals(textParam)) {
            return ParameterType.TEXT;
        } else {
            return ParameterType.DATE;
        }
    }

    /**
     * Formats a single word by capitalising the first letter and setting the remaining
     * as lowercase.
     */
    private static String capitaliseSingleWord(String word) {
        String capitalisedFirstLetter = word.substring(0, 1).toUpperCase();
        String lowercaseRemaining = word.substring(1).toLowerCase();
        return capitalisedFirstLetter + lowercaseRemaining;
    }

    /**
     * Formats a group of words by capitalising the first letter and setting the remaining
     * as lower case for each word present. Any additional spaces between 2 words are now
     * reduced to a single space.
     */
    private static String toStartCase(String words) {
        String[] tokens = words.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String token : tokens) {
            builder.append(capitaliseSingleWord(token)).append(" ");
        }
        return builder.toString().stripTrailing();
    }

    /**
     * Parses and trims {@code String propertyValue} based on the {@code CustomProperty}.
     */
    private static String parseCustomProperty(CustomProperty property, String propertyValue)
        throws ParseException {
        requireNonNull(property, propertyValue);
        ParameterType paramType = property.getParameterType();
        if (paramType.equals(ParameterType.DATE)) {
            return parseDate(propertyValue).toString();
        } else if (paramType.equals(ParameterType.NUMBER)) {
            return parseNumber(propertyValue);
        } else {
            return parseText(propertyValue);
        }
    }

    /**
     * Parses and trims the leading and trailing white spaces of {@code String text}.
     *
     * @throws ParseException if the given {@code text} is invalid.
     */
    private static String parseText(String text) throws ParseException {
        requireNonNull(text);
        String trimmedText = text.trim();
        if (!ParameterType.isValidText(trimmedText)) {
            throw new ParseException(ParameterType.TEXT_CONSTRAINTS);
        }
        return trimmedText;
    }

    /**
     * Parses and trims the leading and trailing white spaces of {@code String number}.
     *
     * @throws ParseException if the given {@code number} is invalid.
     */
    private static String parseNumber(String number) throws ParseException {
        requireNonNull(number);
        String trimmedNumber = number.trim();
        if (!ParameterType.isValidNumber(trimmedNumber)) {
            throw new ParseException(ParameterType.NUMBER_CONSTRAINTS);
        }
        return trimmedNumber;
    }


    /**
     * Parses a {@code String suggestType} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static String parseSuggestType(String suggestType) throws ParseException {
        requireNonNull(suggestType);
        String trimmedSuggestType = suggestType.trim();
        if (!trimmedSuggestType.equals("basic")) {
            throw new ParseException("Suggest type can only be \'basic\'");
        }
        return trimmedSuggestType;
    }
}
