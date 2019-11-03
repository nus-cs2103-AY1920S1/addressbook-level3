package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.EmailCommand.ALL_TIMESLOTS_COMMAND_WORD;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_USAGE;
import static seedu.scheduler.logic.commands.EmailCommand.STATUS_COMMAND_WORD;
import static seedu.scheduler.logic.commands.EmailCommand.TIMESLOT_COMMAND_WORD;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_COMMAND_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NAME;

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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMMAND_TYPE);

        if (!argumentMultimap.getValue(PREFIX_COMMAND_TYPE).isPresent() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String commandType = argumentMultimap.getValue(PREFIX_COMMAND_TYPE).get();

        if (commandType.equals(TIMESLOT_COMMAND_WORD)) {
            return this.parseTimeslotCommand(argumentMultimap);
        } else if (commandType.equals(ALL_TIMESLOTS_COMMAND_WORD)) {
            return new EmailCommand(ALL_TIMESLOTS_COMMAND_WORD);
        } else if (commandType.equals(STATUS_COMMAND_WORD)) {
            return new EmailCommand(STATUS_COMMAND_WORD);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    /**
     * Handles the "timeslot" sub-command in the context of the EmailCommand and returns
     * an EmailCommand object for execution
     * @param args The arguments of the command provided by the user
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parseTimeslotCommand(ArgumentMultimap args) throws ParseException {
        if (!args.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(args.getValue(PREFIX_NAME).get());

        return new EmailCommand(TIMESLOT_COMMAND_WORD, name);
    }

}
