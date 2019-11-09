package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Expense;
import seedu.address.model.person.Person;

/**
 * Deletes the contact identified by its display index from SplitWiser.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_IN_THE_WRONG_CONTEXT = "Unable to delete: not in list contact/activity "
            + "or view activity context.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the current item identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETION_SUCCESS = "Successfully deleted %s.";

    public static final String MESSAGE_SOFT_DELETE_SUCCESS = "Soft deleted the specified expense at index: %s.";

    public static final String MESSAGE_PERSON_INVOLVED_ACTIVITY = "%s is still involved in an activity!";

    public static final String MESSAGE_EXPENSE_ALREADY_DELETED = "Expense at index: %s has already been soft-deleted.";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ContextType context = model.getContext().getType();

        switch (context) {
        case LIST_CONTACT: // delete contact
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            if (model.getActivityBook().hasPerson(personToDelete.getPrimaryKey())) {
                throw new CommandException(String.format(MESSAGE_PERSON_INVOLVED_ACTIVITY, personToDelete.getName()));
            }

            Context listContact = Context.newListContactContext();
            model.deletePerson(personToDelete);
            model.setContext(listContact);

            return new CommandResult(String.format(MESSAGE_DELETION_SUCCESS, personToDelete),
                    listContact);

        case LIST_ACTIVITY: // delete activity
            List<Activity> activityList = model.getFilteredActivityList();

            if (targetIndex.getZeroBased() >= activityList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
            }

            Activity activityToDelete = activityList.get(targetIndex.getZeroBased());
            Context listActivity = Context.newListActivityContext();
            model.deleteActivity(activityToDelete);
            model.setContext(listActivity);

            return new CommandResult(String.format(MESSAGE_DELETION_SUCCESS, activityToDelete),
                    listActivity);

        case VIEW_ACTIVITY: // delete expense in an activity
            Activity activity = model.getContext().getActivity().get();

            if (targetIndex.getZeroBased() >= activity.getExpenses().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
            }

            Expense expense = activity.getExpenses().get(targetIndex.getZeroBased()); // soft deletes the expense
            if (expense.isDeleted()) {
                throw new CommandException(String.format(MESSAGE_EXPENSE_ALREADY_DELETED, targetIndex.getOneBased()));
            }
            activity.deleteExpense(targetIndex.getZeroBased());
            Context thisActivity = new Context(activity);
            model.setContext(thisActivity);

            return new CommandResult(String.format(MESSAGE_SOFT_DELETE_SUCCESS, targetIndex.getOneBased()),
                    thisActivity);

        default:
            throw new CommandException(MESSAGE_IN_THE_WRONG_CONTEXT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
