package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.TemplateItem;

/**
 * Adds a template item to the specified template.
 */
public class AddTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food item to the specified template list. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Apples "
            + PREFIX_AMOUNT + "300g";

    public static final String MESSAGE_SUCCESS = "New food item added into template: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food item already exists in the template list";

    private final TemplateItem toAdd;

    /**
     * Creates an AddTemplateItemCommand to add the specified {@code templateItem}
     */
    public AddTemplateItemCommand(TemplateItem templateItem) {
        requireNonNull(templateItem);
        toAdd = templateItem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /**List<TemplateItem> lastShownList = model.getFilteredTemplateItems();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }
        if (model.hasTemplateItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.addTemplateItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));*/

        return new CommandResult("Method not implemented yet");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateItemCommand // instanceof handles nulls
                && toAdd.equals(((AddTemplateItemCommand) other).toAdd));
    }
}
