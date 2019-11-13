package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.addcommand.AddCommand;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

//@@author KxxMxxx
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");
    private static final int LOWEST_PRIORITY = 0;
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        final Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");
        switch (type) {
        case AddAccommodationCommand.SECOND_COMMAND_WORD:
            return parseAccommodation(arguments);
        case AddActivityCommand.SECOND_COMMAND_WORD:
            return parseActivity(arguments);
        case AddContactCommand.SECOND_COMMAND_WORD:
            return parseContact(arguments);
        case AddDayCommand.SECOND_COMMAND_WORD:
            return parseDay(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddAccommodationCommand for an Accommodation
     * and returns an AddAccommodationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddAccommodationCommand parseAccommodation(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAccommodationCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Contact contact = new Contact(name, phone, null, address, new HashSet<Tag>());
            Accommodation accommodation = new Accommodation(name, address, contact, tagList);
            return new AddAccommodationCommand(accommodation, false);
        } else {
            Accommodation accommodation = new Accommodation(name, address, null, tagList);
            return new AddAccommodationCommand(accommodation, false);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddActivityCommand for an Activity
     * and returns an AddActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddActivityCommand parseActivity(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_COST, PREFIX_TAG, PREFIX_DURATION, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddActivityCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Contact contact = null;

        Priority priority = new Priority(LOWEST_PRIORITY);
        Cost cost = argMultimap.getValue(PREFIX_COST).isPresent()
                ? ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get())
                : null;

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            contact = new Contact(name, phone, null, address, new HashSet<Tag>());
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        }
        Activity activity = new Activity(name, address, contact, cost, tagList, duration, priority);
        return new AddActivityCommand(activity, false);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand for a Contact and returns an
     * AddContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddContactCommand parseContact(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
                return new AddContactCommand(new Contact(name, phone, email, address, tagList), false);
            } else {
                return new AddContactCommand(new Contact(name, phone, email, null, tagList), false);
            }
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            return new AddContactCommand(new Contact(name, phone, null, address, tagList), false);
        } else {
            return new AddContactCommand(new Contact(name, phone, null, null, tagList), false);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddDayCommand for a Day and returns an
     * AddDayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddDayCommand parseDay(String args) throws ParseException {
        int numDays = ParserUtil.parseDays(args.trim());
        return new AddDayCommand(numDays, false);
    }
}
