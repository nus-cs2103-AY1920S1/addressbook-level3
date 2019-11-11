package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.core.Messages.MESSAGE_MULTIPLE_OF_SAME_NAME;
import static seedu.tarence.commons.core.Messages.MESSAGE_SUGGESTED_CORRECTIONS;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.logic.commands.MarkAttendanceCommand.MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT;
import static seedu.tarence.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_TUTORIAL;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.Messages;
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
import seedu.tarence.model.tutorial.Week;

public class MarkAttendanceCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1061";
    public static final String VALID_TUT_NAME = "T02";
    public static final Integer VALID_TUT_INDEX = 1;
    public static final String VALID_STUD_NAME = "Lady Gaga";
    public static final String SIMILAR_STUD_NAME = "Lady Gagaa";

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
                validModCode, validTutName, null, validWeek, validStudName).execute(modelStub);

        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS, validStudName, "present"),
                commandResult.getFeedbackToUser());
        assertTrue(validTutorial.getAttendance().isPresent(validWeek, validStudent));
    }

    @Test
    public void execute_similarModule_suggestSimilarCommands() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module similarModule = new ModuleBuilder().withModCode(SIMILAR_MOD_CODE).build();
        final Student validStudent = new StudentBuilder()
                .withModCode(SIMILAR_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(SIMILAR_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withStudents(new ArrayList<>(Arrays.asList(validStudent)))
                .build();
        modelStub.addModule(similarModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();

        CommandResult commandResult = new MarkAttendanceCommand(
                validModCode, validTutName, null, validWeek, validStudName).execute(modelStub);

        MarkAttendanceCommand expectedSuggestedCommand = new MarkAttendanceCommand(
                new ModCode(SIMILAR_MOD_CODE), validTutName, null, validWeek, validStudName);

        assertEquals(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                VALID_MOD_CODE + " " + VALID_TUT_NAME)
                + "1. " + SIMILAR_MOD_CODE + ", " + VALID_TUT_NAME + "\n",
                commandResult.getFeedbackToUser());
        assertEquals(modelStub.getSuggestedCommands().get(0), expectedSuggestedCommand);
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
                validModCode, validTutName, null, validWeek, validStudName);

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
                validModCode, validTutName, null, validWeek, validStudName);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () -> markAttendanceCommand.execute(modelStub));
    }

    @Test
    public void execute_similarStudent_suggestSimilarCommands() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student similarStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withName(SIMILAR_STUD_NAME)
                .build();
        final Student validStudent = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withName(VALID_STUD_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withStudents(new ArrayList<>())
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);
        modelStub.addStudent(similarStudent);
        modelStub.addStudentToTutorial(similarStudent);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, null, validWeek, validStudName);

        CommandResult commandResult = markAttendanceCommand.execute(modelStub);

        assertEquals(String.format(MESSAGE_SUGGESTED_CORRECTIONS, "Student",
                VALID_STUD_NAME) + "1. " + SIMILAR_STUD_NAME + "\n",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_multipleStudentsSameName_promptSelectStudent() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student studentOne = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withName(VALID_STUD_NAME)
                .withEmail("studentOne@gmail.com")
                .build();
        final Student studentTwo = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withName(VALID_STUD_NAME)
                .withEmail("studentTwo@gmail.com")
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withStudents(new ArrayList<>())
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);
        modelStub.addStudent(studentOne);
        modelStub.addStudentToTutorial(studentOne);
        modelStub.addStudent(studentTwo);
        modelStub.addStudentToTutorial(studentTwo);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = studentOne.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, null, validWeek, validStudName);

        CommandResult commandResult = markAttendanceCommand.execute(modelStub);

        assertEquals(String.format(MESSAGE_MULTIPLE_OF_SAME_NAME, studentOne.getName())
                        + "1. " + studentOne + "\n"
                        + "2. " + studentTwo + "\n",
                commandResult.getFeedbackToUser());
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
                .withStudents(new ArrayList<>())
                .build();
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, null, validWeek, validStudName);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_STUDENT_IN_TUTORIAL, () -> markAttendanceCommand.execute(modelStub));
    }

    @Test
    public void execute_noStudentsSupplied_storePendingCommands() throws CommandException {
        ModelStubWithPendingCommands modelStub = new ModelStubWithPendingCommands();

        final Module validModule = new ModuleBuilder().withModCode(VALID_MOD_CODE).build();
        final Student validStudentOne = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Student validStudentTwo = new StudentBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .withName(VALID_STUD_NAME)
                .build();
        final Tutorial validTutorial = new TutorialBuilder()
                .withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME)
                .build();
        final Week validWeek = new Week(3);
        modelStub.addModule(validModule);
        modelStub.addTutorial(validTutorial);
        modelStub.addTutorialToModule(validTutorial);
        modelStub.addStudent(validStudentOne);
        modelStub.addStudentToTutorial(validStudentOne);
        modelStub.addStudent(validStudentTwo);
        modelStub.addStudentToTutorial(validStudentTwo);
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                new ModCode(VALID_MOD_CODE), new TutName(VALID_TUT_NAME), null, validWeek, null);

        CommandResult expectedResult = new CommandResult(
                String.format(MESSAGE_MARK_ATTENDANCE_TUTORIAL, validTutorial.getTutName()));

        assertEquals(expectedResult, markAttendanceCommand.execute(modelStub));

        Stack<Command> expectedCommandStack = new Stack<>();

        expectedCommandStack.push(
                new MarkAttendanceVerifiedCommand(validTutorial, validWeek, validStudentTwo));
        expectedCommandStack.push(
                new DisplayCommand(
                        String.format(MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT, validStudentTwo.getName())));
        expectedCommandStack.push(
                new DisplayAttendanceCommand(validTutorial.getModCode(), validTutorial.getTutName()));
        expectedCommandStack.push(
                new MarkAttendanceVerifiedCommand(validTutorial, validWeek, validStudentOne));
        expectedCommandStack.push(
                new DisplayCommand(
                        String.format(MESSAGE_CONFIRM_MARK_ATTENDANCE_OF_STUDENT, validStudentOne.getName())));
        expectedCommandStack.push(
                new DisplayAttendanceCommand(validTutorial.getModCode(), validTutorial.getTutName()));

        assertEquals(expectedCommandStack, modelStub.getPendingCommands());
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();

        final ModCode validModCode = new ModCode(VALID_MOD_CODE);
        final TutName validTutName = new TutName(VALID_TUT_NAME);
        final Week validWeek = new Week(3);
        MarkAttendanceCommand markAliceCommand = new MarkAttendanceCommand(
                validModCode, validTutName, null, validWeek, alice.getName());
        MarkAttendanceCommand markBobCommand = new MarkAttendanceCommand(
            validModCode, validTutName, null, validWeek, bob.getName());

        // same object -> returns true
        assertTrue(markAliceCommand.equals(markAliceCommand));

        // same values -> returns true
        MarkAttendanceCommand markAliceCommandCopy = new MarkAttendanceCommand(
            validModCode, validTutName, null, validWeek, alice.getName());
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
        public void deleteSuggestedCommands() {}

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
        private List<Command> suggestedCommands = new ArrayList<>();

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
        public boolean hasStudentInTutorialAndModule(Name studName, TutName tutName, ModCode modCode) {
            for (Module module : modules) {
                if (!module.getModCode().equals(modCode)) {
                    continue;
                }
                for (Tutorial tutorial : module.getTutorials()) {
                    if (!tutorial.getTutName().equals(tutName)) {
                        continue;
                    }
                    for (Student student : tutorial.getStudents()) {
                        if (student.getName().equals(studName)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public ReadOnlyApplication getApplication() {
            return new Application();
        }

        @Override
        public void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {
            this.suggestedCommands = suggestedCommands;
        }
        @Override
        public List<Command> getSuggestedCommands() {
            return suggestedCommands;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(studentsAdded);
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorials);
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return FXCollections.observableArrayList(modules);
        }
    }

    /**
     * A ModelStub that also stores pending commands.
     */
    private class ModelStubWithPendingCommands extends ModelStubAcceptingStudentAdded {
        private Stack<Command> pendingCommands = new Stack<>();

        @Override
        public void storePendingCommand(Command command) {
            pendingCommands.push(command);
        }

        public Stack<Command> getPendingCommands() {
            return pendingCommands;
        }
    }

}
