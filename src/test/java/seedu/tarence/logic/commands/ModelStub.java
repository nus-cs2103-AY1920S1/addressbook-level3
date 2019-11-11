package seedu.tarence.logic.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * A default model stub that has all of the methods failing.
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
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(NameContainsKeywordsPredicate predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudentIgnoreDuplicates(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudentIgnoreDuplicates(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudentInTutorialAndModule(Name studName, TutName tutName, ModCode modCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTutorialsFromModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTutorial(Tutorial tutorial) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTutorial(Tutorial tutorial) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudentsFromTutorial(Tutorial tutorial) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModuleOfCode(ModCode modCode) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTutorialToModule(Tutorial tutorial) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudentToTutorial(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getNumberOfTutorialsOfName(TutName tutName) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void storePendingCommand(Command command) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Command getPendingCommand() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Command peekPendingCommand() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPendingCommand() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void storeSuggestedCommands(List<Command> l, String s) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Command> getSuggestedCommands() {
        return new ArrayList<Command>();
    }

    @Override
    public String getSuggestedCorrections() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSuggestedCommands() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void storeSuggestedCompletions(PartialInput partialInput) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public PartialInput getSuggestedCompletions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSuggestedCompletions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSuggestedCompletions() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInputChangedToTrue() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setInputChangedToFalse() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void saveInput(String input) {};

    @Override
    public List<String> getInputHistory() {
        return new ArrayList<>();
    }

    @Override
    public int getInputHistoryIndex() {
        return 0;
    }

    @Override
    public void incrementInputHistoryIndex() {}

    @Override
    public void decrementInputHistoryIndex() {}

    @Override
    public void resetInputHistoryIndex() {}

    @Override
    public boolean hasInputChanged() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModel(ReadOnlyApplication application) {

    }
}
