package seedu.address.stub;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ActivityBook;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setInternalState(InternalState internalState) {
        throw new AssertionError("This method (setInternalState) should not be called.");
    }

    @Override
    public InternalState getInternalState() {
        throw new AssertionError("This method (getInternalState) should not be called.");
    }

    @Override
    public void setContext(Context context) {
        throw new AssertionError("This method (setContext) should not be called.");
    }

    @Override
    public Context getContext() {
        throw new AssertionError("This method (getContext) should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method (setUserPrefs) should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method (getUserPrefs) should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method (getGuiSettings) should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method (setGuiSettings) should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method (getAddressBookFilePath) should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method (setAddressBookFilePath) should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method (addPerson) should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method (setAddressBook) should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method (ReadOnlyAddressBook) should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method (hasPerson) should not be called.");
    }

    @Override
    public ArrayList<Person> findPersonAny(NameContainsKeywordsPredicate predicate) {
        throw new AssertionError("This method (findPersonAny) should not be called.");
    }

    @Override
    public ArrayList<Person> findPersonAll(NameContainsAllKeywordsPredicate predicate) {
        throw new AssertionError("This method (findPersonAll) should not be called.");
    }

    @Override
    public Optional<Person> findPersonByName(String searchTerm) {
        throw new AssertionError("This method (findPersonByName) should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method (deletePerson) should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method (setPerson) should not be called.");
    }

    @Override
    public Path getActivityBookFilePath() {
        throw new AssertionError("This method (getActivityBookFilePath) should not be called.");
    }

    @Override
    public ActivityBook getActivityBook() {
        throw new AssertionError("This method (getActivityBook) should not be called.");
    }

    public void setActivityBook(ActivityBook activityBook) {
        throw new AssertionError("This method (setActivityBook) should not be called.");
    }

    @Override
    public void setActivityBookFilePath(Path activityBookFilePath) {
        throw new AssertionError("This method (setActivityBookFilePath) should not be called.");
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        throw new AssertionError("This method (setActivity) should not be called.");
    }

    @Override
    public void addActivity(Activity activity) {
        throw new AssertionError("This method (addActivity) should not be called.");
    }

    @Override
    public void deleteActivity(Activity target) {
        throw new AssertionError("This method (deleteActivity) should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method (getFilteredPersonList) should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        throw new AssertionError("This method (updateFilteredPersonList) should not be called.");
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        throw new AssertionError("This method (getFilteredActivityList) should not be called.");
    }

    @Override
    public void updateFilteredActivityList(Predicate<? super Activity> predicate) {
        throw new AssertionError("This method (updateFilteredActivityList) should not be called.");
    }

    @Override
    public List<Person> getAssociatedPersons(Activity activity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Activity> getAssociatedActivities(Person person) {
        throw new AssertionError("This method should not be called.");
    }
}
