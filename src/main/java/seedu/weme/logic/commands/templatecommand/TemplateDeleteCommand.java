package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.Template;

/**
 * Deletes a template identified using it's displayed index from Weme.
 */
public class TemplateDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": deletes the template identified by the index number used in the displayed template list.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TEMPLATE_SUCCESS = "Deleted Template: %1$s";

    private final Index targetIndex;

    public TemplateDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Template> lastShownList = model.getFilteredTemplateList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        Template templateToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTemplate(templateToDelete);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_TEMPLATE_SUCCESS, templateToDelete));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TemplateDeleteCommand // instanceof handles nulls
            && targetIndex.equals(((TemplateDeleteCommand) other).targetIndex)); // state check
    }
}

