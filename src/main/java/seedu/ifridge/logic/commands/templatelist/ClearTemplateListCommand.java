package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.TemplateList;

/**
 * Clears the template list.
 */
public class ClearTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TemplateList has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTemplateList(new TemplateList());

        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setTemplateListCommand();

        return commandResult;
    }
}
