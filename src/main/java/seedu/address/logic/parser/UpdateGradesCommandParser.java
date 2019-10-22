package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateGradesCommand object
 */
public class UpdateGradesCommandParser implements Parser<UpdateGradesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateGradesCommand
     * and returns an UpdateGradesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateGradesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MARKS);

        Index index;
        List<String> marks = new ArrayList<>();

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateGradesCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_MARKS).isPresent()) {
            marks = ParserUtil.parseAssignmentGrades(argMultimap.getValue(PREFIX_MARKS).get());
        }

        return new UpdateGradesCommand(index, marks);
    }


}
