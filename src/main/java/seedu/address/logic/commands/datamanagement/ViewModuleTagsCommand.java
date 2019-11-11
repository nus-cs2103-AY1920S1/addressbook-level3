package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

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
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing tags for a specified module";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags attached to a specific module. "
            + "Parameters: "
            + "MODULE CODE \n"
            + "Example: "
            + "viewtags CS3230";

    public static final String MESSAGE_SUCCESS = "All tags for the module shown";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_NO_TAGS_FOUND = "This module does not have any tags";
    private final String moduleCode;

    /**
     * Creates an {@code ViewModuleTagsCommand} to show all tags attached to the given module.
     */
    public ViewModuleTagsCommand(String moduleCode) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (!model.isValidModuleCode(this.moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        UniqueTagList tags = model.getModuleTagsFromActiveSp(moduleCode);

        System.out.println(tags.asListOfStrings().size());

        if (tags.asUnmodifiableObservableList().size() == 0) {
            throw new CommandException(MESSAGE_NO_TAGS_FOUND);
        }

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.TAG, tags.asUnmodifiableObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewModuleTagsCommand // instanceof handles nulls
                && moduleCode.equals(((ViewModuleTagsCommand) other).moduleCode)); // state check
    }

}
