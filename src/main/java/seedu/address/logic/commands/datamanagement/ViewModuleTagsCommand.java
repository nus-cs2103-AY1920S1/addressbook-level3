package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.ResultViewType;

/**
 * Shows all tags attached to a specific module.
 */
public class ViewModuleTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewtags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags attached to a specific module. "
            + "Parameters: "
            + "MODULE CODE \n"
            + "Example: "
            + "viewtags CS3230";

    public static final String MESSAGE_SUCCESS = "All tags for the module shown \n%1$s";
    private final String moduleCode;

    /**
     * Creates an {@code ViewModuleTagsCommand} to show all tags attached to the given module.
     */
    public ViewModuleTagsCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList tags = model.getModuleTagsFromActiveSp(moduleCode);

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.TAG, tags.asUnmodifiableObservableList());
    }
}
