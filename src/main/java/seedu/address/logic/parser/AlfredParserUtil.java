package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class AlfredParserUtil {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<specifier>\\S+)(?<arguments>.*)");
    private static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(AlfredParserUtil.class);
    private static final String ID_SEPARATOR_CHARACTER = "-";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Id parseIndex(String oneBasedIndex, PrefixType prefix) throws ParseException {
        oneBasedIndex = oneBasedIndex.trim();
        String trimmedIndex;
        String idSeparator;
        try {
            trimmedIndex = oneBasedIndex.substring(2);
            idSeparator = Character.toString(oneBasedIndex.charAt(1));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        String expectedPrefix = prefix.name();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex) || !oneBasedIndex.startsWith(expectedPrefix)
                || !idSeparator.equals(ID_SEPARATOR_CHARACTER)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        int idNumber = Integer.parseInt(trimmedIndex);
        return new Id(prefix, idNumber);
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
            logger.severe("Integer cannot be parsed from location:" + location);
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
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
