package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Clears all the templates in Weme.
 */
public class TemplateClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Templates have been cleared!";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": delete all templates in Weme.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.clearTemplates();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }
}
