package seedu.tarence.logic.commands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * A default model stub that has all of the methods failing.
 */
class ModelStub implements Model {
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
    public Path getApplicationFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setApplicationFilePath(Path applicationFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setApplication(ReadOnlyApplication application) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyApplication getApplication() {
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
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module module) {
        // todo: Implement test for hasModule
        return false;
    }

    @Override
    public void addModule(Module module) {
        // todo: Implement test for addModule
    }

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        // todo: Implement test for hasTutorial
        return false;
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        // todo: Implement test for addTutorial
    }

    @Override
    public boolean hasModuleOfCode(ModCode modCode) {
        // todo: Implement test for addTutorial
        return false;
    }

    @Override
    public void addTutorialToModule(Tutorial tutorial) {
    }

    @Override
    public void addStudentToTutorial(Student student) {
        // to implement
    }

    @Override
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        // to implement
        return false;
    }
}
