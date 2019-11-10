package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.USER_PREFIXES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListBinCommand;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;
import seedu.address.model.tag.Tag;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_POSITIVE_INT = "Value is not a positive integer.";

    private static HashSet<String> commands = new HashSet<>();

    private static int lengthLongerThanAllCommandWords = 100;

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
     * Parses a {@code String NRIC} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim().toUpperCase();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String dateOfBirth} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();
        if (!DateOfBirth.isValidDateOfBirth(trimmedDateOfBirth)) {
            throw new ParseException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        return new DateOfBirth(trimmedDateOfBirth);
    }

    /**
     * Parses a {@code String gender} into an {@code gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        if (gender.isEmpty()) {
            throw new ParseException(Gender.getMessageConstraints());
        }

        String formattedGender = ParserUtil.capitalizeFirstLetter(gender.trim());
        if (!Gender.isValidGender(formattedGender)) {
            throw new ParseException(Gender.getMessageConstraints());
        }
        return new Gender(formattedGender);
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
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code Collection<String> criteria} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseCriteria(Collection<String> criteria) throws ParseException {
        requireNonNull(criteria);
        final Set<Tag> criteriaSet = new HashSet<>();
        for (String criteriaName : criteria) {
            criteriaSet.add(parseTag(criteriaName));
        }
        return criteriaSet;
    }

    /**
     * Parses a {@code String name} into a {@code PolicyName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static PolicyName parsePolicyName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!PolicyName.isValidName(trimmedName)) {
            throw new ParseException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        return new PolicyName(trimmedName);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String coverage} into a {@code Coverage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Coverage parseCoverage(String coverage) throws ParseException {
        requireNonNull(coverage);
        String trimmedCoverage = coverage.trim().replaceAll(" +", " ");
        if (!Coverage.isValidCoverage(trimmedCoverage)) {
            throw new ParseException(Coverage.MESSAGE_CONSTRAINTS);
        }
        return new Coverage(trimmedCoverage);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String startAge} into a {@code StartAge}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startAge} is invalid.
     */
    public static StartAge parseStartAge(String startAge) throws ParseException {
        requireNonNull(startAge);
        String trimmedStartAge = startAge.trim();
        if (trimmedStartAge.length() == 0) {
            return new StartAge();
        }
        if (!StartAge.isValidAge(trimmedStartAge)) {
            throw new ParseException(StartAge.MESSAGE_CONSTRAINTS);
        }
        return new StartAge(trimmedStartAge);
    }

    /**
     * Parses a {@code String endAge} into a {@code EndAge}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startAge} is invalid.
     */
    public static EndAge parseEndAge(String endAge) throws ParseException {
        requireNonNull(endAge);
        String trimmedEndAge = endAge.trim();
        if (trimmedEndAge.length() == 0) {
            return new EndAge();
        }
        if (!EndAge.isValidAge(trimmedEndAge)) {
            throw new ParseException(EndAge.MESSAGE_CONSTRAINTS);
        }
        return new EndAge(trimmedEndAge);
    }

    /**
     * Parses a {@code String display indicator} into a {@code display indicator}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code displayIndicator} is invalid.
     */
    public static DisplayIndicator parseDisplayIndicator(String displayIndicator) throws ParseException {
        requireNonNull(displayIndicator);
        String trimmedDisplayIndicator = displayIndicator.trim();

        if (!DisplayIndicator.isValidDisplayIndicator(trimmedDisplayIndicator)) {
            throw new ParseException(DisplayIndicator.getMessageConstraints());
        }
        return new DisplayIndicator(trimmedDisplayIndicator);
    }

    /**
     * Parses a {@code String display format} into a {@code display format}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code displayFormat} is invalid.
     */
    public static DisplayFormat parseDisplayFormat(String displayFormat) throws ParseException {
        requireNonNull(displayFormat);
        String trimmedDisplayFormat = displayFormat.trim();

        if (!DisplayFormat.isValidDisplayFormat(trimmedDisplayFormat)) {
            throw new ParseException(DisplayFormat.getMessageConstraints());
        }
        return new DisplayFormat(trimmedDisplayFormat);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parsePositiveInt(String positiveInt) throws ParseException {
        String trimmedPositiveInt = positiveInt.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(positiveInt)) {
            throw new ParseException(MESSAGE_INVALID_POSITIVE_INT);
        }
        return Integer.parseInt(trimmedPositiveInt);
    }

    /**
     * Parses a {@String invalidInputCommandWord} into a {@String suggestedCommandWord}.
     *
     * @param inputCommand Invalid input command word by user.
     * @param arguments    Arguments of command input by user.
     * @return Suggested command word.
     */
    public static String parseCommand(String inputCommand, String arguments) {
        requireNonNull(inputCommand, arguments);
        return similarPrefixesAndShortestDistance(inputCommand, arguments);
    }

    /**
     * Shortlists commands based on prefixes present and calls the method to find the shortest distance between
     * the input command and the shortlisted command words.
     *
     * @param command   Input command.
     * @param arguments Arguments of the input command.
     * @return The command word closest to the input command word.
     */
    //todo update commands
    private static String similarPrefixesAndShortestDistance(String command, String arguments) {
        HashSet<String> shortListedCommands = new HashSet<>();
        boolean hasNoArguments = arguments.length() == 0;
        if (hasNoArguments) {
            shortListedCommands.addAll(getNoArgumentCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }
        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(arguments, USER_PREFIXES);

        for (int i = 0; i < USER_PREFIXES.length; i++) {
            Prefix prefix = USER_PREFIXES[i];
            if (argMultimap.getValue(prefix).isPresent()) {
                ArrayList<String> commandWords = prefix.getCommands();
                for (int j = 0; j < commandWords.size(); j++) {
                    shortListedCommands.add(commandWords.get(j));
                }
            }
        }
        if (shortListedCommands.size() != 0) {
            return getShortestDistanceString(command, shortListedCommands);
        } else {
            return getShortestDistanceString(command);
        }
    }

    private static ArrayList<String> getNoArgumentCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ListPeopleCommand.COMMAND_WORD);
        commandList.add(ListPolicyCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListBinCommand.COMMAND_WORD);
        return commandList;
    }

    private static String getShortestDistanceString(String input) {
        return getShortestDistanceString(input, commands);
    }

    private static String getShortestDistanceString(String input, HashSet<String> commands) {
        ArrayList<String> commandsThatHaveShortestDistanceAway = new ArrayList<>();
        Iterator<String> iterator = commands.iterator();
        int distance = lengthLongerThanAllCommandWords;
        for (int i = 0; i < commands.size(); i++) {
            String originalCommand = iterator.next();
            int thisDistance = getDistance(input, originalCommand);
            if (thisDistance < distance) {
                commandsThatHaveShortestDistanceAway.clear();
                commandsThatHaveShortestDistanceAway.add(originalCommand);
                distance = thisDistance;
            } else {
                commandsThatHaveShortestDistanceAway.add(originalCommand);
            }
        }
        if (commandsThatHaveShortestDistanceAway.size() == 1) {
            return commandsThatHaveShortestDistanceAway.get(0);
        } else {
            String suggestion = getNearestSubstring(input, commandsThatHaveShortestDistanceAway);
            return suggestion;
        }
    }

    private static String getNearestSubstring(String input, ArrayList<String> possibleSuggestions) {
        int longest = 0;
        String command = "";
        for (int i = 0; i < possibleSuggestions.size(); i++) {
            String thisCommand = possibleSuggestions.get(i);
            int lengthOfSubstring = getLongestSubstring(input, thisCommand);
            if (lengthOfSubstring > longest) {
                longest = lengthOfSubstring;
                command = thisCommand;
            }
        }
        return command;
    }

    private static int getLongestSubstring(String s1, String s2) {
        return getLongestSubstring(s1, s2, 0, 0, 0);
    }

    private static int getLongestSubstring(String s1, String s2, int s1Index, int s2Index, int counter) {
        if (s1Index >= s1.length() || s2Index >= s2.length()) {
            return counter;
        } else {
            if (s1.charAt(s1Index) == s2.charAt(s2Index)) {
                return getLongestSubstring(s1, s2, s1Index + 1, s2Index + 1, counter + 1);
            } else {
                return Math.max(counter,
                    Math.max(getLongestSubstring(s1, s2, s1Index + 1, s2Index, 0),
                        getLongestSubstring(s1, s2, s1Index, s2Index + 1, 0)));
            }
        }
    }

    private static int getDistance(String input, String originalCommand) {
        int[][] distanceArray = new int[input.length()][originalCommand.length()];
        int cols = originalCommand.length();
        int rows = input.length();
        for (int i = 0; i < cols; i++) {
            distanceArray[0][i] = i;
        }
        for (int j = 1; j < rows; j++) {
            distanceArray[j][0] = j;
            for (int k = 1; k < cols; k++) {
                int insert = distanceArray[j][k - 1] + 1;
                int delete = distanceArray[j - 1][k] + 1;
                int replace;
                if (input.charAt(j) == originalCommand.charAt(k)) {
                    replace = distanceArray[j - 1][k - 1];
                } else {
                    replace = distanceArray[j - 1][k - 1] + 2;
                }
                distanceArray[j][k] = Math.min(Math.min(insert, delete), replace);
            }
        }
        return distanceArray[rows - 1][cols - 1];
    }

    /**
     * Adds the command word to the list of command words.
     *
     * @param string Valid command words.
     */
    public static void addCommands(String... string) {
        ArrayList<String> addedCommands = (ArrayList<String>) Arrays.stream(string).collect(Collectors.toList());
        commands.addAll(addedCommands);
    }

    /**
     * Removes the command word from the list of command words.
     *
     * @param string Valid command words.
     */
    public static void removeCommands(String... string) {
        ArrayList<String> commandsToDelete = (ArrayList<String>) Arrays.stream(string).collect(Collectors.toList());
        for (int i = 0; i < commandsToDelete.size(); i++) {
            commands.remove(commandsToDelete.get(i));
        }
    }

    /**
     * Capitalises first letter in string.
     *
     * @param str string
     * @return string with first letter capitalised
     */
    private static String capitalizeFirstLetter(String str) {
        if (str == null) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
