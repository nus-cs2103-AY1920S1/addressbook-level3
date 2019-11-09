package seedu.billboard.logic.commands;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

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

//@@author waifonglee
/**
 * Removes tag(s) from existing expense.
 */
public class RemoveTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "rm";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes tag(s) from the expense identified "
            + "by the index number used in the last expense listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG] (1 or more)\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "SCHOOL";

    public static final String MESSAGE_RM_TAG_SUCCESS = "Removed tag(s) from Expense: %1$s";

    public static final String MESSAGE_RM_TAG_FAILURE = "No tag(s) to be removed";

    private final Index index;
    private List<String> tagNames;

    /**
     * Creates a RemoveTagCommand to remove tags from the specified {@code Expense}
     * @param index             of the expense in the filtered expense list to edit
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
        Set<Tag> tagsToRemove = getExisting(currentTags, tagNames);

        if (tagsToRemove.isEmpty()) {
            throw new CommandException(MESSAGE_RM_TAG_FAILURE);
        }

        Set<Tag> editedTags = getEditedTags(currentTags, tagsToRemove);
        model.decreaseCount(tagsToRemove);

        Expense editedExpense = new Expense(expenseToEdit.getName(), expenseToEdit.getDescription(),
                expenseToEdit.getAmount(), expenseToEdit.getCreated(), editedTags);

        model.setExpense(expenseToEdit, editedExpense);

        return new CommandResult(String.format(MESSAGE_RM_TAG_SUCCESS, editedExpense),
                false, false, CommandResult.DEFAULT_LIST_VIEW);
    }

    /**
     * Checks and returns a set consisting of tags whose names exist in the existing tags set.
     * @param existingTags  in the expense
     * @param inputNames    of tags input by user.
     * @return unmodifiable set consisting of tags whose names exist in existing set of tags.
     */
    private Set<Tag> getExisting(Set<Tag> existingTags, List<String> inputNames) {
        requireAllNonNull(existingTags, inputNames);
        Set<Tag> toReturn = new HashSet<>();
        for (Tag tag : existingTags) {
            if (inputNames.contains(tag.tagName)) {
                toReturn.add(tag);
            }
        }
        return Collections.unmodifiableSet(toReturn);
    }

    /**
     * Removes tags from existing set of tags in expense.
     * Tags in set to be removed must exist in existing tags set.
     * @param existingTags      in expense.
     * @param toRemove          tag set to be removed.
     * @return unmodifiable set consisting of remaining elements.
     */
    private Set<Tag> getEditedTags(Set<Tag> existingTags, Set<Tag> toRemove) {
        requireAllNonNull(existingTags, toRemove);
        Set<Tag> toReturn = new HashSet<>(existingTags);
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
