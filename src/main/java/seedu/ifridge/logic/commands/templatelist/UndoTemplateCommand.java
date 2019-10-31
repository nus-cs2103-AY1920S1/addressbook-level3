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
 * Undo template list state.
 */
public class UndoTemplateCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo template list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoTemplateList()) {
            throw new CommandException("Cannot undo.");
        }

        if (!(model.getIndex() == -1)) {
            UniqueTemplateItems prevTemplate = model.getPrevTemplate();
            model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
            model.setShownTemplate(prevTemplate);
            model.updateFilteredTemplateToBeShown();
        }
        ReadOnlyTemplateList currTemplateList = model.undoTemplateList();
        model.setTemplateList(currTemplateList);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }
}
