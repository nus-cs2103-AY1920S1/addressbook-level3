package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeleteTrainingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        DeleteCommand deleteCommand = null;
        if (args.contains(FLAG_PERSON.toString())) {
            deleteCommand = parsePerson(trimmedArgs);
        } else if (args.contains(FLAG_TRAINING.toString())) {
            deleteCommand = parseTraining(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return deleteCommand;
    }

    /**
     * Creates a DeletePersonCommand object if the flag given is for a person.
     */
    public DeletePersonCommand parsePerson(String args) throws ParseException {
        args = args.replaceAll(FLAG_PERSON.toString(), "");
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a DeleteTrainingCommand object if the flag given is for a training.
     */
    public DeleteTrainingCommand parseTraining(String args) throws ParseException {
        args = args.replaceAll(FLAG_TRAINING.toString(), "");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (!isPrefixPresent(argMultimap, PREFIX_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTrainingCommand.MESSAGE_USAGE));
        }
        AthletickDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        return new DeleteTrainingCommand(date);
    }

    /**
     * Returns true if prefixes doesn't contains empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
