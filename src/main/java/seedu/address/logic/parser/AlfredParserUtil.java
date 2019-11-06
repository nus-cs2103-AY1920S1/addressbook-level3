package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIE_BREAK;
import static seedu.address.commons.core.Messages.MISSING_TIEBREAK_METHODS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.Comparators;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.LeaderboardUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.ParseIdException;
import seedu.address.logic.parser.findcommandparser.FindCommandUtilEnum;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class AlfredParserUtil {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<specifier>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AlfredParserUtil.class);
    private static final String ID_SEPARATOR_CHARACTER = "-";

    /**
     * Parses {@code entityId} into an {@code Id} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid in an invalid format.
     */
    public static Id parseIndex(String entityId, PrefixType prefix) throws ParseIdException {
        entityId = entityId.trim();
        validateIdFormat(entityId);
        String idValue = entityId.substring(2);
        String idSeparator = Character.toString(entityId.charAt(1));
        String expectedPrefix = prefix.name();

        if (!StringUtil.isNonZeroUnsignedInteger(idValue) || !entityId.startsWith(expectedPrefix)
                || !idSeparator.equals(ID_SEPARATOR_CHARACTER)) {
            throw new ParseIdException(MESSAGE_INVALID_INDEX);
        }
        int idNumber = Integer.parseInt(idValue);
        return new Id(prefix, idNumber);
    }

    /**
     * Checks whether the format of the user inputted entity ID {@code idString} in the command
     * is correct.
     *
     * @throws ParseIdException if the entity ID format is incorrect.
     */
    private static void validateIdFormat(String idString) throws ParseIdException {
        try {
            String trimmedIndex = idString.substring(2);
            String idSeparator = Character.toString(idString.charAt(1));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseIdException(MESSAGE_INVALID_INDEX);
        }
    }

    /**
     * Parses the {@code userInput} to separate the specifier from the user's input, where
     * the specifier is the entity or entity's ID specifying either which parser to call
     * or which specific entity to handle.
     *
     * @param userInput the user's command input.
     * @return String representation of the specifier.
     * @throws ParseException if the user input is invalid.
     */
    public static String getSpecifierFromCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        String entity = matcher.group("specifier");
        return entity;
    }

    /**
     * Parses the {@code userInput} to separate the arguments from the user's input, where
     * the arguments are the additional details the user provides as part of the command's
     * requirements.
     *
     * @param userInput the user's command input.
     * @return String representation of the arguments.
     * @throws ParseException if the user input is invalid.
     */
    public static String getArgumentsFromCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        String args = matcher.group("arguments");
        return args;
    }

    /**
     * Parses the {@code userInput} to separate the arguments from the user's input, where
     * the arguments are the additional details the user provides as part of the command's
     * requirements. This method ensures that the arguments are not empty.
     *
     * @param userInput the user's command input.
     * @return String representation of the arguments.
     * @throws ParseException if the argument String from the command are empty.
     */
    public static String getNonEmptyArgumentFromCommand(String userInput) throws ParseException {
        String args = getArgumentsFromCommand(userInput);
        if (args.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return args;
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
            logger.severe("Name is not in the valid format: " + name);
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String score} into a {@code Score}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param score the string score inputted by the user.
     * @return a Score object representing the score inputted by the user.
     * @throws ParseException if the score is of invalid format.
     */
    public static Score parseScore(String score) throws ParseException {
        requireNonNull(score);
        String trimmedScore = score.trim();
        validateScore(trimmedScore);
        int scoreValue = Integer.parseInt(trimmedScore);
        if (!Score.isValidScore(scoreValue)) {
            logger.severe("Score is not in the valid format: " + scoreValue);
            throw new ParseException(Score.MESSAGE_CONSTRAINTS);
        }
        return new Score(scoreValue);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String} into a {@code Location}.
     *
     * @param location
     * @return Location
     * @throws ParseException if the {@code String} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        try {
            int trimmedLocation = Integer.parseInt(location.trim());
            if (!Location.isValidLocation(trimmedLocation)) {
                logger.severe("Integer location is not in correct format:" + location);
                throw new ParseException(Location.MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);
            }
            return new Location(trimmedLocation);
        } catch (NumberFormatException e) {
            logger.severe("Integer cannot be parsed from location: " + location);
            throw new ParseException(Location.MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);
        }

    }

    /**
     * Parses a {@code String} into a {@code Subject}.
     *
     * @param subject
     * @return SubjectName
     * @throws ParseException if the {@code String} is invalid.
     */
    public static SubjectName parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!SubjectName.isValidSubjectName(trimmedSubject)) {
            throw new ParseException(SubjectName.MESSAGE_CONSTRAINTS);
        }
        for (SubjectName subjectName : SubjectName.values()) {
            if (subjectName.toString().equalsIgnoreCase(trimmedSubject)) {
                return subjectName;
            }
        }
        throw new ParseException(SubjectName.MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns a new comparator based on the tie-break {@code method} specified by the users.
     *
     * @return new comparator for Team objects.
     * @throws ParseException if the tie-break method specified is invalid.
     */
    public static Comparator<Team> getAppropriateComparator(String method) throws ParseException {
        method = method.trim();
        switch(method) {
        case Comparators.HIGHER_ID:
            return Comparators.rankByIdDescending();
        case Comparators.LOWER_ID:
            return Comparators.rankByIdAscending();
        case Comparators.MORE_PARTICIPANTS:
            return Comparators.rankByParticipantsDescending();
        case Comparators.LESS_PARTICIPANTS:
            return Comparators.rankByParticipantsAscending();
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_TIE_BREAK + method));
        }
    }

    /**
     * Checks if the user specified the tiebreaking method "random" correctly.
     *
     * @param methods the string array containing every user inputted tie-breaking method.
     * @return {@code true} if "random" is mentioned as one of the methods and is the last in the list.
     * @throws ParseException if the method specification format is incorrect.
     */
    public static boolean isRandomPresent(String[] methods) throws ParseException {
        if (!Arrays.asList(methods).contains(LeaderboardUtil.RANDOM_KEYWORD)) {
            return false;
        } else if (randomAtCorrectPlace(methods)) {
            return true;
        } else {
            throw new ParseException(LeaderboardUtil.RANDOM_USAGE_WARNING);
        }
    }

    private static boolean randomAtCorrectPlace(String[] methods) {
        int size = methods.length;
        return methods[size - 1].equals(LeaderboardUtil.RANDOM_KEYWORD);
    }

    /**
     * Returns an ArrayList of comparators populated with the appropriate comparator depending on the
     * strings present in the array {@code tieBreakMethods}.
     *
     * @return an ArrayList of comparators.
     * @throws ParseException if a specified does not exist or is in a wrong format.
     */
    public static ArrayList<Comparator<Team>> processedComparators(String[] tieBreakMethods) throws ParseException {
        checkNoTieBreakMethods(tieBreakMethods);
        ArrayList<Comparator<Team>> allComparators = new ArrayList<>();
        for (String method : tieBreakMethods) {
            if (method.trim().equals(LeaderboardUtil.RANDOM_KEYWORD)) {
                continue;
            }
            allComparators.add(AlfredParserUtil.getAppropriateComparator(method));
        }
        // Reverse the order of comparators for them to applied in the order users specified.
        Collections.reverse(allComparators);
        return allComparators;
    }

    private static void checkNoTieBreakMethods(String[] methods) throws ParseException {
        if (methods.length == 1 && methods[0].equals("")) {
            throw new ParseException(MISSING_TIEBREAK_METHODS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    /**
     * Validates whether {@code score} is a valid integer.
     *
     * @param score the string representation of the score under check.
     * @throws ParseException if the score is not a valid integer.
     */
    public static void validateScore(String score) throws ParseException {
        if (score.equals("")) {
            throw new ParseException(Score.MESSAGE_CONSTRAINTS);
        }
        try {
            int scoreValue = Integer.parseInt(score);
        } catch (NumberFormatException e) {
            throw new ParseException(Score.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Find the type of search we are going to perform.
     *
     * @param string the user input string
     */
    public static FindCommandUtilEnum getFindType(String string) throws ParseException {
        boolean isAndType = string.contains(FindCommandUtilEnum.AND.name());
        boolean isOrType = string.contains(FindCommandUtilEnum.OR.name());

        if (isAndType && isOrType) {
            throw new ParseException("You cannot find by AND and OR");
        }

        // Default to AND if find type is not given
        if (!isAndType && !isOrType) {
            return FindCommandUtilEnum.AND;
        }

        return isAndType ? FindCommandUtilEnum.AND : FindCommandUtilEnum.OR;
    }

    /**
     * This gets the String containing all the prefixes to be excluded.
     *
     * @param string
     * @return the String containing the exclude clauses
     * @throws ParseException if the input given is wrong
     */
    public static String getExcludeString(String string) throws ParseException {
        String[] arr = string.split(FindCommandUtilEnum.EXCLUDE.name());
        if (arr.length > 2) {
            throw new ParseException("Only one EXCLUDE option should be given");
        }
        if (arr.length == 1) {
            return "";
        }
        String negString = arr[1];

        // Checks if the negative string is valid.
        if (negString.contains(FindCommandUtilEnum.AND.name())
                || negString.contains(FindCommandUtilEnum.OR.name())) {
            throw new ParseException("Position your find types at the start");
        }
        return negString.trim();
    }

    /**
     * Gets the AndOrString to parse for input.
     *
     * @param string
     * @return String
     * @throws ParseException if the input given is wrong.
     */
    public static String getAndOrString(String string) throws ParseException {
        String andOrString = string.split(FindCommandUtilEnum.EXCLUDE.name())[0].trim();
        return andOrString;
    }

    /**
     * Pass in a string with the entity name removed.
     *
     * @param string which does not contain the entity type
     * @throws ParseException if the AND or OR is not at the start of the string
     */
    public static void isFindTypeAtStart(String string) throws ParseException {
        // We trim the string, then check if the first x letters are the keywords we expect
        // Note: This function does not check for duplicate OR or AND declarations
        // That is handled by getFindType
        // Trim string at first just in case since having a starting space will cause bugs
        string = string.trim();
        if (string.contains(FindCommandUtilEnum.AND.name())) {
            if (!string.startsWith(FindCommandUtilEnum.AND.name())) {
                throw new ParseException("AND has to be before the prefixes");
            }
        } else if (string.contains(FindCommandUtilEnum.OR.name())) {
            if (!string.startsWith(FindCommandUtilEnum.OR.name())) {
                throw new ParseException("OR has to be before the prefixes");
            }
        }
    }
}
