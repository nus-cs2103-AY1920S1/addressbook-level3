package seedu.address.stub;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ActivityBook;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

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
    public void setInternalState(InternalState internalState) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public InternalState getInternalState() {
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
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Person> findPerson(NameContainsKeywordsPredicate predicate) {
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
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getActivityBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ActivityBook getActivityBook() {
        throw new AssertionError("This method should not be called.");
    }

    public void setActivityBook(ActivityBook activityBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setActivityBookFilePath(Path activityBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addActivity(Activity activity) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteActivity(Activity target) {
        throw new AssertionError("This method should not be called.");
    }
}
