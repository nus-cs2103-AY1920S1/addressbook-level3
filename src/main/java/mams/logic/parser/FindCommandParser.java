package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEALID;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.List;

import mams.commons.core.Messages;
import mams.logic.commands.FindCommand;
import mams.logic.commands.FindModCommand;
import mams.logic.commands.FindStudentCommand;
import mams.logic.parser.exceptions.ParseException;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.NameContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private List<String> studentKeywords = new ArrayList<>();
    private List<String> moduleKeywords = new ArrayList<>();

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE_CODE, PREFIX_APPEALID);

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
            studentKeywords = argMultimap.getAllValues(PREFIX_STUDENT);
            return new FindStudentCommand(new NameContainsKeywordsPredicate(studentKeywords));
        }

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            moduleKeywords = argMultimap.getAllValues(PREFIX_MODULE_CODE);
            return new FindModCommand(new ModuleContainsKeywordsPredicate(moduleKeywords));
        }

        // for compilation
        return new FindStudentCommand(new NameContainsKeywordsPredicate(studentKeywords));
    }

}
