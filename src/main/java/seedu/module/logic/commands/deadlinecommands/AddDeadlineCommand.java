package seedu.module.logic.commands.deadlinecommands;
import static seedu.module.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.CommandResult;
import seedu.module.logic.commands.exceptions.CommandException;
import seedu.module.model.Model;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.TrackedModule;


/**
 * Adds deadline to a module.
 */
public class AddDeadlineCommand extends DeadlineCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds  a Deadline task to the Module. \n"
            + "All fields are compulsory \n"
            + "Parameters: "
            + "MODULE_INDEX (must be a positive integer) \n"
            + PREFIX_ACTION + "add "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TIME + "TIME "
            + PREFIX_TAG + "PRIORITY \n"
            + "Example: deadline 1 a/" + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "tutorial " + PREFIX_TIME + "2/2/2019 2359 "
            + PREFIX_TAG + "HIGH";

    public static final String MESSAGE_ADD_DEADLINE_SUCCESS = "Added deadline to Module: %1$s";
    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Unable to add deadline to module: %1$s";

    private final Index index;
    private final Deadline deadline;

    public AddDeadlineCommand(Index index, Deadline deadline) {
        this.index = index;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (deadline.getDescription().equals("") || deadline.getTime().equals("") || deadline.getTag().equals("")) {
            throw new CommandException("Description, Time and Tag inputs cannot be empty.");
        }
        TrackedModule moduleToAddDeadline = model.getTrackedModuleByIndex(model, index);
        if (moduleToAddDeadline.hasDeadline(deadline)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
        }
        moduleToAddDeadline.addDeadline(deadline);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.showAllTrackedModules();

        return new CommandResult(generateSuccessMessage(moduleToAddDeadline),
                false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the deadline is added to or removed from
     * {@code moduleToAddDeadline}.
     */
    private String generateSuccessMessage(TrackedModule moduleToAddDeadline) {
        String message = !deadline.getDescription().isEmpty() ? MESSAGE_ADD_DEADLINE_SUCCESS
                : MESSAGE_DELETE_DEADLINE_SUCCESS;
        return String.format(message, moduleToAddDeadline.getModuleCode() + " " + moduleToAddDeadline.getTitle());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddDeadlineCommand)) {
            return false;
        }
        AddDeadlineCommand e = (AddDeadlineCommand) other;
        return index.equals(e.deadline);
    }
}
