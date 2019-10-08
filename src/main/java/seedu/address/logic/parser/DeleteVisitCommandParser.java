package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_VISIT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.DeleteVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser class for DeleteVisitCommand
 */
public class DeleteVisitCommandParser implements Parser<DeleteVisitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteVisitCommand}
     * and returns a {@code DeleteVisitCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DELETE_VISIT);

        Index index;
        int reportIdx;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteVisitCommand.MESSAGE_USAGE), ive);
        }

        try {
            reportIdx = Integer.parseInt(argMultimap.getValue(PREFIX_DELETE_VISIT).orElse("-1"));
        } catch (NumberFormatException e) {
            reportIdx = -1;
        }

        return new DeleteVisitCommand(index, reportIdx);
    }
}
