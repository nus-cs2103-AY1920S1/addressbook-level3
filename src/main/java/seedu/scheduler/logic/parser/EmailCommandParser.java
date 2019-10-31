package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.commands.EmailCommand.TIMESLOT_COMMAND_WORD;

import seedu.scheduler.logic.commands.EmailCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.person.Name;

/**
 * Parses input arguments and creates a new EmailCommand object.
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");

        if (trimmedArgs.isEmpty() || splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        boolean isTimeslotCommand = splitArgs[0].equals(TIMESLOT_COMMAND_WORD);

        if (isTimeslotCommand) {
            return parseTimeslotCommand(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    /**
     * Handles the "timeslot" sub-command in the context of the EmailCommand and returns
     * an EmailCommand object for execution
     * @param args The arguments of the command provided by the user
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parseTimeslotCommand(String args) throws ParseException {
        String nameArg = args.substring(9).trim();
        Name name;

        if (nameArg.length() == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            name = ParserUtil.parseName(nameArg);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new EmailCommand(name);
    }

}
