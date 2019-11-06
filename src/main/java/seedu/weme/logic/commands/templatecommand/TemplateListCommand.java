package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Lists all memes in Weme to the user.
 */
public class TemplateListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all templates";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": list all templates.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
