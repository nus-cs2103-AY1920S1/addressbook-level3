package seedu.billboard.logic.commands;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.billboard.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * Removes tag(s) from existing expense.
 */
public class RemoveTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "rm";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes tags from the expense identified "
            + "by the index number used in the last expense listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "SCHOOL";

    public static final String MESSAGE_RM_TAG_SUCCESS = "Removed tag(s) from Expense: %1$s";

    private final Index index;
    private List<String> tagNames;

    /**
     * Creates an RemoveTagCommand to remove tags from the specified {@code Expense}
     * @param index                 of the expense in the filtered expense list to edit
     * @param tagNames          tags to be removed from expense.
     */
    public RemoveTagCommand(Index index, List<String> tagNames) {
        requireAllNonNull(index, tagNames);
        this.index = index;
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Expense> lastShownList = model.getFilteredExpenses();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToEdit = lastShownList.get(index.getZeroBased());

        Set<Tag> currentTags = expenseToEdit.getTags();
        Set<Tag> tagsToRemove = getExistingTags(currentTags, tagNames);
        Set<Tag> editedTags = getRemainingTags(currentTags, tagsToRemove);
        model.decreaseCount(tagsToRemove);

        Expense editedExpense = new Expense(expenseToEdit.getName(), expenseToEdit.getDescription(),
                expenseToEdit.getAmount(), expenseToEdit.getCreated(), editedTags);

        model.setExpense(expenseToEdit, editedExpense);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);

        return new CommandResult(String.format(MESSAGE_RM_TAG_SUCCESS, editedExpense));
    }

    /**
     * Checks and returns a set consisting of tags whose names exist in the set given in argument.
     * @param current to check names against
     * @param names of tags to be removed.
     * @return set consisting of tags whose names exist in set.
     */
    private Set<Tag> getExistingTags(Set<Tag> current, List<String> names) {
        requireAllNonNull(current, names);
        Set<Tag> toReturn = new HashSet<>();
        for (Tag tag : current) {
            if (names.contains(tag.tagName)) {
                toReturn.add(tag);
            }
        }
        return Collections.unmodifiableSet(toReturn);
    }

    /**
     * Removes all elements of a set from another set.
     * @param current set which elements will be removed from.
     * @param toRemove set whose elements are to be removed.
     * @return set consisting of remaining elements.
     */
    private Set<Tag> getRemainingTags(Set<Tag> current, Set<Tag> toRemove) {
        requireAllNonNull(current, toRemove);
        Set<Tag> toReturn = new HashSet<>(current);
        toReturn.removeAll(toRemove);
        return Collections.unmodifiableSet(toReturn);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && tagNames.equals(((RemoveTagCommand) other).tagNames))
                && index.equals(((RemoveTagCommand) other).index);
    }
}
