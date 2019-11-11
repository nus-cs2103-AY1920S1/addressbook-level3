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
 * Adds tag(s) to an existing expense.
 */
public class AddTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds tag(s) to the expense identified "
            + "by the index number used in the last expense listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG] (1 or more)\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "SCHOOL";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to Expense: \n%1$s";

    public static final String MESSAGE_ADD_TAG_FAILURE = "No tag(s) to be added";

    private final Index index;
    private List<String> tagNames;

    /**
     * Creates an AddTagCommand to add tags to the specified {@code Expense}
     * @param index                 of the expense in the filtered expense list to edit
     * @param tagNames              of tags to be added to expense.
     */
    public AddTagCommand(Index index, List<String> tagNames) {
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

        Set<Tag> existingTags = expenseToEdit.getTags();
        Set<Tag> inputTags = model.retrieveTags(tagNames);

        Set<Tag> tagsToIncrementCount = getTagsToIncrement(existingTags, inputTags);

        if (tagsToIncrementCount.isEmpty()) {
            throw new CommandException(MESSAGE_ADD_TAG_FAILURE);
        }

        Set<Tag> editedTags = getEditedTags(existingTags, tagsToIncrementCount);

        model.incrementCount(tagsToIncrementCount);

        Expense editedExpense = new Expense(expenseToEdit.getName(), expenseToEdit.getDescription(),
                expenseToEdit.getAmount(), expenseToEdit.getCreated(), editedTags);

        model.setExpense(expenseToEdit, editedExpense);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedExpense),
                false, false, CommandResult.DEFAULT_LIST_VIEW);
    }

    /**
     * Returns a set of tags whose count needs to be incremented in the TagCountManager by 1.
     * This set contains tags that are input by the user, and do not exist in the existing tag set.
     * @param existingTags      in the expense.
     * @param inputTags         by the user.
     * @return Set of tags whose count are to be incremented in the TagCountManager by 1.
     */
    private Set<Tag> getTagsToIncrement(Set<Tag> existingTags, Set<Tag> inputTags) {
        requireAllNonNull(existingTags, inputTags);
        Set<Tag> existingTagsCopy = new HashSet<>(existingTags);
        Set<Tag> toIncrement = new HashSet<>(inputTags);
        existingTagsCopy.retainAll(toIncrement);
        toIncrement.removeAll(existingTagsCopy);
        return Collections.unmodifiableSet(toIncrement);
    }

    /**
     * Returns set of tags for edited expense by merging existing tag set and set of tags
     * whose count are to be incremented.
     * Tags to be added must not contain any tag from the existing tags set.
     * @param existingTags      in the expense.
     * @param toIncrement       tags whose count are to be incremented.
     * @return unmodifiable edited tag set with unique elements.
     */
    private Set<Tag> getEditedTags(Set<Tag> existingTags, Set<Tag> toIncrement) {
        requireAllNonNull(existingTags, toIncrement);
        Set<Tag> editedTags = new HashSet<>(existingTags);
        editedTags.addAll(toIncrement);
        return Collections.unmodifiableSet(editedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && tagNames.equals(((AddTagCommand) other).tagNames))
                && index.equals(((AddTagCommand) other).index);
    }

}
