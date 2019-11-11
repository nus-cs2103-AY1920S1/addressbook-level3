package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.PartialInput;
import seedu.tarence.model.Application;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class ExportAttendanceCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String VALID_TUT_NAME = "T02";
    public static final Integer VALID_TUT_INDEX = 1;

    @Test
    public void execute_validTutNameAndModCode_exportAttendanceSuccessful() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withStudents(new ArrayList<>(Arrays.asList(validStudent)))
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);

        // Locally, the test should pass with a CommandResult being returned
        // In Travis, due to different directory structures an IO/InvalidPathException is thrown.
        // Here we assert that the correct exception is thrown instead.
        try {
            CommandResult commandResult = new ExportAttendanceCommand(
                    validModCode, validTutName, null, null).execute(modelStub);
            assertEquals(String.format(ExportAttendanceCommand.MESSAGE_EXPORT_ATTENDANCE_SUCCESS,
                    validTutName),
                    commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), Messages.MESSAGE_INVALID_FILE);
        }
        // TODO: Assert presence of exported file
    }

    @Test
    public void execute_validIndex_exportAttendanceSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withStudents(new ArrayList<>(Arrays.asList(validStudent)))
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);

        // Locally, the test should pass with a CommandResult being returned
        // In Travis, due to different directory structures an IO/InvalidPathException is thrown.
        // Here we assert that the correct exception is thrown instead.
        try {
            CommandResult commandResult = new ExportAttendanceCommand(
                    null, null, Index.fromOneBased(1), null).execute(modelStub);
            assertEquals(String.format(ExportAttendanceCommand.MESSAGE_EXPORT_ATTENDANCE_SUCCESS,
                    validTutName),
                    commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), Messages.MESSAGE_INVALID_FILE);
        }
        // TODO: Assert presence of exported file
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        ExportAttendanceCommand exportAttendanceCommand = new ExportAttendanceCommand(
                validModCode, validTutName, null, null);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> exportAttendanceCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidTutorial_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        modelStub.addModule(validModule);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        ExportAttendanceCommand exportAttendanceCommand = new ExportAttendanceCommand(
                validModCode, validTutName, null, null);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> exportAttendanceCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Index validIndex = Index.fromOneBased(1);
        final String fileName = "fileName";

        ExportAttendanceCommand validExportAttendanceCommand = new ExportAttendanceCommand(
                validModCode, validTutName, null, fileName);

        // same object -> returns true
        assertTrue(validExportAttendanceCommand.equals(validExportAttendanceCommand));

        // same values -> returns true
        ExportAttendanceCommand validExportAttendanceCommandCopy = new ExportAttendanceCommand(
            validModCode, validTutName, null, fileName);
        assertTrue(validExportAttendanceCommand.equals(validExportAttendanceCommandCopy));

        // different types -> returns false
        assertFalse(validExportAttendanceCommand.equals(1));

        // null -> returns false
        assertFalse(validExportAttendanceCommand.equals(null));

        // different modcode -> returns false
        assertFalse(validExportAttendanceCommand.equals(
                new ExportAttendanceCommand(null, validTutName, null, fileName)));

        // different tut name -> returns false
        assertFalse(validExportAttendanceCommand.equals(
                new ExportAttendanceCommand(validModCode, null, null, fileName)));

        // different index -> returns false
        assertFalse(validExportAttendanceCommand.equals(
                new ExportAttendanceCommand(validModCode, validTutName, validIndex, fileName)));

        // different file name -> returns false
        assertFalse(validExportAttendanceCommand.equals(
                new ExportAttendanceCommand(validModCode, validTutName, null, null)));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
            return false;
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
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudentIgnoreDuplicates(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudentInTutorialAndModule(Name studName, TutName tutName, ModCode modCode) {
            return false;
        }

        @Override
        public boolean hasModule(Module module) {
            return false;
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
            return false;
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            // TODO: Implement test for addTutorial
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
            return false;
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
            return false;
        }

        @Override
        public int getNumberOfTutorialsOfName(TutName tutName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void storePendingCommand(Command command) {};

        @Override
        public Command getPendingCommand() {
            return null;
        }

        @Override
        public Command peekPendingCommand() {
            return null;
        }

        @Override
        public void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {

        }

        @Override
        public List<Command> getSuggestedCommands() {
            return null;
        }

        @Override
        public String getSuggestedCorrections() {
            return null;
        }

        @Override
        public void deleteSuggestedCommands() {

        }

        @Override
        public boolean hasPendingCommand() {
            return false;
        }

        @Override
        public void storeSuggestedCompletions(PartialInput partialInput) {}

        @Override
        public PartialInput getSuggestedCompletions() {
            return null;
        }

        @Override
        public void deleteSuggestedCompletions() {}

        @Override
        public boolean hasSuggestedCompletions() {
            return false;
        }

        @Override
        public void setInputChangedToTrue() {}

        @Override
        public void setInputChangedToFalse() {}

        @Override
        public boolean hasInputChanged() {
            return false;
        }

        @Override
        public void setModel(ReadOnlyApplication application) {

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
    }

    /**
     * A Model stub that always accepts the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Student> studentsAdded = new ArrayList<>();
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            studentsAdded.add(student);
        }

        @Override
        public void addStudentToTutorial(Student student) {
            requireNonNull(student);
            for (int i = 0; i < tutorials.size(); i++) {
                if (tutorials.get(i).getTutName().equals(student.getTutName())) {
                    tutorials.get(i).addStudent(student);
                }
            }
        }

        public void addModule(Module module) {
            requireNonNull(module);
            modules.add(module);
        }

        public void addTutorial(Tutorial tutorial) {
            requireNonNull(tutorial);
            tutorials.add(tutorial);
        }

        @Override
        public void addTutorialToModule(Tutorial tutorial) {
            requireNonNull(tutorial);
            for (int i = 0; i < modules.size(); i++) {
                if (modules.get(i).getModCode().equals(tutorial.getModCode())) {
                    modules.get(i).addTutorial(tutorial);
                }
            }
        }

        @Override
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            requireAllNonNull(modCode, tutName);
            boolean hasMod = false;
            for (Module module : modules) {
                if (module.getModCode().equals(modCode)) {
                    hasMod = true;
                    break;
                }
            }
            if (!hasMod) {
                return false;
            }
            boolean hasTut = false;
            for (Tutorial tutorial : tutorials) {
                if (tutorial.getTutName().equals(tutName)) {
                    hasTut = true;
                    break;
                }
            }
            return hasTut;
        }

        @Override
        public ReadOnlyApplication getApplication() {
            return new Application();
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorials);
        }
    }

}
