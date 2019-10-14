package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKS;

import java.util.stream.Stream;

import seedu.tarence.logic.commands.ExportAttendanceCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Week;


/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class ExportAttendanceCommandParser implements Parser<ExportAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public ExportAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_MODULE, PREFIX_TUTORIAL_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE,
                PREFIX_TUTORIAL_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportAttendanceCommand.MESSAGE_USAGE));
        }

        ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
        TutName tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());

        if (argMultimap.getValue(PREFIX_FILE).isPresent()) {
            String fileName = argMultimap.getValue(PREFIX_FILE).get();
            return new ExportAttendanceCommand(modCode, tutName, fileName);
        } else {
            return new ExportAttendanceCommand(modCode, tutName);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

