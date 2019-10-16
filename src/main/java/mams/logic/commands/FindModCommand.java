package mams.logic.commands;

import mams.commons.core.Messages;
import mams.model.Model;
import mams.model.module.ModuleContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class FindModCommand extends FindCommand {

    private ModuleContainsKeywordsPredicate modulePredicate;

    public FindModCommand(ModuleContainsKeywordsPredicate predicate) {
        this.modulePredicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!modulePredicate.isEmpty()) {
            model.updateFilteredModuleList(modulePredicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
        } else {
            return new CommandResult("error");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModCommand // instanceof handles nulls
                && modulePredicate.equals(((FindModCommand) other).modulePredicate)); // state check
    }
}
