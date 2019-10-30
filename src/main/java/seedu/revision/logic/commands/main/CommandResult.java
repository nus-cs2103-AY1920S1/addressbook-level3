package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.quiz.Mode;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private String feedbackToUser;

    /** Help information should be shown to the user. */
    private boolean showHelp;

    /** The application should exit. */
    private boolean exit;

    /** The quiz will start. */
    private boolean start;

    /** The restore window will open. */
    private boolean showRestore;

    /** The mode of the quiz in session **/
    private Mode mode;

    /** To pass the Model. */
    private Model model;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult() {
        this.feedbackToUser = "";
        this.showHelp = false;
        this.exit = false;
        this.start = false;
        this.showRestore = false;
        this.mode = new Mode("normal");
        this.model = new ModelManager();
    }

    /**
     * Instantiates a CommandResult object with the fields updated during building by the factory method.
     * @param feedbackToUser feedback that will be provided to the user.
     * @param showHelp true if it is a help command, false otherwise.
     * @param exit true if it is a exit command, false otherwise.
     * @param start true if it is a start command, false otherwise.
     * @param showRestore true if it is a restore command, false otherwise.
     * @param mode mode that was selected by the user.
     * @param model model that will be used to restore.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean start, boolean showRestore,
                          Mode mode, Model model) {
        requireNonNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
        this.start = start;
        this.showRestore = showRestore;
        this.mode = mode;
        this.model = model;
    }

    /**
     * Adds feedback to the {@code CommandResult} to be returned to the user.
     * @param feedbackToUser feedback that will be provided to the user.
     * @return CommandResult with the update feedback.
     */
    public CommandResult withFeedBack(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResult} to indicate whether the command is a {@code HelpCommand}.
     * @param isHelp input boolean to determine result.
     * @return {@code CommandResult} with the withHelp boolean updated according to the input.
     */
    public CommandResult withHelp(boolean isHelp) {
        this.showHelp = isHelp;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResult} to indicate whether the command is a {@code ExitCommand}.
     * @param isExit input boolean to determine result.
     * @return {@code CommandResult} with the withExit boolean updated according to the input.
     */
    public CommandResult withExit(boolean isExit) {
        this.exit = isExit;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResult} to indicate whether the command is a {@code StartQuizCommand}.
     * @param isStart input boolean to determine result.
     * @return {@code CommandResult} with the withStart boolean updated according to the input.
     */
    public CommandResult withStart(boolean isStart) {
        this.start = isStart;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResult} to indicate whether the command is a {@code RestoreCommand}.
     * @param isRestore input boolean to determine result.
     * @return {@code CommandResult} with the withRestore boolean updated according to the input.
     */
    public CommandResult withRestore(boolean isRestore) {
        this.showRestore = isRestore;
        return this;
    }

    /**
     * Adds a {@code Mode} to the {@code CommandResult} to be used to determine the mode when starting the quiz session.
     * @param mode mode that is chosen by the user.
     * @return {@code CommandResult} with the updated mode.
     */
    public CommandResult withMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Adds a {@code Mode} to the {@code CommandResult} to be used in the restore command.
     * @param model current model that is being used.
     * @return {@code CommandResult} with the updated model.
     */
    public CommandResult withModel(Model model) {
        this.model = model;
        return this;
    }

    /**
     * Builds a new instance of the {@code CommandResult} using the factory method.
     * @return {@code CommandResult}
     */
    public CommandResult build() {
        return new CommandResult(feedbackToUser, showHelp, exit, start, showRestore, mode, model);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isShowRestore() {
        return showRestore;
    }

    public Model getModel() {
        return model;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
