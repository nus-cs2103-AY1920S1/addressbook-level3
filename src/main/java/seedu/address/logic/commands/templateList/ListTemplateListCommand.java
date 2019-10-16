package seedu.address.logic.commands.templateList;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all templates in the template list to the user.
 */
public class ListTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all templates";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        //return new CommandResult(MESSAGE_SUCCESS);
        return new CommandResult("Method not implemented yet.");
    }
}
