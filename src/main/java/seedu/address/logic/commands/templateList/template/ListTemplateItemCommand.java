package seedu.address.logic.commands.templateList.template;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * Lists all template items in the template list to the user.
 */
public class ListTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all template items";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the template items in the specified "
            + "template list\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetTemplateIndex;

    /**
     *
     * @param targetTemplateIndex of the target template to be listed
     */
    public ListTemplateItemCommand(Index targetTemplateIndex) {
        requireNonNull(targetTemplateIndex);
        this.targetTemplateIndex = targetTemplateIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        if (targetTemplateIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }
        UniqueTemplateItems targetTemplate = lastShownList.get(targetTemplateIndex.getZeroBased());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
