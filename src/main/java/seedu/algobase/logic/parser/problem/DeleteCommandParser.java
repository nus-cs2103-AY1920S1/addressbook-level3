package seedu.algobase.logic.parser.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.FLAG_FORCE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.problem.DeleteCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, FLAG_FORCE);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            boolean isForced = argMultimap.getValue(FLAG_FORCE).isPresent();
            return new DeleteCommand(index, isForced);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
