package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Application;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

public class MarkAttendanceVerifiedCommandTest {

    public static final String VALID_MOD_CODE = "ES1601";
    public static final String VALID_TUT_NAME = "T02";

    @Test
    public void execute_studentMarkedAsPresent_markAttendanceSuccessful() throws CommandException {
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

        final Week validWeek = new Week(3);
        final Name validStudName = validStudent.getName();

        // Mark attendance to flip from absent to present
        CommandResult commandResult = new MarkAttendanceVerifiedCommand(validTutorial, validWeek,
                validStudent).execute(modelStub);

        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS, validStudName, "present"),
                commandResult.getFeedbackToUser());
        assertTrue(validTutorial.getAttendance().isPresent(validWeek, validStudent));

        // Mark again to flip from present to absent
        commandResult = new MarkAttendanceVerifiedCommand(validTutorial, validWeek,
                validStudent).execute(modelStub);

        assertEquals(String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS, validStudName, "absent"),
                commandResult.getFeedbackToUser());
        assertFalse(validTutorial.getAttendance().isPresent(validWeek, validStudent));
    }

    /**
     * A Model stub that always accepts the student being added.
     */
    class ModelStubAcceptingStudentAdded extends ModelStub {
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
        public void storePendingCommand(Command command) {};

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
}
