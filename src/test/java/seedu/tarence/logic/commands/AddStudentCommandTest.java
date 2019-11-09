package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class AddStudentCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1061";
    public static final String VALID_TUT_NAME = "T02";
    public static final String SIMILAR_TUT_NAME = "T03";
    public static final Index VALID_TUT_INDEX = Index.fromOneBased(1);


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void indexConstructor_nullPerson_throwsNullPointerException() {
        Student validStudent = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(validStudent, null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(VALID_MOD_CODE).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(VALID_MOD_CODE).withTutName(VALID_TUT_NAME).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(VALID_MOD_CODE).withTutName(VALID_TUT_NAME).build());
        Student validStudent = new StudentBuilder().withModCode(VALID_MOD_CODE).withTutName(VALID_TUT_NAME).build();

        CommandResult commandResult = new AddStudentCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_similarModuleSuggested_promptSuggestionSelection() throws Exception {
        ModelStubStudentCommand modelStub = new ModelStubStudentCommand();

        ModuleBuilder.DEFAULT_TUTORIALS.clear();
        Module similarModule = new ModuleBuilder().withModCode(SIMILAR_MOD_CODE).build();
        Tutorial validTutorial = new TutorialBuilder().withModCode(SIMILAR_MOD_CODE).withTutName(VALID_TUT_NAME)
                .build();
        similarModule.addTutorial(validTutorial);
        modelStub.addModule(similarModule);
        modelStub.addTutorial(validTutorial);

        List<Command> suggestedCommands = new ArrayList<>();
        suggestedCommands.add(new AddStudentCommand(
                new StudentBuilder().withModCode(SIMILAR_MOD_CODE).withTutName(VALID_TUT_NAME).build()));

        CommandResult commandResult = new AddStudentCommand(new StudentBuilder().withModCode(VALID_MOD_CODE)
                .withTutName(VALID_TUT_NAME).build()).execute(modelStub);
        String expectedMessage = String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                VALID_MOD_CODE + " " + VALID_TUT_NAME) + "1. " + SIMILAR_MOD_CODE + ", " + VALID_TUT_NAME + "\n";

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(suggestedCommands, modelStub.getSuggestedCommands());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(new StudentBuilder().build());
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class,
                AddStudentCommand.MESSAGE_DUPLICATE_STUDENT, () -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void execute_studentAcceptedByIndexFormat_addSuccessful() throws Exception {
        final String validModCode = "ES1601";
        final String validTutName = "T02";
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(validModCode).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        Student validStudent = new StudentBuilder().withModCode(validModCode).withTutName(validTutName).build();

        CommandResult commandResult = new AddStudentCommand(validStudent, VALID_TUT_INDEX).execute(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, validStudent),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_tutorialIndexOutOfBounds_throwsCommandException() {
        final String validModCode = "ES1601";
        final String validTutName = "T02";
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        modelStub.addModule(new ModuleBuilder().withModCode(validModCode).build());
        modelStub.addTutorial(new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());
        modelStub.addTutorialToModule(
                new TutorialBuilder().withModCode(validModCode).withTutName(validTutName).build());

        Student bob = new StudentBuilder().withName("Bob").build();
        Index outOfBoundsTutorialIndex = Index.fromOneBased(100);
        AddStudentCommand addStudentCommand = new AddStudentCommand(bob, outOfBoundsTutorialIndex);
        String tutorialIndexOutOfBoundsMessage =
                String.format(AddStudentCommand.MESSAGE_TUTORIAL_IDX_OUT_OF_BOUNDS,
                        outOfBoundsTutorialIndex.getOneBased());


        assertThrows(CommandException.class,
                tutorialIndexOutOfBoundsMessage, () -> addStudentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(Arrays.asList(student));
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
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
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(studentsAdded);
        }

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

    private class ModelStubStudentCommand extends ModelStub {
        final ArrayList<Module> modules = new ArrayList<>();
        final ArrayList<Tutorial> tutorials = new ArrayList<>();
        final ArrayList<Student> students = new ArrayList<>();
        private List<Command> suggestedCommands = new ArrayList<>();

        @Override
        public void storePendingCommand(Command command) {
            // TODO Auto-generated method stub
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(students);
        }

        @Override
        public void addModule(Module module) {
            modules.add(module);
        }

        @Override
        public void addTutorial(Tutorial tutorial) {
            tutorials.add(tutorial);
        }

        @Override
        public void addStudent(Student student) {
            students.add(student);
        }

        @Override
        public boolean hasModuleOfCode(ModCode modCode) {
            for (Module module : modules) {
                if (module.getModCode().equals(modCode)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            requireAllNonNull(modCode, tutName);
            Module module = null;
            for (Module currModule : modules) {
                if (currModule.getModCode().equals(modCode)) {
                    module = currModule;
                    break;
                }
            }
            if (module == null) {
                return false;
            }
            boolean hasTut = false;
            for (Tutorial tutorial : module.getTutorials()) {
                if (tutorial.getTutName().equals(tutName)) {
                    hasTut = true;
                    break;
                }
            }
            return hasTut;
        }

        @Override
        public void addTutorialToModule(Tutorial tutorial) {
            for (Module module : modules) {
                if (module.getModCode().equals(tutorial.getModCode())) {
                    module.addTutorial(tutorial);
                    break;
                }
            }
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            ObservableList<Module> list = FXCollections.observableArrayList();
            list.addAll(modules);
            return list;
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            ObservableList<Tutorial> list = FXCollections.observableArrayList();
            list.addAll(tutorials);
            return list;
        }

        @Override
        public void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {
            this.suggestedCommands = suggestedCommands;
        }

        @Override
        public List<Command> getSuggestedCommands() {
            return suggestedCommands;
        }

    }

}
