package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "tlist " + COMMAND_WORD
            + ": Deletes the template  identified by the index number used in the displayed template list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: tlist " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted Template: %1$s";

    private final Index targetIndex;

    public DeleteTemplateListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        UniqueTemplateItems templateToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTemplate(templateToDelete);

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, templateToDelete));
        commandResult.setTemplateListCommand();

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTemplateListCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTemplateListCommand) other).targetIndex)); // state check
    }
}
