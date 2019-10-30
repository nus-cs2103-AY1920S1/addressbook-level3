package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Category;

/**
 * Deletes a category from guiltTrip();
 */
public class DeleteCategoryCommand extends Command {

    public static final String COMMAND_WORD = "deleteCategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the category from guiltTrip"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY TYPE "
            + PREFIX_DESC + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Expense "
            + PREFIX_DESC + "Spicy Food ";

    public static final String MESSAGE_DELETE_CATEGORY_SUCCESS = "Deleted Category: %1$s";

    private final Category targetCategory;

    public DeleteCategoryCommand(Category category) {
        this.targetCategory = category;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.deleteCategory(targetCategory);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_CATEGORY_SUCCESS, targetCategory));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCategoryCommand // instanceof handles nulls
                && targetCategory.equals(((DeleteCategoryCommand) other).targetCategory)); // state check
    }
}
