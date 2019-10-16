package seedu.address.logic.commands.templateList;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.TemplateList;

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
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
