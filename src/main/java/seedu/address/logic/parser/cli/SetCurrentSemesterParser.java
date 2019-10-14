package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new SetCurrentSemesterCommand object
 */
public class SetCurrentSemesterParser implements Parser<SetCurrentSemesterCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SetCurrentSemesterCommand
     * and returns an SetCurrentSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCurrentSemesterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetCurrentSemesterCommand.MESSAGE_USAGE));
        }
        SemesterName semesterName = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());

        return new SetCurrentSemesterCommand(semesterName);
    }

}