package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEMESTER_PATTERN;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new BlockCurrentSemesterCommand object
 */
public class BlockCurrentSemesterParser implements Parser<BlockCurrentSemesterCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePatternsPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the BlockCurrentSemesterCommand
     * and returns an BlockCurrentSemesterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlockCurrentSemesterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, SEMESTER_PATTERN);
        String[] tokens = args.trim().split(" ");
        String reason = tokens.length >= 2 ? String.join(" ",
                Arrays.copyOfRange(tokens, 1, tokens.length)) : "";
        if (!arePatternsPresent(argMultimap, SEMESTER_PATTERN)
            || argMultimap.getNumberOfArgsForPattern(SEMESTER_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BlockCurrentSemesterCommand.MESSAGE_USAGE));
        }
        SemesterName semesterName = ParserUtil.parseSemester(argMultimap.getValue(SEMESTER_PATTERN).get());

        return new BlockCurrentSemesterCommand(semesterName, reason);
    }
}
