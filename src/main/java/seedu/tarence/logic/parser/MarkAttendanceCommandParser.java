package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.stream.Stream;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.MarkAttendanceCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Week;


/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME, PREFIX_TUTORIAL_INDEX, PREFIX_NAME, PREFIX_TUTORIAL_WEEKS);

        ModCode modCode = null;
        TutName tutName = null;
        Index tutIndex = null;
        Name studName = null;
        if (validateModCodeTutNameFormat(argMultimap)) {
            modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
            tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
        } else if (validateIndexFormat(argMultimap)) {
            tutIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TUTORIAL_INDEX).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }

        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_TUTORIAL_WEEKS).get());
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            studName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        return new MarkAttendanceCommand(modCode, tutName, tutIndex, week, studName);
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the first format with modcode and tutorial name,
     * else false.
     */
    public static boolean validateModCodeTutNameFormat(ArgumentMultimap argMultimap) {
        // student name not checked since it is optional
        // modcode, tutorial name present without tutorial index - first format
        return (arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME,
                PREFIX_TUTORIAL_WEEKS)
                && arePrefixesAbsent(argMultimap, PREFIX_TUTORIAL_INDEX));
    }

    /**
     * Checks the argument multimap if it contains the correct combination of arguments.
     * @return True if argument multimap contains the combination for the second format with tutorial index,
     * else false.
     */
    public static boolean validateIndexFormat(ArgumentMultimap argMultimap) {
        // student name not checked since it is optional
        // tutorial index present without modcode or tutorial name - second format
        return (arePrefixesPresent(argMultimap, PREFIX_TUTORIAL_INDEX, PREFIX_TUTORIAL_WEEKS)
                && arePrefixesAbsent(argMultimap, PREFIX_MODULE, PREFIX_TUTORIAL_NAME));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}

