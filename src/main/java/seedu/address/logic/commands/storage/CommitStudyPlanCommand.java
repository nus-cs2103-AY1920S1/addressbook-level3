package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Commits the current active study plan with a commit message.
 */
public class CommitStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "commit";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Committing edits to a study plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits the current active study plan."
            + "Parameters: "
            + "commit message \n"
            + "Example: " + COMMAND_WORD + " "
            + "NOC half year";
    public static final String MESSAGE_SUCCESS = "Current study plan committed with a message: %1$s";

    private final String commitMessage;

    /**
     * Creates a CommitStudyPlanCommand to commit with the specified {@code commitMessage}.
     */
    public CommitStudyPlanCommand(String commitMessage) {
        requireNonNull(commitMessage);
        this.commitMessage = commitMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        model.commitActiveStudyPlan(commitMessage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commitMessage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommitStudyPlanCommand // instanceof handles nulls
                && commitMessage.equals(((CommitStudyPlanCommand) other).commitMessage));
    }
}
