package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import mams.commons.core.Messages;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.appeal.AppealContainsKeywordsPredicate;
import mams.model.module.Module;
import mams.model.module.ModuleContainsKeywordsPredicate;
import mams.model.student.NameContainsKeywordsPredicate;
import mams.model.student.Student;

/**
 * Finds and lists all students in MAMS whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";


    private Predicate[] preds;

    public FindCommand (Predicate... predicate) {
        requireNonNull(predicate);
        this.preds = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Module> lastShownModList = model.getFilteredModuleList();
        List<Appeal> lastShownAppealList = model.getFilteredAppealList();

        for (Predicate pred : preds) {
            if (pred instanceof ModuleContainsKeywordsPredicate
                    && !((ModuleContainsKeywordsPredicate) pred).isEmpty()) {
                model.updateFilteredModuleList(pred);
                return new CommandResult(
                        String.format(Messages.MESSAGE_MODULES_LISTED_OVERVIEW, model.getFilteredModuleList().size()));
            }

            if (pred instanceof NameContainsKeywordsPredicate
                    && !((NameContainsKeywordsPredicate) pred).isEmpty()) {
                model.updateFilteredStudentList(pred);
                return new CommandResult(
                        String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
            }

            if (pred instanceof AppealContainsKeywordsPredicate
                    && !((AppealContainsKeywordsPredicate) pred).isEmpty()) {
                model.updateFilteredAppealList(pred);
                return new CommandResult(
                        String.format(Messages.MESSAGE_APPEALS_LISTED_OVERVIEW, model.getFilteredAppealList().size()));
            }
        }

        return new CommandResult("error");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && preds.equals(((FindCommand) other).preds)); // state check
    }
}
