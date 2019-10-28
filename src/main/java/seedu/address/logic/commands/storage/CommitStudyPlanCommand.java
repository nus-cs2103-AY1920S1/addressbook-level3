package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Commits current active study plan with a commit message.
 */
public class CommitStudyPlanCommand extends Command {
    public static final String COMMAND_WORD = "commit";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Committing edits to a study plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits the current active studyPlan."
            + "Parameters: "
            + "commit message \n"
            + "Example: " + COMMAND_WORD + " "
            + "NOC halfyear";

    public static final String MESSAGE_SUCCESS = "study plan commited: %1$s";
    public static final String MESSAGE_DUPLICATE_COMMIT = "This commit already exists in the commit list";

    private final String commitMessage;

    /**
     * Creates a CommitStudyPlanCommand to commit with the specified {@code commitMessage}
     */
    public CommitStudyPlanCommand(String commitMessage) {
        requireNonNull(commitMessage);
        this.commitMessage = commitMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

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
