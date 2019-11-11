package seedu.address.logic.parser.cli;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.MODULE_PATTERN;
import static seedu.address.logic.parser.CliSyntax.SEMESTER_PATTERN;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleParser implements Parser<AddModuleCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePatternsPresent(ArgumentMultimap argumentMultimap, Pattern... patterns) {
        return Stream.of(patterns).allMatch(pattern -> argumentMultimap.getValue(pattern).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE_PATTERN, SEMESTER_PATTERN);

        if (!arePatternsPresent(argMultimap, MODULE_PATTERN, SEMESTER_PATTERN)
                || argMultimap.getNumberOfArgsForPattern(SEMESTER_PATTERN) != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddModuleCommand.MESSAGE_USAGE));
        }

        assert argMultimap.getNumberOfArgsForPattern(MODULE_PATTERN) >= 1;
        assert argMultimap.getNumberOfArgsForPattern(SEMESTER_PATTERN) == 1;

        SemesterName semesterName = ParserUtil.parseSemester(argMultimap.getValue(SEMESTER_PATTERN).get());
        List<String> moduleCodes = argMultimap.getAllValues(MODULE_PATTERN);
        List<String> parsedModuleCodes = new ArrayList<>();
        for (String s : moduleCodes) {
            parsedModuleCodes.add(ParserUtil.parseModule(s));
        }

        return new AddModuleCommand(parsedModuleCodes, semesterName);
    }
}
