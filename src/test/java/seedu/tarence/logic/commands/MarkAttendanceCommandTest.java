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
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Application;
import seedu.tarence.model.Model;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.testutil.ModuleBuilder;
import seedu.tarence.testutil.StudentBuilder;
import seedu.tarence.testutil.TutorialBuilder;

public class MarkAttendanceCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String VALID_TUT_NAME = "T02";
    public static final Integer VALID_TUT_INDEX = 1;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MarkAttendanceCommand(null, null, null, null));
    }

    @Test
    public void execute_personAcceptedByModel_markAttendanceSuccessful() throws Exception {
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
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();

        CommandResult commandResult = new MarkAttendanceCommand(
                validModCode, validTutName, validWeek, validStudName).execute(modelStub);

        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS, validStudName, "present"),
                commandResult.getFeedbackToUser());
        assertTrue(validTutorial.getAttendance().isPresent(validWeek, validStudent));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, validWeek, validStudName);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> markAttendanceCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidTutorial_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        modelStub.addModule(validModule);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, validWeek, validStudName);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> markAttendanceCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidStudent_throwsCommandException() {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, validWeek, validStudName);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_STUDENT_IN_TUTORIAL, () -> markAttendanceCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        MarkAttendanceCommand markAliceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, validWeek, alice.getName());
        MarkAttendanceCommand markBobCommand = new MarkAttendanceCommand(
            validModCode, validTutName, validWeek, bob.getName());

        // same object -> returns true
        assertTrue(markAliceCommand.equals(markAliceCommand));

        // same values -> returns true
        MarkAttendanceCommand markAliceCommandCopy = new MarkAttendanceCommand(
            validModCode, validTutName, validWeek, alice.getName());
        assertTrue(markAliceCommandCopy.equals(markAliceCommand));

        // different types -> returns false
        assertFalse(markAliceCommand.equals(1));

        // null -> returns false
        assertFalse(markAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(markAliceCommand.equals(markBobCommand));
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
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
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
        public void setAttendance(Tutorial tutorial, Week week, Student student) {
            // TODO: implement
        }

        @Override
        public void storePendingCommand(Command command) {};

        @Override
        public Command getPendingCommand() {
            return null;
        }

        @Override
        public boolean hasPendingCommand() {
            return false;
        }
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
        public void setAttendance(Tutorial tutorial, Week week, Student student) {
            requireAllNonNull(tutorial, week, student);
            tutorial.setAttendance(week, student);
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorials);
        }
    }

}
