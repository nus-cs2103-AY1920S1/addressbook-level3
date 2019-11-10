package unrealunity.visit.logic.parser;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_EDIT_VISIT;

import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.commons.exceptions.IllegalValueException;
import unrealunity.visit.logic.commands.EditVisitCommand;
import unrealunity.visit.logic.parser.exceptions.ParseException;

/**
 * Parser class for DeleteVisitCommand
 */
public class EditVisitCommandParser implements Parser<EditVisitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditVisitCommandParser}
     * and returns a {@code EditVisitCommandParser} object for execution.
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
            String input = argMultimap.getValue(PREFIX_EDIT_VISIT).orElse(ParserUtil.EMPTY_REPORT_INDEX);
            reportIdx = ParserUtil.parseVisitReportIndex(input);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVisitCommand.MESSAGE_USAGE), e);
        }

        return new EditVisitCommand(index, reportIdx);
    }
}

