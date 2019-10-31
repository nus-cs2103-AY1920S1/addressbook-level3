package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Redo template list state.
 */
public class RedoTemplateCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo template list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoTemplateList()) {
            throw new CommandException("Cannot redo.");
        }

        ReadOnlyTemplateList currTemplateList = model.redoTemplateList();
        model.setTemplateList(currTemplateList);
        if (!(model.getIndex() == -1)) {
            UniqueTemplateItems newTemplate = model.getNewTemplate();
            model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
            model.setShownTemplate(newTemplate);
            model.updateFilteredTemplateToBeShown();
        }
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }
}
