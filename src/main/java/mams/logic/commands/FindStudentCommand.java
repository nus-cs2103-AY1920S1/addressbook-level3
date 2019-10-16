package mams.logic.commands;

import mams.commons.core.Messages;
import mams.model.Model;
import mams.model.student.NameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

public class FindStudentCommand extends FindCommand {

    private NameContainsKeywordsPredicate studentPredicate;

    public FindStudentCommand(NameContainsKeywordsPredicate predicate) {
        this.studentPredicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!studentPredicate.isEmpty()) {
         model.updateFilteredStudentList(studentPredicate);
         return new CommandResult(
         String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
        } else {
            return new CommandResult("error");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindStudentCommand // instanceof handles nulls
                && studentPredicate.equals(((FindStudentCommand) other).studentPredicate)); // state check
    }
}
