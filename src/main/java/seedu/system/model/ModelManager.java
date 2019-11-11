package seedu.system.model;

import static java.util.Objects.requireNonNull;
import static seedu.system.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.system.commons.core.GuiSettings;
import seedu.system.commons.core.LogsCenter;
import seedu.system.logic.commands.outofsession.EditParticipationCommand;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.session.ParticipationAttempt;
import seedu.system.model.session.Session;

/**
 * Represents the in-memory model of the data of the system.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Data<Person> persons;
    private final FilteredList<Person> filteredPersons;
    private final Data<Competition> competitions;
    private final FilteredList<Competition> filteredCompetitions;
    private final Data<Participation> participations;
    private final FilteredList<Participation> filteredParticipations;
    private final UserPrefs userPrefs;
    private final Session session;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyData<Person> persons,
                        ReadOnlyData<Competition> competitions,
                        ReadOnlyData<Participation> participations,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(persons, userPrefs);

        logger.fine("Initializing system with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.persons = new Data<>(persons);
        filteredPersons = new FilteredList<>(this.persons.getListOfElements());
        this.competitions = new Data<>(competitions);
        filteredCompetitions = new FilteredList<>(this.competitions.getListOfElements());
        this.participations = new Data<>(participations);
        filteredParticipations = new FilteredList<>(this.participations.getListOfElements());
        this.session = Session.getInstance();
    }

    public ModelManager() {
        this(new Data(), new Data(), new Data(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefs.getPersonDataFilePath();
    }

    @Override
    public void setUserPrefsFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setPersonDataFilePath(addressBookFilePath);
    }

    //=========== Persons ================================================================================

    @Override
    public void setPersons(ReadOnlyData persons) {
        this.persons.resetData(persons);
    }

    @Override
    public ReadOnlyData<Person> getPersons() {
        return persons;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.hasUniqueElement(person);
    }

    @Override
    public void deletePerson(Person target) {
        deleteDependentParticipations(target);
        persons.removeElement(target);
    }

    @Override
    public void addPerson(Person person) {
        persons.addUniqueElement(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        updateDependentParticipations(target, editedPerson);
        persons.setElement(target, editedPerson);
    }

    @Override
    public int getTotalWins(Person person) {
        int wins = 0;
        for (Competition competition : this.competitions.getListOfElements()) {
            if (person.equals(getWinner(competition))) {
                wins++;
            }
        }
        return wins;
    }

    @Override
    public Person getWinner(Competition competition) {
        Person winner = null;
        int bestScore = -1;
        for (Participation participation : this.participations.getListOfElements()) {
            if (participation.getCompetition().equals(competition)) {
                int participationTotalScore = participation.getTotalScore();
                if (participationTotalScore > bestScore) {
                    bestScore = participationTotalScore;
                    winner = participation.getPerson();
                }
            }
        }
        return winner;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }


    //=========== Competitions ================================================================================

    @Override
    public void setCompetitions(ReadOnlyData<Competition> competitions) {
        this.competitions.resetData(competitions);
    }

    @Override
    public ReadOnlyData<Competition> getCompetitions() {
        return competitions;
    }

    @Override
    public boolean hasCompetition(Competition competition) {
        requireNonNull(competition);
        return competitions.hasUniqueElement(competition);
    }

    @Override
    public void deleteCompetition(Competition competition) {
        deleteDependentParticipations(competition);
        competitions.removeElement(competition);
    }

    @Override
    public void addCompetition(Competition competition) {
        competitions.addUniqueElement(competition);
        updateFilteredCompetitionList(PREDICATE_SHOW_ALL_COMPETITIONS);
    }

    @Override
    public void setCompetition(Competition target, Competition editedCompetition) {
        requireAllNonNull(target, editedCompetition);

        updateDependentParticipations(target, editedCompetition);
        competitions.setElement(target, editedCompetition);
    }

    //=========== Filtered Competition List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Competition} backed by the internal list of
     * {@code versionedSystem}
     */
    @Override
    public ObservableList<Competition> getFilteredCompetitionList() {
        return filteredCompetitions;
    }

    @Override
    public void updateFilteredCompetitionList(Predicate<Competition> predicate) {
        requireNonNull(predicate);
        filteredCompetitions.setPredicate(predicate);
    }

    //=========== Participations ================================================================================

    @Override
    public void setParticipations(ReadOnlyData<Participation> participations) {
        this.participations.resetData(participations);
    }

    @Override
    public ReadOnlyData<Participation> getParticipations() {
        return participations;
    }

    @Override
    public boolean hasParticipation(Participation participation) {
        requireNonNull(participation);
        return participations.hasUniqueElement(participation);
    }

    @Override
    public void deleteParticipation(Participation participation) {
        participations.removeElement(participation);
    }

    @Override
    public void deleteDependentParticipations(Competition competition) {
        List<Participation> toDelete = new ArrayList<>();

        for (Participation participation : participations.getListOfElements()) {
            if (participation.getCompetition().equals((competition))) {
                toDelete.add(participation);
            }
        }

        for (Participation participation : toDelete) {
            deleteParticipation(participation);
        }
    }

    @Override
    public void deleteDependentParticipations(Person person) {
        List<Participation> toDelete = new ArrayList<>();

        for (Participation participation : participations.getListOfElements()) {
            if (participation.getPerson().equals((person))) {
                toDelete.add(participation);
            }
        }

        for (Participation participation : toDelete) {
            deleteParticipation(participation);
        }

    }

    @Override
    public void addParticipation(Participation participation) {
        participations.addUniqueElement(participation);
        updateFilteredParticipationList(PREDICATE_SHOW_ALL_PARTICIPATIONS);
    }

    @Override
    public void setParticipation(Participation target, Participation editedParticipation) {
        requireAllNonNull(target, editedParticipation);

        participations.setElement(target, editedParticipation);
    }

    //=========== Filtered Participation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Competition} backed by the internal list of
     * {@code versionedSystem}
     */
    @Override
    public ObservableList<Participation> getFilteredParticipationList() {
        return filteredParticipations;
    }

    @Override
    public void updateFilteredParticipationList(Predicate<Participation> predicate) {
        requireNonNull(predicate);
        filteredParticipations.setPredicate(predicate);
    }

    //=========== Session Handling =============================================================

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public Competition getOngoingCompetition() {
        return session.getCompetition();
    }

    @Override
    public boolean hasOngoingSession() {
        return session.isOngoing();
    }

    @Override
    public void startSession(Competition competition, ObservableList<Participation> participations) {
        requireNonNull(competition);
        requireAllNonNull(participations);
        session.start(competition, participations);
    }

    @Override
    public ParticipationAttempt makeAttempt() {
        return session.attemptMade();
    }

    @Override
    public ParticipationAttempt getNextLifter() {
        return session.nextLifter();
    }

    @Override
    public ParticipationAttempt getFollowingLifter() {
        return session.getFollowingLifter();
    }

    @Override
    public void endSession() {
        session.end();
    }

    //============ Private functions to cope with the many-to-many relationship ==============================

    /**
     * Updates all participation associated with a {@code person} with the new {@code updatedPerson}
     * @param person the person that is associated with the the target participations.
     * @param updatedPerson the person the participations are to be updated with.
     */
    private void updateDependentParticipations(Person person, Person updatedPerson) {
        for (Participation participation : this.participations.getListOfElements()) {
            if (person.equals(participation.getPerson())) {
                Participation newParticipation =
                    EditParticipationCommand.createEditedParticipation(participation, updatedPerson);
                participations.setElement(participation, newParticipation);
            }
        }
    }

    /**
     * Updates all participation associated with a {@code competition} with the new {@code updatedCompetition}
     * @param competition the competition that is associated with the the target participations.
     * @param updatedCompetition the competition the participations are to be updated with.
     */
    private void updateDependentParticipations(Competition competition, Competition updatedCompetition) {
        for (Participation participation : this.participations.getListOfElements()) {
            if (competition.equals(participation.getCompetition())) {
                Participation newParticipation =
                    EditParticipationCommand.createEditedParticipation(participation, updatedCompetition);
                participations.setElement(participation, newParticipation);
            }
        }
    }

    //==========================================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
            && persons.equals(other.persons)
            && filteredPersons.equals(other.filteredPersons)
            && competitions.equals(other.competitions)
            && filteredCompetitions.equals(other.filteredCompetitions)
            && participations.equals(other.participations)
            && filteredParticipations.equals(other.filteredParticipations);
    }
}
