package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.PREFIX_ALL;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import mams.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private boolean showStudents = false;
    private boolean showModules = false;
    private boolean showAppeals = false;
    private boolean showAll = false;

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public ListCommand parse(String args) {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_APPEAL, PREFIX_STUDENT, PREFIX_MODULE);

        boolean showAllPrefixPresent = argMultimap.getValue(PREFIX_ALL).isPresent();

        // if PREFIX_ALL is present, or if no prefixes were specified, default to list all items.
        showAll = showAllPrefixPresent || arePrefixesAbsent(argMultimap, PREFIX_APPEAL,
                PREFIX_MODULE, PREFIX_STUDENT);

        if (argMultimap.getValue(PREFIX_APPEAL).isPresent() || showAll) {
            showAppeals = true;
        }
        if (argMultimap.getValue(PREFIX_MODULE).isPresent() || showAll) {
            showModules = true;
        }
        if (argMultimap.getValue(PREFIX_STUDENT).isPresent() || showAll) {
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
