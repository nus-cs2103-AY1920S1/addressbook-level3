package mams.logic.commands;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;
import java.util.function.Predicate;

import mams.commons.core.Messages;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.StudentContainsKeywordsPredicate;

/**
 * Finds and lists all students in MAMS whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all objects in the specified list that contain  "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..."
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_APPEAL + "add"
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_MODULE + "algorithm"
            + "\nor " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "bob alice";

    public static final String MESSAGE_EMPTY_KEYWORD = "Please enter a keyword to search.";

    private List<Predicate> preds;

    public FindCommand (List<Predicate> predicates) {
        requireNonNull(predicates);
        this.preds = predicates;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) {
        requireNonNull(model);

        StringBuilder response = new StringBuilder("");

        for (Predicate pred : preds) {

            if (pred instanceof AppealContainsKeywordsPredicate
                    && ((AppealContainsKeywordsPredicate) pred).getListSize() != 0) {
                model.updateFilteredAppealList(pred);
                response.append(String.format(Messages.MESSAGE_APPEALS_LISTED_OVERVIEW,
                        model.getFilteredAppealList().size()));
                response.append("\n");
            }

            if (pred instanceof ModuleContainsKeywordsPredicate
                    && ((ModuleContainsKeywordsPredicate) pred).getListSize() != 0) {
                model.updateFilteredModuleList(pred);
                response.append(String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW,
                        model.getFilteredModuleList().size()));
                response.append("\n");
            }

            if (pred instanceof StudentContainsKeywordsPredicate
                    && ((StudentContainsKeywordsPredicate) pred).getListSize() != 0) {
                model.updateFilteredStudentList(pred);
                response.append(String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                        model.getFilteredStudentList().size()));
                response.append("\n");
            }

        }
        return new CommandResult(response.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && preds.equals(preds)); // state check
    }
}
