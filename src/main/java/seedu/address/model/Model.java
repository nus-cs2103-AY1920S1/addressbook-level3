package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Initializes the model.
     */
    void initialize();

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

    /* Get the filtered lists */

    FilteredList<Participant> getFilteredParticipantList();

    FilteredList<Team> getFilteredTeamList();

    FilteredList<Mentor> getFilteredMentorList();

    void resetFilteredLists();

    /* Below is the API exposed for the controllers to call */

    /* Participant methods */

    Participant getParticipant(Id id) throws AlfredException;

    void addParticipant(Participant participant) throws AlfredException;

    void updateParticipant(Id id, Participant participant) throws AlfredException;

    Participant deleteParticipant(Id id) throws AlfredException;

    /* Team methods */

    Team getTeam(Id teamId) throws AlfredException;

    Team getTeamByParticipantId(Id participantId) throws AlfredException;

    Team getTeamByMentorId(Id mentorId) throws AlfredException;

    void addTeam(Team team) throws AlfredException;

    void addParticipantToTeam(Id teamId, Participant participant) throws AlfredException;

    void addMentorToTeam(Id teamId, Mentor mentor) throws AlfredException;

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

    /* View command */
    /**
     * Sets the predicate to show detailed information of {@code entity}.
     *
     * @param entity {@code Entity} to view.
     */
    void viewEntity(Entity entity);

    /**
     * Updates the history of entity states with the current state (after execution of Command c)
     */
    void updateHistory(Command c);

    /**
     * Undoes the effects of the previous command and returns the model to the state
     * prior to the execution of the command.
     */
    void undo() throws AlfredModelHistoryException;

    /**
     * Redoes the effects of the previously executed command and returns the model to the state
     * after the execution of the command.
     */
    void redo() throws AlfredModelHistoryException;

    /**
     * Gets a String detailing the previously executed commands that can be undone by the user.
     * @return String representing the previously executed commands that can be undone by the user.
     */
    String getCommandHistoryString();

    /**
     * Returns a List of Strings describing the commands that can be undone.
     */
    List<String> getUndoCommandHistory();

    /**
     * Returns a List of Strings describing the commands that can be redone.
     */
    List<String> getRedoCommandHistory();

    /**
     * Returns a List of CommandsRecords describing the commands that can be undone/redone
     * @throws AlfredModelHistoryException
     */
    ArrayList<CommandRecord> getCommandHistory();
}
