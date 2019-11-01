package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.PARAM_APPEAL;
import static mams.logic.parser.CliSyntax.PARAM_MODULE;
import static mams.logic.parser.CliSyntax.PARAM_STUDENT;

import java.util.stream.Stream;

import mams.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    @Override
    public ListCommand parse(String args) {
        requireNonNull(args);

        boolean showStudents = false;
        boolean showModules = false;
        boolean showAppeals = false;
        boolean showAll;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PARAM_APPEAL, PARAM_MODULE, PARAM_STUDENT);

        // if no prefixes were specified, default to list all items.
        showAll = arePrefixesAbsent(argMultimap, PARAM_APPEAL, PARAM_STUDENT, PARAM_MODULE);

        if (argMultimap.getValue(PARAM_APPEAL).isPresent() || showAll) {
            showAppeals = true;
        }
        if (argMultimap.getValue(PARAM_MODULE).isPresent() || showAll) {
            showModules = true;
        }
        if (argMultimap.getValue(PARAM_STUDENT).isPresent() || showAll) {
            showStudents = true;
        }

        return new ListCommand(showAppeals, showModules, showStudents);
    }

    /**
     * Returns true if all of the prefixes are not present in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
