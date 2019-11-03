package seedu.module.logic.commands.deadlinecommands;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;

/**
 * Edits deadline description of a module.
 */
public class EditDeadlineDescCommand extends EditDeadlineCommand {

    private Index index;
    private String description;
    private int taskListNum;
    private Deadline deadline;

    public EditDeadlineDescCommand(Index index, String description, int taskListNum) {
        this.index = index;
        this.description = description;
        this.taskListNum = taskListNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TrackedModule moduleToEditDescription = model.getTrackedModuleByIndex(model, index);
        deadline = moduleToEditDescription.getDeadlineList().get(taskListNum - 1);
        deadline.editDescription(description);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToEditDescription),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the description of deadline task is edited in
     * {@code moduleToEditDescription}.
     */
    private String generateSuccessMessage(TrackedModule moduleToEditDescription) {
        String message = !deadline.getDescription().isEmpty() ? MESSAGE_EDIT_DEADLINE_SUCCESS
                : MESSAGE_EDIT_DEADLINE_FAIL;
        return String.format(message, moduleToEditDescription);
    }
}
