package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAccommodationCommand;
import seedu.address.logic.commands.AddActivityCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddDayCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        final Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");

        switch(type) {
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
            Contact contact = new Contact(name, phone, null, null, new HashSet<Tag>());
            Accommodation accommodation = new Accommodation(name, address, contact, tagList);
            return new AddAccommodationCommand(accommodation);
        } else {
            Accommodation accommodation = new Accommodation(name, address, null, tagList);
            return new AddAccommodationCommand(accommodation);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddActivityCommand for an Activity
     * and returns an AddActivityCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddActivityCommand parseActivity(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddActivityCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            Contact contact = new Contact(name, phone, null, null, new HashSet<Tag>());
            Activity activity = new Activity(name, address, contact, tagList);
            return new AddActivityCommand(activity);
        } else {
            Activity activity = new Activity(name, address, null, tagList);
            return new AddActivityCommand(activity);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand for a Contact and returns an
     * AddContactCommand object for execution.
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
                return new AddContactCommand(new Contact(name, phone, email, address, tagList));
            } else {
                return new AddContactCommand(new Contact(name, phone, email, null, tagList));
            }
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            return new AddContactCommand(new Contact(name, phone, null, address, tagList));
        } else {
            return new AddContactCommand(new Contact(name, phone, null, null, tagList));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddDayCommand for a Day and returns an
     * AddDayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private AddDayCommand parseDay(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDayCommand.MESSAGE_USAGE));
        }

        int numDays = ParserUtil.parseDays(argMultimap.getValue(PREFIX_DAY).get());
        return new AddDayCommand(numDays);
    }
}
