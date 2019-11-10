package seedu.address.logic;

import java.nio.file.Path;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandRecord;
import seedu.address.model.Statistics;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, AlfredModelHistoryException;

    /**
     * Returns an unmodifiable view of the filtered list of Participants
     */
    ObservableList<Participant> getFilteredParticipantList();

    /**
     * Returns an unmodifiable view of the filtered list of Teams
     */
    ObservableList<Team> getFilteredTeamList();

    /**
     * Returns an unmodifiable view of the filtered list of Mentors
     */
    ObservableList<Mentor> getFilteredMentorList();

    /**
     * Returns an unmodifiable view of the sorted list of Teams
     */
    ObservableList<Team> getSortedTeamList();

    /** Returns the Record of all Commands entered by User */
    ArrayList<CommandRecord> getCommandHistory();

    /**
     * Returns the user prefs' ParticipantList file path.
     */
    Path getParticipantListFilePath();

    /**
     * Returns the user prefs' TeamList file path.
     */
    Path getTeamListFilePath();

    /**
     * Returns the user prefs' MentorList file path.
     */
    Path getMentorListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get statistics on EntityLists.
     */
    Statistics getStatistics();

    /**
     * Gets string of previous command executed.
     */
    String getPrevCommandString();

    /**
     * Gets string of next command executed.
     *
     * @return
     */
    String getNextCommandString();
}
