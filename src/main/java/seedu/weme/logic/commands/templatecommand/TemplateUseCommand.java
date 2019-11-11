package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.template.Template;

/**
 * Chooses a template to be used for meme creation.
 */
public class TemplateUseCommand extends Command {

    public static final String COMMAND_WORD = "use";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": chooses a template and starts meme creation in the create tab.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USE_TEMPLATE_SUCCESS = "Started creating meme with template: %1$s";
    public static final String MESSAGE_USE_TEMPLATE_FAILED_TO_START_SESSION = "Failed to start a meme"
            + "creation session.";

    private final Index index;

    /**
     * @param index of the template in the filtered template list to use
     */
    public TemplateUseCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Template> lastShownList = model.getFilteredTemplateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        Template templateToUse = lastShownList.get(index.getZeroBased());

        try {
            model.startMemeCreation(templateToUse);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_USE_TEMPLATE_FAILED_TO_START_SESSION, ioe);
        }
        model.setContext(ModelContext.CONTEXT_CREATE);
        CommandResult result = new CommandResult(String.format(MESSAGE_USE_TEMPLATE_SUCCESS, templateToUse));
        model.commitWeme(result.getFeedbackToUser());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateUseCommand)) {
            return false;
        }

        // state check
        TemplateUseCommand e = (TemplateUseCommand) other;
        return index.equals(e.index);
    }
}

