package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;

/**
 * Lists all templates in the template list to the user.
 */
public class ListTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all templates";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS));
        commandResult.setTemplateListCommand();

        return commandResult;
    }
}
