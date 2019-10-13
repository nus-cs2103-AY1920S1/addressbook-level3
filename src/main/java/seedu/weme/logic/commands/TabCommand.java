package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Switches tab to change parser and user interface.
 */
public class TabCommand extends Command {

    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_SUCCESS = "Switched to %s context.";

    private ModelContext context;

    /**
     * Creates TabCommand to switch to specified context.
     * @param context Context to switch to.
     */
    public TabCommand(ModelContext context) {
        this.context = context;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContext(context);
        return new CommandResult(String.format(MESSAGE_SUCCESS, context.getContextName()));
    }
}
