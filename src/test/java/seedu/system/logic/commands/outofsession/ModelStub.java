package seedu.system.logic.commands.outofsession;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.system.commons.core.GuiSettings;
import seedu.system.model.Model;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.ReadOnlyUserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.session.ParticipationAttempt;
import seedu.system.model.session.Session;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getUserPrefsFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefsFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    // ===== Person =====

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPersons(ReadOnlyData<Person> newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyData<Person> getPersons() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getTotalWins(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getWinner(Competition competition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ===== Competition =====

    @Override
    public void addCompetition(Competition competition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCompetitions(ReadOnlyData<Competition> newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyData<Competition> getCompetitions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCompetition(Competition competition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCompetition(Competition target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCompetition(Competition target, Competition editedCompetition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Competition> getFilteredCompetitionList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredCompetitionList(Predicate<Competition> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ===== Participation =====

    @Override
    public void addParticipation(Participation person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setParticipations(ReadOnlyData<Participation> newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyData<Participation> getParticipations() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasParticipation(Participation person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteParticipation(Participation target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDependentParticipations(Competition competition) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDependentParticipations(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setParticipation(Participation target, Participation editedParticipation) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Participation> getFilteredParticipationList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredParticipationList(Predicate<Participation> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Session getSession() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Competition getOngoingCompetition() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasOngoingSession() {
        return false;
    }

    @Override
    public void startSession(Competition competition, ObservableList<Participation> participations) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ParticipationAttempt makeAttempt() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ParticipationAttempt getNextLifter() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ParticipationAttempt getFollowingLifter() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void endSession() {
        throw new AssertionError("This method should not be called.");
    }

}
