package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.ReadOnlyEntityList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

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
     * Checks if there exists any {@code Entity} in this {@code Model}.
     */
    boolean isEmpty();

    /**
     * Returns the ParticipantList.
     */
    ReadOnlyEntityList getParticipantList();

    /**
     * Returns the TeamList.
     */
    ReadOnlyEntityList getTeamList();

    /**
     * Returns the MentorList.
     */
    ReadOnlyEntityList getMentorList();

    /* Get the filtered and sorted lists */

    FilteredList<Participant> getFilteredParticipantList();

    FilteredList<Team> getFilteredTeamList();

    FilteredList<Mentor> getFilteredMentorList();

    SortedList<Team> getSortedTeamList();

    void resetFilteredLists();

    /* Below is the API exposed for the controllers to call */

    /* Participant methods */

    Participant getParticipant(Id id) throws AlfredException;

    void addParticipant(Participant participant) throws AlfredException;

    void updateParticipant(Id id, Participant participant) throws AlfredException;

    Participant deleteParticipant(Id id) throws AlfredException;

    /* Team methods */

    Team getTeam(Id teamId) throws AlfredException;

    List<Team> getTeamByParticipantId(Id participantId) throws AlfredException;

    List<Team> getTeamByMentorId(Id mentorId) throws AlfredException;

    void addTeam(Team team) throws AlfredException;

    void addParticipantToTeam(Id teamId, Participant participant) throws AlfredException;

    void removeParticipantFromTeam(Id teamId, Participant participant) throws AlfredException;

    void addMentorToTeam(Id teamId, Mentor mentor) throws AlfredException;

    void removeMentorFromTeam(Id teamId, Mentor mentor) throws AlfredException;

    void updateTeam(Id teamId, Team team) throws AlfredException;

    Team deleteTeam(Id id) throws AlfredException;

    /* Mentor methods */

    Mentor getMentor(Id id) throws AlfredException;

    void addMentor(Mentor mentor) throws AlfredException;

    void updateMentor(Id id, Mentor mentor) throws AlfredException;

    Mentor deleteMentor(Id id) throws AlfredException;

    /* Find commands */

    List<Participant> findParticipant(Predicate<Participant> predicate);

    List<Team> findTeam(Predicate<Team> predicate);

    List<Mentor> findMentor(Predicate<Mentor> predicate);

    /* Score methods */

    void setTeamScore(Team team, Score score) throws AlfredException;

    void addTeamScore(Team team, Score score) throws AlfredException;

    void subtractTeamScore(Team team, Score score) throws AlfredException;

    /* Leaderboard methods */

    void setSimpleLeaderboard(ArrayList<Comparator<Team>> comparators, SubjectName subject);

    void setTopK(int k, ArrayList<Comparator<Team>> comparators, SubjectName subject);

    void setTopKRandom(int k, ArrayList<Comparator<Team>> comparators, SubjectName subject);

    /* View command */

    /**
     * Sets the predicate to show detailed information of {@code entity}.
     *
     * @param entity {@code Entity} to view.
     */
    void viewEntity(Entity entity);

    /**
     * Updates the history of entity states with the current state (after execution
     * of Command c)
     */
    void updateHistory(Command c);

    /**
     * Undoes the effects of {@code numToUndo} previous command(s) and returns the model to the state
     * prior to the execution of these command(s).
     *
     * @param numToUndo number of previous commands to undo.
     * @throws AlfredModelHistoryException this is thrown when an error is encountered trying to alter the model
     *                                     state while undoing the previous commands.
     */
    void undo(int numToUndo) throws AlfredModelHistoryException;

    /**
     * Redoes the effects of the {@code numToRedo} next command(s) and returns the model to the state
     * after the execution of these command(s).
     *
     * @param numToRedo number of next commands to redo.
     * @throws AlfredModelHistoryException this is thrown when an error is encountered trying to alter the model
     *                                     state while redoing the next commands.
     */
    void redo(int numToRedo) throws AlfredModelHistoryException;

    /**
     * Returns a List of CommandsRecords describing the commands that can be
     * undone/redone
     *
     * @throws AlfredModelHistoryException
     */
    ArrayList<CommandRecord> getCommandHistory();

    /**
     * Records the execution of the command.
     * This is for the Command Navigation feature.
     */
    void recordCommandExecution(String commandInputString);

    /**
     * Gets the string of the previous command executed.
     */
    String getPrevCommandString();

    /**
     * Gets the string of the next command executed.
     */
    String getNextCommandString();
}
