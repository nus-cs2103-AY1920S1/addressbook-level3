package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the template item identified by the index number used in the displayed template list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TEMPLATE_ITEM_SUCCESS = "Deleted TemplateList Item: %1$s";

    private final Index targetIndex;

    public DeleteTemplateItemCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        /**List<TemplateItem> lastShownList = model.getFilteredTemplateItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        TemplateItem foodToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTemplateItem(foodToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TEMPLATE_ITEM_SUCCESS, foodToDelete));**/
        return new CommandResult("Method not implemented yet.");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTemplateItemCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTemplateItemCommand) other).targetIndex)); // state check
    }
}
