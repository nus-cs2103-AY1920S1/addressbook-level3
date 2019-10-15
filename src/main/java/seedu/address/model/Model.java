package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.competition.Competition;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Competition> PREDICATE_SHOW_ALL_COMPETITIONS = unused -> true;
    Predicate<Participation> PREDICATE_SHOW_ALL_PARTICIPATIONS = unused -> true;

    //=========== User Preferences ================================================================================

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
     * Returns the user prefs' system file path.
     */
    Path getUserPrefsFilePath();

    /**
     * Sets the user prefs' system file path.
     */
    void setUserPrefsFilePath(Path addressBookFilePath);

    //=========== Persons ================================================================================

    /**
     * Replaces data of persons with the data in {@code persons}.
     */
    void setPersons(ReadOnlyData<Person> persons);

    /** Returns the data of persons */
    ReadOnlyData<Person> getPersons();

    /**
     * Returns true if a competition with the same identity as {@code competition} exists in the system.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the system.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the system.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the system.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the system.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Competitions ================================================================================

    /**
     * Replaces data of competitions with the data in {@code competitions}.
     */
    void setCompetitions(ReadOnlyData<Competition> competitions);

    /** Returns the data of competitions */
    ReadOnlyData<Competition> getCompetitions();

    /**
     * Returns true if a competition with the same identity as {@code competition} exists in the system.
     */
    boolean hasCompetition(Competition competition);

    /**
     * Soft deletes the given competition.
     * The competition must exist in the system.
     */
    void deleteCompetition(Competition target);

    /**
     * Adds the given competition.
     * {@code competition} must not already exist in the system.
     */
    void addCompetition(Competition competition);

    /**
     * Replaces the given competition {@code target} with {@code editedCompetition}.
     * {@code target} must exist in the system.
     * The competition identity of {@code editedCompetition} must not be the same as another existing competition
     * in the system.
     */
    void setCompetition(Competition target, Competition editedCompetition);

    /** Returns an unmodifiable view of the filtered competition list */
    ObservableList<Competition> getFilteredCompetitionList();

    /**
     * Updates the filter of the filtered competition list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCompetitionList(Predicate<Competition> predicate);

    //=========== Participations ================================================================================

    /**
     * Replaces data of participations with the data in {@code participations}.
     */
    void setParticipations(ReadOnlyData<Participation> participations);

    /** Returns the Participation */
    ReadOnlyData<Participation> getParticipations();

    /**
     * Returns true if a participation with the same identity as {@code participation} exists in the system.
     */
    boolean hasParticipation(Participation participation);

    /**
     * Soft deletes the given participation.
     * The participation must exist in the system.
     */
    void deleteParticipation(Participation target);

    /**
     * Adds the given participation.
     * {@code participation} must not already exist in the system.
     */
    void addParticipation(Participation participation);

    /**
     * Replaces the given participation {@code target} with {@code editedParticipation}.
     * {@code target} must exist in the system.
     * The competition identity of {@code editedParticipation} must not be the same as another existing participation
     * in the system.
     */
    void setParticipation(Participation target, Participation editedParticipation);

    /** Returns an unmodifiable view of the filtered participation list */
    ObservableList<Participation> getFilteredParticipationList();

    /**
     * Updates the filter of the filtered participation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredParticipationList(Predicate<Participation> predicate);
}
