package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new MoveSemesterAcrossStudyPlansCommand object
 */
public class MoveSemesterAcrossStudyPlansCommandParser implements Parser<MoveSemesterAcrossStudyPlansCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the MoveSemesterAcrossStudyPlansCommand
     * and returns an MoveSemesterAcrossStudyPlansCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoveSemesterAcrossStudyPlansCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER);
        String[] argTokens = args.trim().split(" ");

        if (!arePrefixesPresent(argMultimap, PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()
                || argTokens.length != 3
                || !argTokens[1].matches("-?\\d+")
                || !argTokens[2].matches("-?\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MoveSemesterAcrossStudyPlansCommand.MESSAGE_USAGE));
        }
        int sourceSemester = Integer.parseInt(argTokens[1]);
        int destSemester = Integer.parseInt(argTokens[2]);
        SemesterName semesterName = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
        return new MoveSemesterAcrossStudyPlansCommand(semesterName, sourceSemester, destSemester);
    }

}