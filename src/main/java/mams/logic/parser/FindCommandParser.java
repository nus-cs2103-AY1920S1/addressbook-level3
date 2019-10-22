package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import mams.commons.core.Messages;
import mams.logic.commands.FindCommand;
import mams.logic.parser.exceptions.ParseException;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.NameContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private List<Predicate> predicates = new ArrayList<>();

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
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT, PREFIX_MODULE, PREFIX_APPEAL);

        NameContainsKeywordsPredicate studentPred = new NameContainsKeywordsPredicate(
                argMultimap.getAllValues(PREFIX_STUDENT));
        ModuleContainsKeywordsPredicate modulePred = new ModuleContainsKeywordsPredicate(
                argMultimap.getAllValues(PREFIX_MODULE));
        AppealContainsKeywordsPredicate appealPred = new AppealContainsKeywordsPredicate(
                argMultimap.getAllValues(PREFIX_APPEAL));

        if (args.contains(PREFIX_STUDENT.toString()) && studentPred.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_EMPTY_KEYWORD);
        } else {
            predicates.add(studentPred);
        }

        if (args.contains(PREFIX_MODULE.toString()) && modulePred.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_EMPTY_KEYWORD);
        } else {
            predicates.add(modulePred);
        }

        if (args.contains(PREFIX_APPEAL.toString()) && appealPred.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_EMPTY_KEYWORD);
        } else {
            predicates.add(appealPred);
        }

        return new FindCommand(predicates);
    }



}
