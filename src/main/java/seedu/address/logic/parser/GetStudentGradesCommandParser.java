package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GetStudentGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author weikiat97
/**
 * Parses input arguments and creates a new GetStudentGradesCommand object
 */
public class GetStudentGradesCommandParser implements Parser<GetStudentGradesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStudentGradesCommand
     * and returns a GetStudentGradesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetStudentGradesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT);
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("undone")) {
            return new GetStudentGradesCommand();
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetStudentGradesCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());

        return new GetStudentGradesCommand(index);
    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
