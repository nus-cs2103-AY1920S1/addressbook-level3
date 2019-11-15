package seedu.guilttrip.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;


/**
 * Adds a Category to guiltTrip.
 */
public class AddCategoryCommand extends Command {

    public static final String COMMAND_WORD = "addCategory";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Adds a category to the finance tracker. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY TYPE "
            + PREFIX_DESC + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Expense "
            + PREFIX_DESC + "Spicy Food ";

    public static final String MESSAGE_SUCCESS = "New category added: %1$s";

    private final Category toAdd;

    /**
     * Creates an AddCommand to add the specified {@code category}
     */
    public AddCategoryCommand(Category category) {
        requireNonNull(category);
        toAdd = category;
    }

    /**
     * Adds Category toAdd to the existing categories. Model will handle the check if the category is
     * already present in the list.
     *
     * @param model the model to carry out commands on.
     * @return CommandResult the CommandResult for guiltTrip to display to User.
     * @throws CommandException if the category to be added already exists in the list.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasCategory(toAdd)) {
            throw new CommandException(String.format(CategoryList.MESSAGE_CONSTRAINTS_IN_LIST,
                    toAdd.getCategoryType()));
        }
        model.addCategory(toAdd);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCategoryCommand // instanceof handles nulls
                && toAdd.equals(((AddCategoryCommand) other).toAdd));
    }

}

