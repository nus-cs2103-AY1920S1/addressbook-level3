package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.book.Book;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /** The application should exit */
    private final boolean exit;

    /** The application is serving a borrower */
    private final boolean serve;

    /** The application is done serving a borrower */
    private final boolean done;

    /** The application should toggle its ui */
    private final boolean toggleUi;

    /** The application is showing information about a book*/
    private final Optional<Book> info;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser,
                          boolean showHelp, boolean exit, boolean serve, boolean done, boolean toggleUi,
                          Optional<Book> info) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.serve = serve;
        this.done = done;
        this.info = info;
        this.toggleUi = toggleUi;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false, Optional.empty());
    }

    public static CommandResult commandResultHelp(String feedbackToUser) {
        return new CommandResult(feedbackToUser, true, false, false, false, false, Optional.empty());
    }

    public static CommandResult commandResultExit(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, true, false, false, false, Optional.empty());
    }

    public static CommandResult commandResultServe(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, true, false, false, Optional.empty());
    }

    public static CommandResult commandResultDone(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, false, true, false, Optional.empty());
    }

    public static CommandResult commandResultToggleUi(String feedbackToUser) {
        return new CommandResult(feedbackToUser, false, false, false, false, true, Optional.empty());
    }

    public static CommandResult commandResultInfo(String feedbackToUser, Book book) {
        return new CommandResult(feedbackToUser, false, false, false, false, false, Optional.of(book));
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

    public boolean isServe() {
        return serve;
    }

    public boolean isInfo() {
        return info.isPresent();
    }

    public boolean isDone() {
        return done;
    }

    public boolean isToggleUi() {
        return toggleUi;
    }

    public Book getBook() {
        assert info.isPresent() : "No book info present";
        return info.get();
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
                && exit == otherCommandResult.exit
                && serve == otherCommandResult.serve
                && done == otherCommandResult.done
                && toggleUi == otherCommandResult.toggleUi
                && (info.isPresent() && otherCommandResult.info.isPresent()
                && info.get().equals(otherCommandResult.info.get())
                || info.isEmpty() && otherCommandResult.info.isEmpty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, serve, done, toggleUi, info);
    }

}
