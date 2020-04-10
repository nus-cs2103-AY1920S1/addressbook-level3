package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.ui.feature.Feature;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;
    private Feature feature;
    private Person person;
    private AthletickDate date;
    private Model model;
    private String eventName;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** All data in application should be cleared and calendar should refresh. */
    private final boolean clear;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean clear) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.clear = clear;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code
     * showHelp}, {@code exit}, {@code clear} and {@code model}.
     * @param feedbackToUser String to display to user
     * @param showHelp Boolean indicating if command is a help command
     * @param exit Boolean indicating if command is an exit command
     * @param clear Boolean indicating if command is a clear command
     * @param model Model representing state of Athletick
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean clear,
                         Model model) {
        this(feedbackToUser, showHelp, exit, clear);
        this.model = model;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code
     * featureToDisplay} and {@code model}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Feature featureToDisplay, Model model) {
        this(feedbackToUser, false, false, false);
        this.feature = featureToDisplay;
        this.model = model;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code date}
     * and {@code model}, and other fields set to their default value.
     * @param feedbackToUser String to display to user
     * @param date Date
     * @param model Model representing state of Athletick
     */
    public CommandResult(String feedbackToUser, Feature feature, AthletickDate date, Model model) {
        this(feedbackToUser, false, false, false);
        this.feature = feature;
        this.date = date;
        this.model = model;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code
     * featureToDisplay}, {@code model}, {@code eventName}, and other fields set to their default value.
     * For use in viewing records command.
     */
    public CommandResult(String feedbackToUser, Feature featureToDisplay, Model model, String eventName) {
        this(feedbackToUser, false, false, false);
        this.feature = featureToDisplay;
        this.model = model;
        this.eventName = eventName;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code
     * selectedPerson}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Person selectedPerson) {
        this(feedbackToUser, false, false, false);
        this.person = selectedPerson;
    }

    public Feature getFeature() {
        return feature;
    }

    public Person getPerson() {
        return person;
    }

    public AthletickDate getDate() {
        return date;
    }

    public Model getModel() {
        return model;
    }

    public String getEventName() {
        return eventName;
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

    public boolean isClear() {
        return clear;
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
                && clear == otherCommandResult.clear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, clear);
    }

}
