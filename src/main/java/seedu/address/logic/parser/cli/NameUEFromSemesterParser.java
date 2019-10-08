package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new NameUEFromSemesterCommand object
 */
public class NameUEFromSemesterParser implements Parser<NameUEFromSemesterCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the NameUEFromSemesterCommand
     * and returns an NameUEFromSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NameUEFromSemesterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER);
        String[] tokens = args.split(" ");
        if (!arePrefixesPresent(argMultimap, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()
                || tokens.length != 2
                || tokens[1].isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NameUEFromSemesterCommand.MESSAGE_USAGE));
        }
        SemesterName semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
        String ueName = tokens[1].trim();
        return new NameUEFromSemesterCommand(semester, ueName);
    }

}