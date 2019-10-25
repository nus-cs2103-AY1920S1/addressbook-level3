package seedu.ifridge.logic.commands.templatelist.template;

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
 * Lists all template items in the template list to the user.
 */
public class ListTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all template items in %1$s";
    public static final String MESSAGE_USAGE = "tlist template " + COMMAND_WORD
            + ": Lists the template items in the specified template list\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: tlist template " + COMMAND_WORD + " 1 ";

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

        model.setShownTemplate(targetTemplate);
        model.updateFilteredTemplateToBeShown();
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, targetTemplate.getName()));
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }
}
