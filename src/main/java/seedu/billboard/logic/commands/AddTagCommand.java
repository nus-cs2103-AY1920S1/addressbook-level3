package seedu.billboard.logic.commands;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.billboard.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

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
 * Adds tag(s) to an existing expense.
 */
public class AddTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds tags to the expense identified "
            + "by the index number used in the last expense listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "[TAG]\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "SCHOOL";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to Expense: %1$s";

    private final Index index;
    private List<String> tagNames;

    /**
     * Creates an AddTagCommand to add tags to the specified {@code Expense}
     * @param index                 of the expense in the filtered expense list to edit
     * @param tagNames          tags to be added to expense.
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
        Set<Tag> currTags = expenseToEdit.getTags();
        Set<Tag> toAdd = model.retrieveTags(tagNames);
        Set<Tag> mergedSet = getUniqueSet(currTags, toAdd);
        Expense editedExpense = new Expense(expenseToEdit.getName(), expenseToEdit.getDescription(),
                expenseToEdit.getAmount(), expenseToEdit.getCreated(), mergedSet);

        model.setExpense(expenseToEdit, editedExpense);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedExpense));
    }

    /**
     * Merge 2 sets into 1.
     * @param setOne first set.
     * @param setTwo second set.
     * @return Merged set.
     */
    private Set<Tag> getUniqueSet(Set<Tag> setOne, Set<Tag> setTwo) {
        Set<Tag> toReturn = new HashSet<>();
        toReturn.addAll(setOne);
        toReturn.addAll(setTwo);
        return toReturn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && tagNames.equals(((AddTagCommand) other).tagNames))
                && index.equals(((AddTagCommand) other).index);
    }

}
