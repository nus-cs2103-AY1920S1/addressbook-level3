package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_INDEX;


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
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "tlist template " + COMMAND_WORD
            + ": Deletes the template item identified by the index number used in the displayed template list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ITEM_INDEX + "ITEMINDEX\n"
            + "Example: tlist template " + COMMAND_WORD + " 1 " + PREFIX_ITEM_INDEX + " 1 ";

    public static final String MESSAGE_SUCCESS = "Deleted TemplateList Item: %1$s";

    private final Index targetTemplateIndex;
    private final Index targetItemIndex;

    public DeleteTemplateItemCommand(Index targetTemplateIndex, Index targetItemIndex) {
        this.targetTemplateIndex = targetTemplateIndex;
        this.targetItemIndex = targetItemIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        // Check that the template index is valid
        if (targetTemplateIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }
        UniqueTemplateItems templateToEdit = lastShownList.get(targetTemplateIndex.getZeroBased());
        if (targetItemIndex.getZeroBased() >= templateToEdit.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        TemplateItem itemToDelete = templateToEdit.get(targetItemIndex.getZeroBased());
        templateToEdit.remove(itemToDelete);

        model.setShownTemplate(templateToEdit);
        model.updateFilteredTemplateToBeShown();
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, itemToDelete));
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTemplateItemCommand // instanceof handles nulls
                && targetTemplateIndex.equals(((DeleteTemplateItemCommand) other).targetTemplateIndex)
                && targetItemIndex.equals(((DeleteTemplateItemCommand) other).targetItemIndex)); // state check
    }
}
