package seedu.planner.logic.commands.result;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 * @author 1nefootstep
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The application should exit. */
    private final boolean exit;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    private final UiFocus[] uiFocus;
    private final ResultInformation[] resultInformation;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, ResultInformation[] resultInformation,
                         boolean showHelp, boolean exit, UiFocus[] uiFocus) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.resultInformation = resultInformation;
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiFocus = uiFocus;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, UiFocus[] uiFocus) {
        this(feedbackToUser, null, showHelp, exit, uiFocus);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * but with uiFocus in its default value of null.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, null, showHelp, exit, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default values.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, false,
                false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and {@code uiFocus}, while other fields are set to their default values.
     */
    public CommandResult(String feedbackToUser, UiFocus[] uiFocus) {
        this(feedbackToUser, null, false, false, requireAllNonNullAndReturn(uiFocus));
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and {@code uiFocus}, while other fields are set to their default values.
     */
    public CommandResult(String feedbackToUser, ResultInformation[] resultInformation, UiFocus[] uiFocus) {
        this(feedbackToUser, requireAllNonNullAndReturn(resultInformation),
                false, false, requireAllNonNullAndReturn(uiFocus));
    }

    /**
     * Ensures that the array is non-null and that all elements are also non-null.
     */
    private static <E> E[] requireAllNonNullAndReturn(E[] elements) {
        requireNonNull(elements);
        for (E e : elements) {
            requireNonNull(e);
        }
        return elements;
    }

    public Optional<ResultInformation[]> getInformationToUser() {
        return Optional.ofNullable(resultInformation);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public Optional<UiFocus[]> getUiFocus() {
        return Optional.ofNullable(uiFocus);
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
                && Arrays.equals(uiFocus, otherCommandResult.uiFocus)
                && Arrays.equals(resultInformation, otherCommandResult.resultInformation)
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, Arrays.hashCode(uiFocus), Arrays.hashCode(resultInformation),
                showHelp, exit);
    }

}
