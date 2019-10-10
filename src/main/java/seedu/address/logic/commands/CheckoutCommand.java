package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Checkout to a project to work on it.
 */
public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": switch to a project in the list to work on "
            + "by the index number used in the displayed project list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_CHECKOUT_SUCCESS = "Switched to project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;

    public CheckoutCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToSwitchTo = lastShownList.get(index.getZeroBased());
        model.setWorkingProject(projectToSwitchTo);
        return new CommandResult(String.format(MESSAGE_CHECKOUT_SUCCESS, projectToSwitchTo.toString()));
    }
}
