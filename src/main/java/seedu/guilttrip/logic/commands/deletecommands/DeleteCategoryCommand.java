package seedu.guilttrip.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Category;

/**
 * Deletes a category from guilttrip();
 */
public class DeleteCategoryCommand extends Command {

    public static final String COMMAND_WORD = "deleteCategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the category from guilttrip"
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
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_DELETE_CATEGORY_SUCCESS, targetCategory));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCategoryCommand // instanceof handles nulls
                && targetCategory.equals(((DeleteCategoryCommand) other).targetCategory)); // state check
    }
}
