package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.performance.Event;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_INVALID_FLAG = "%1$s is not a valid flag.\n";
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        DeleteCommand deleteCommand;
        String flag = getFlag(trimmedArgs);
        if (flag.equals(FLAG_PERSON.toString())) {
            deleteCommand = parsePerson(trimmedArgs);
        } else if (flag.equals(FLAG_EVENT.toString())) {
            deleteCommand = parseEvent(trimmedArgs);
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return deleteCommand;
    }

    /**
     * Extracts the valid flag from the argument string.
     */
    private String getFlag(String args) throws ParseException {
        String[] stringArray = args.split("\\s+", 2);
        String flag = stringArray[0].trim();
        if (flag.length() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (!Flag.isValidFlag(flag)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(MESSAGE_INVALID_FLAG, flag) + Flag.MESSAGE_INVALID_FLAG));
        }
        assert(Flag.isValidFlag(flag));
        return flag;
    }

    /**
     * Creates a DeletePersonCommand object if the flag given is for a person.
     */
    public DeletePersonCommand parsePerson(String args) throws ParseException {
        args = args.replaceAll(FLAG_PERSON.toString(), "");
        if (args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_NO_PERSON_GIVEN));
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a DeleteEventCommand object if the flag given is for an event.
     */
    public DeleteEventCommand parseEvent(String args) throws ParseException {
        args = args.replaceAll(FLAG_EVENT.toString(), "");
        if (args.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_NO_EVENT_GIVEN));
        }
        try {
            String eventName = ParserUtil.parseEvent(args);
            Event event = new Event(eventName);
            return new DeleteEventCommand(event);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }
}
