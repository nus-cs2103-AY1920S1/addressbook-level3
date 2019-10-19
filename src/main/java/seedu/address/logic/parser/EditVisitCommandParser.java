package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditVisitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_VISIT;

/**
 * Parser class for DeleteVisitCommand
 */
public class EditVisitCommandParser implements Parser<EditVisitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteVisitCommand}
     * and returns a {@code DeleteVisitCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditVisitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EDIT_VISIT);

        Index index;
        int reportIdx;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVisitCommand.MESSAGE_USAGE), ive);
        }

        try {
            reportIdx = Integer.parseInt(argMultimap.getValue(PREFIX_EDIT_VISIT).orElse("-1"));
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVisitCommand.MESSAGE_USAGE), e);
        }

        return new EditVisitCommand(index, reportIdx);
    }
}

