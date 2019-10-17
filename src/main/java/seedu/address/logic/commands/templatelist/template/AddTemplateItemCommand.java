package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;

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
    private final Index index;

    /**
     * Creates an AddTemplateItemCommand to add the specified {@code templateItem}
     */
    public AddTemplateItemCommand(Index index, TemplateItem templateItem) {
        requireNonNull(index);
        requireNonNull(templateItem);
        this.toAdd = templateItem;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        // Check that the template index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }
        UniqueTemplateItems templateToEdit = lastShownList.get(index.getZeroBased());

        if (templateToEdit.contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        templateToEdit.add(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateItemCommand // instanceof handles nulls
                && toAdd.equals(((AddTemplateItemCommand) other).toAdd));
    }
}
