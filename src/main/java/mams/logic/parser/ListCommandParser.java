package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import mams.logic.commands.ListCommand;
import mams.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    // TODO standardize prefixes with rest of the group. leaving these here for now.
    public static final Prefix PREFIX_MODS = new Prefix("mod/");
    public static final Prefix PREFIX_STUDENTS = new Prefix("stu/");
    public static final Prefix PREFIX_APPEALS = new Prefix("app/");
    public static final Prefix PREFIX_ALL = new Prefix("all/");

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
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_APPEALS, PREFIX_STUDENTS, PREFIX_MODS);

        boolean showAllPrefixPresent = argMultimap.getValue(PREFIX_ALL).isPresent();

        // if show all prefix is present, or if no prefixes were specified, default to list all items.
        showAll = showAllPrefixPresent || arePrefixesAbsent(argMultimap, PREFIX_APPEALS,
                PREFIX_MODS, PREFIX_STUDENTS);

        if (argMultimap.getValue(PREFIX_APPEALS).isPresent() || showAll) {
            showAppeals = true;
        }
        if (argMultimap.getValue(PREFIX_MODS).isPresent() || showAll) {
            showModules = true;
        }
        if (argMultimap.getValue(PREFIX_STUDENTS).isPresent() || showAll) {
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
