package seedu.address.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all template items in the template list to the user.
 */
public class ListTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all template items";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredTemplateItemList(PREDICATE_SHOW_ALL_TEMPLATE_ITEMS);
        //return new CommandResult(MESSAGE_SUCCESS);
        return new CommandResult("Method not implemented yet.");
    }
}
