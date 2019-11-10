package seedu.revision.logic.commands.main;

import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;

/**
 * Represents the result of a command execution.
 */
public class CommandResultBuilder {

    /** Feedback that will be shown to the user**/
    private String feedbackToUser = "";

    /** Help information should be shown to the user. */
    private boolean showHelp = false;

    /** The application should exit. */
    private boolean exit = false;

    /** The quiz will start. */
    private boolean start = false;

    /** The restore window will open. */
    private boolean showRestore = false;

    /** The history window will open. */
    private boolean showHistory = false;

    /** The stats window will open. */
    private boolean showStats = false;

    /** The answer is correct. */
    private boolean isCorrect = false;

    /** The mode of the quiz in session **/
    private Mode mode;

    /** To pass the Model. */
    private Model model;

    /**
     * Constructs a {@code CommandResultBuilder} with the default values.
     * and other fields set to their default value.
     */
    public CommandResultBuilder() {
        this.mode = new NormalMode();
        this.model = new ModelManager();
    }

    /**
     * Adds feedback to the {@code CommandResultBuilder} to be returned to the user.
     * @param feedbackToUser feedback that will be provided to the user.
     * @return CommandResultBuilder with the updated feedback.
     */
    public CommandResultBuilder withFeedBack(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code HelpCommand}.
     * @param isHelp input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withHelp boolean updated according to the input.
     */
    public CommandResultBuilder withHelp(boolean isHelp) {
        this.showHelp = isHelp;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code ExitCommand}.
     * @param isExit input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withExit boolean updated according to the input.
     */
    public CommandResultBuilder withExit(boolean isExit) {
        this.exit = isExit;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code StartCommand}.
     * @param isStart input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withStart boolean updated according to the input.
     */
    public CommandResultBuilder withStart(boolean isStart) {
        this.start = isStart;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code RestoreCommand}.
     * @param isRestore input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withRestore boolean updated according to the input.
     */
    public CommandResultBuilder withRestore(boolean isRestore) {
        this.showRestore = isRestore;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code RestoreCommand}.
     * @param isHistory input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withRestore boolean updated according to the input.
     */
    public CommandResultBuilder withHistory(boolean isHistory) {
        this.showHistory = isHistory;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the command is a {@code RestoreCommand}.
     * @param isStats input boolean to determine result.
     * @return {@code CommandResultBuilder} with the withRestore boolean updated according to the input.
     */
    public CommandResultBuilder withStats(boolean isStats) {
        this.showStats = isStats;
        return this;
    }

    /**
     * Adds a {@code Mode} to the {@code CommandResultBuilder} to be used to determine the mode when starting the
     * quiz session.
     * @param mode mode that is chosen by the user.
     * @return {@code CommandResultBuilder} with the updated mode.
     */
    public CommandResultBuilder withMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Adds a boolean to the {@code CommandResultBuilder} to indicate whether the answer is correct
     * @param isCorrect input boolean to determine whether answer is correct.
     * @return {@code CommandResultBuilder} with the withCorrect boolean updated according to the input.
     */
    public CommandResultBuilder withCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
        return this;
    }

    /**
     * Adds a {@code Mode} to the {@code CommandResultBuilder} to be used in the restore command.
     * @param model current model that is being used.
     * @return {@code CommandResultBuilder} with the updated model.
     */
    public CommandResultBuilder withModel(Model model) {
        this.model = model;
        return this;
    }

    /**
     * Builds a new instance of the {@code CommandResult} using the builder design pattern.
     * @return {@code CommandResult}
     */
    public CommandResult build() {
        return new CommandResult(this);
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

    public boolean isShowHistory() {
        return showHistory;
    }

    public boolean isShowStats() {
        return showStats;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public Model getModel() {
        return model;
    }

    public Mode getMode() {
        return mode;
    }

}
