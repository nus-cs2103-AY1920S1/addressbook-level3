package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.source.tree.Tree;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyTagCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.commands.DeletePolicyTagCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPolicyCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;
import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
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

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static ArrayList<String> commands = new ArrayList<>();

    private static int lengthLongerThanAllCommandWords = 100;
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
    public static Description parseDescription (String description) throws ParseException {
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
    public static Coverage parseCoverage (String coverage) throws ParseException {
        requireNonNull(coverage);
        String trimmedCoverage = coverage.trim();
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
    public static Price parsePrice (String price) throws ParseException {
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
    public static StartAge parseStartAge (String startAge) throws ParseException {
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
    public static EndAge parseEndAge (String endAge) throws ParseException {
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

    public static String parseCommand(String inputCommand, String arguments) throws ParseException {
        return similarPrefixesAndShortestDistance(inputCommand, arguments);
    }

    private static String similarPrefixesAndShortestDistance(String command, String arguments) {
        ArrayList<String> shortListedCommands = new ArrayList<>();
        boolean hasNoArguments = arguments.length() == 0;
        if (hasNoArguments) {
            shortListedCommands.addAll(getNoArgumentCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }
        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE_OF_BIRTH, PREFIX_POLICY, PREFIX_TAG, PREFIX_DESCRIPTION,
                PREFIX_COVERAGE, PREFIX_PRICE, PREFIX_START_AGE, PREFIX_END_AGE, PREFIX_CRITERIA);

        boolean hasUniquePersonPrefixes = anyPrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE_OF_BIRTH);
        if (hasUniquePersonPrefixes) {
            shortListedCommands.addAll(getUniquePersonPrefixCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }

        boolean hasUniquePolicyPrefixes = anyPrefixesPresent(argMultimap, PREFIX_DESCRIPTION,
                PREFIX_COVERAGE, PREFIX_PRICE, PREFIX_START_AGE, PREFIX_END_AGE);
        if (hasUniquePolicyPrefixes) {
            shortListedCommands.addAll(getUniquePolicyPrefixCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }

        boolean hasNamePrefix = anyPrefixesPresent(argMultimap, PREFIX_NAME);
        if (hasNamePrefix) {
            shortListedCommands.addAll(getUniquePersonPrefixCommands());
            shortListedCommands.addAll(getUniquePolicyPrefixCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }

        boolean hasTagPrefixes = anyPrefixesPresent(argMultimap, PREFIX_TAG);
        if (hasTagPrefixes) {
            shortListedCommands.addAll(getTagRelatedCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }

        // todo: hasCriteriaPrefixes

        boolean hasPolicyPrefix = anyPrefixesPresent(argMultimap, PREFIX_POLICY);
        if (hasPolicyPrefix) {
            shortListedCommands.addAll(getAssignCommands());
            return getShortestDistanceString(command, shortListedCommands);
        }

        boolean hasNoPrefixes = !anyPrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE_OF_BIRTH, PREFIX_POLICY, PREFIX_TAG, PREFIX_DESCRIPTION,
                PREFIX_COVERAGE, PREFIX_PRICE, PREFIX_START_AGE, PREFIX_END_AGE, PREFIX_CRITERIA);
        if (hasNoPrefixes) {
            shortListedCommands.addAll(getCommandsWithIndexArguments());
            return getShortestDistanceString(command, shortListedCommands);
        }

        return getShortestDistanceString(command);
    }

    private static ArrayList<String> getNoArgumentCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ListPeopleCommand.COMMAND_WORD);
        commandList.add(ListPolicyCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        return commandList;
    }

    private static ArrayList<String> getUniquePersonPrefixCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        return commandList;
    }

    private static ArrayList<String> getUniquePolicyPrefixCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AddPolicyCommand.COMMAND_WORD);
        commandList.add(EditPolicyCommand.COMMAND_WORD);
        return commandList;
    }

    private static ArrayList<String> getTagRelatedCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AddTagCommand.COMMAND_WORD);
        commandList.add(AddPolicyTagCommand.COMMAND_WORD);
        commandList.add(DeleteTagCommand.COMMAND_WORD);
        commandList.add(DeletePolicyTagCommand.COMMAND_WORD);
        return commandList;
    }

    private static ArrayList<String> getAssignCommands() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AssignPolicyCommand.COMMAND_WORD);
        commandList.add(UnassignPolicyCommand.COMMAND_WORD);
        return commandList;
    }

    private static ArrayList<String> getCommandsWithIndexArguments() {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add(AssignPolicyCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(FindPolicyCommand.COMMAND_WORD);
        commandList.add(DeletePolicyCommand.COMMAND_WORD);
        commandList.add(UnassignPolicyCommand.COMMAND_WORD);
        return commandList;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static String getShortestDistanceString(String input) {
        return getShortestDistanceString(input, commands);
    }
    // todo: settle those with equal distances
    private static String getShortestDistanceString(String input, ArrayList<String> commands) {
        String commandsThatHaveShortestDistanceAway = "";
        int distance = lengthLongerThanAllCommandWords;
        for (int i = 0; i < commands.size(); i++) {
            String originalCommand = commands.get(i);
            int thisDistance = getDistance(input, originalCommand);
            if (thisDistance < distance) {
                commandsThatHaveShortestDistanceAway = originalCommand;
                distance = thisDistance;
            }
        }
        return commandsThatHaveShortestDistanceAway;
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

    public static void addCommands(String... string) {
        ArrayList<String> addedCommands = (ArrayList<String>) Arrays.stream(string).collect(Collectors.toList());
        commands.addAll(addedCommands);
    }

    public static void removeCommands(String... string) {
        ArrayList<String> commandsToDelete = (ArrayList<String>) Arrays.stream(string).collect(Collectors.toList());
        for (int i = 0; i < commandsToDelete.size(); i++) {
            commands.remove(commandsToDelete.get(i));
        }
    }

}
