package seedu.guilttrip.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_EXISTING_ENTRIES_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.model.entry.CategoryList.MESSAGE_CONSTRAINTS_NOT_IN_LIST;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Category;

/**
 * Deletes a category from guiltTrip.
 */
public class DeleteCategoryCommand extends Command {

    public static final String COMMAND_WORD = "deleteCategory";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Deletes the category from GuiltTrip";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
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

    /**
     *  Deletes Category target category to the existing categories. Model will handle the check if the category is
     *  present in the list and if the category has any existing entries.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult the CommandResult for guiltTrip to display to User.
     * @throws CommandException if the category to be deleted does not exist in the list or if the category to be
     * deleted has existing entries
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(targetCategory)) {
            throw new CommandException(String.format(MESSAGE_CONSTRAINTS_NOT_IN_LIST,
                    targetCategory.getCategoryType()));
        }
        if (model.categoryHasAnyEntries(targetCategory)) {
            throw new CommandException(MESSAGE_EXISTING_ENTRIES_CATEGORY);
        }
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
