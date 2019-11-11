package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import mams.commons.core.Messages;
import mams.logic.commands.FindCommand;
import mams.logic.parser.exceptions.ParseException;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.StudentContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

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

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Predicate> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent()
                && !argMultimap.getValue(PREFIX_STUDENT).get().isEmpty()) {

            StudentContainsKeywordsPredicate studentPred = new StudentContainsKeywordsPredicate(
                    Arrays.asList(argMultimap.getValue(PREFIX_STUDENT).get().split("\\s+")));
            predicates.add(studentPred);

        }

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()
                && !argMultimap.getValue(PREFIX_MODULE).get().isEmpty()) {

            ModuleContainsKeywordsPredicate modulePred = new ModuleContainsKeywordsPredicate(
                    Arrays.asList(argMultimap.getValue(PREFIX_MODULE).get().split("\\s+")));
            predicates.add(modulePred);

        }

        if (argMultimap.getValue(PREFIX_APPEAL).isPresent()
                && !argMultimap.getValue(PREFIX_APPEAL).get().isEmpty()) {

            AppealContainsKeywordsPredicate appealPred = new AppealContainsKeywordsPredicate(
                    Arrays.asList(argMultimap.getValue(PREFIX_APPEAL).get().split("\\s+")));
            predicates.add(appealPred);

        }

        if (!predicates.isEmpty()) {
            return new FindCommand(predicates);
        } else {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
