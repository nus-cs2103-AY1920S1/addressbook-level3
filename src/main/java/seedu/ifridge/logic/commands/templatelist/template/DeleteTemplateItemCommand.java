package seedu.ifridge.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_ITEM_INDEX;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

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
        UniqueTemplateItems editedTemplate = templateToEdit;

        if (targetItemIndex.getZeroBased() >= templateToEdit.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
        }
        TemplateItem itemToDelete = templateToEdit.get(targetItemIndex.getZeroBased());
        editedTemplate.remove(itemToDelete);

        model.setTemplate(templateToEdit, editedTemplate);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        model.setShownTemplate(editedTemplate);
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
