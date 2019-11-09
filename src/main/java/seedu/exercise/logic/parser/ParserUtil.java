package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_TYPE;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.parser.AddCommandParser.ADD_CATEGORY_EXERCISE;
import static seedu.exercise.logic.parser.AddCommandParser.ADD_CATEGORY_REGIME;
import static seedu.exercise.logic.parser.SuggestCommandParser.SUGGEST_TYPE_BASIC;
import static seedu.exercise.logic.parser.SuggestCommandParser.SUGGEST_TYPE_POSSIBLE;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.OPERATION_TYPE_AND;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.OPERATION_TYPE_OR;
import static seedu.exercise.ui.ListResourceType.LIST_RESOURCE_TYPE_CONSTRAINTS;
import static seedu.exercise.ui.ListResourceType.LIST_TYPE_EXERCISE;
import static seedu.exercise.ui.ListResourceType.LIST_TYPE_REGIME;
import static seedu.exercise.ui.ListResourceType.LIST_TYPE_SCHEDULE;
import static seedu.exercise.ui.ListResourceType.LIST_TYPE_SUGGESTION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.util.StringUtil;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.logic.parser.predicate.BasePropertyPredicate;
import seedu.exercise.logic.parser.predicate.ExerciseCustomPropertyPredicate;
import seedu.exercise.logic.parser.predicate.ExerciseMusclePredicate;
import seedu.exercise.logic.parser.predicate.ExercisePredicate;
import seedu.exercise.logic.parser.predicate.PredicateUtil;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.model.property.custom.ParameterType;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS);
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
     * Parses a {@code String endDate} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code endDate} is invalid.
     */
    public static Date parseEndDate(Date startDate, String endDate) throws ParseException {
        requireAllNonNull(startDate, endDate);
        String trimmedEndDate = endDate.trim();
        parseDate(trimmedEndDate);
        if (!Date.isEndDateAfterStartDate(startDate.toString(), trimmedEndDate)) {
            throw new ParseException(Date.MESSAGE_INVALID_END_DATE);
        }
        return new Date(trimmedEndDate);
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
     * Parses a {@code category} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid
     */
    public static String parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!trimmedCategory.equals(ADD_CATEGORY_EXERCISE)
            && !trimmedCategory.equals(ADD_CATEGORY_REGIME)) {
            throw new ParseException("Category can only be \'" + ADD_CATEGORY_EXERCISE + "\'"
                + " or \'" + ADD_CATEGORY_REGIME + "\'");
        }
        return trimmedCategory;
    }

    /**
     * Parses a {@code listType} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code listType} is invalid
     */
    public static ListResourceType parseListResourceType(String listType) throws ParseException {
        requireNonNull(listType);
        String trimmedCategory = listType.trim();
        switch (trimmedCategory) {
        case LIST_TYPE_EXERCISE:
            return ListResourceType.EXERCISE;

        case LIST_TYPE_REGIME:
            return ListResourceType.REGIME;

        case LIST_TYPE_SCHEDULE:
            return ListResourceType.SCHEDULE;

        case LIST_TYPE_SUGGESTION:
            return ListResourceType.SUGGESTION;

        default:
            throw new ParseException(LIST_RESOURCE_TYPE_CONSTRAINTS);
        }
    }

    /**
     * Parses and trims all of the values in {@code Map<String, String> customProperties}.
     *
     * @throws ParseException if any of the keys present in {@code customProperties} is invalid.
     */
    public static Map<String, String> parseCustomProperties(Map<String, String> customProperties)
        throws ParseException {
        requireNonNull(customProperties);
        Set<CustomProperty> allCustomProperties = PropertyBook.getInstance().getCustomProperties();
        final Map<String, String> customPropertiesMap = new TreeMap<>();
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
    public static String parseFullName(String fullName) throws ParseException {
        requireNonNull(fullName);
        String trimmedFullName = fullName.trim();
        if (!CustomProperty.isValidFullName(trimmedFullName)) {
            throw new ParseException(CustomProperty.FULL_NAME_CONSTRAINTS);
        }
        return toStartCase(trimmedFullName);
    }

    /**
     * Parses and trims the leading and trailing whitespaces of {@code String prefixName}.
     *
     * @param prefixName the intended prefix name for a custom property
     * @return a {@code Prefix} object containing the trimmed prefix name for a custom property
     * @throws ParseException if the given prefix name is invalid
     */
    public static Prefix parsePrefixName(String prefixName) throws ParseException {
        requireNonNull(prefixName);
        String trimmedPrefixName = prefixName.trim();
        if (!CustomProperty.isValidPrefixName(trimmedPrefixName)) {
            throw new ParseException(CustomProperty.PREFIX_NAME_CONSTRAINTS);
        }
        return new Prefix(prefixName + "/");
    }

    /**
     * Parses a {@code String parameterType} into a {@code ParameterType}.
     *
     * @param parameterType the intended parameter type for a custom property
     * @return a {@code ParameterType} that corresponds with the intended parameter type
     * @throws ParseException if the intended parameter type is invalid
     */
    public static ParameterType parseParameterType(String parameterType) throws ParseException {
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
     * Parses a {@code String suggestType} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param suggestType the intended suggest type
     * @return a trimmed suggest type of a suggest command
     * @throws ParseException if the intended suggest type is invalid
     */
    public static String parseSuggestType(String suggestType) throws ParseException {
        requireNonNull(suggestType);
        String trimmedSuggestType = suggestType.trim();
        if (!trimmedSuggestType.equals(SUGGEST_TYPE_BASIC) && !trimmedSuggestType.equals(SUGGEST_TYPE_POSSIBLE)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_TYPE, "Suggest type", SUGGEST_TYPE_BASIC, SUGGEST_TYPE_POSSIBLE));
        }
        return trimmedSuggestType;
    }

    /**
     * Parses a {@code String operationType} into a boolean.
     *
     * @param operationType the intended operation type
     * @return a {@code boolean} representing the whether the operation type is "and" or "or".
     * @throws ParseException if the intended operation type is invalid
     */
    public static boolean parseOperationType(String operationType) throws ParseException {
        requireNonNull(operationType);
        String trimmedOperationType = operationType.trim();

        if (trimmedOperationType.equals(OPERATION_TYPE_AND)) {
            return true;
        }

        if (trimmedOperationType.equals(OPERATION_TYPE_OR)) {
            return false;
        }

        throw new ParseException(PredicateUtil.OPERATION_TYPE_CONSTRAINTS);
    }

    /**
     * Parses {@code Set<Muscle> muscles}, {@code Map<String, String> customProperties} and {@code boolean isStrict}
     * into a {@code Predicate<Exercise>}.
     */
    public static Predicate<Exercise> parsePredicate(
        Set<Muscle> muscles, Map<String, String> customProperties, boolean isStrict) throws ParseException {
        requireNonNull(muscles);
        requireNonNull(customProperties);

        if (muscles.isEmpty() && customProperties.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE));
        }

        BasePropertyPredicate musclePredicate = new ExerciseMusclePredicate(muscles, isStrict);
        BasePropertyPredicate customPropertiesPredicate =
            new ExerciseCustomPropertyPredicate(customProperties, isStrict);

        if (muscles.isEmpty()) {
            return new ExercisePredicate(isStrict, customPropertiesPredicate);
        }

        if (customProperties.isEmpty()) {
            return new ExercisePredicate(isStrict, musclePredicate);
        }

        Predicate<Exercise> predicate = new ExercisePredicate(isStrict, musclePredicate, customPropertiesPredicate);
        return predicate;
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
     * Parses a {@code String chart} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code chart} is invalid
     */
    public static String parseChart(String chart) throws ParseException {
        requireNonNull(chart);
        String trimmedCategory = chart.trim();
        if (!trimmedCategory.equals("piechart") && !trimmedCategory.equals("linechart")
            && !trimmedCategory.equals("barchart")) {
            throw new ParseException(Statistic.MESSAGE_INVALID_CHART_TYPE);
        }
        return trimmedCategory;
    }

    /**
     * Parses a {@code String statisticCategory} into a String.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code statisticCategory} is invalid
     */
    public static String parseStatisticCategory(String statisticCategory) throws ParseException {
        requireNonNull(statisticCategory);
        String trimmedCategory = statisticCategory.trim();
        if (!trimmedCategory.equals("exercise") && !trimmedCategory.equals("calories")) {
            throw new ParseException(Statistic.MESSAGE_INVALID_CATEGORY);
        }
        return trimmedCategory;
    }
}
