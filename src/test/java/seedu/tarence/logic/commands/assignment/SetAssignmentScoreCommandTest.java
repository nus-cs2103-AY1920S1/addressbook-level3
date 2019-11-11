package seedu.tarence.logic.commands.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.Command;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.ModelStub;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.builder.ModuleBuilder;
import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class SetAssignmentScoreCommandTest {
    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1061";
    public static final String VALID_TUT_NAME = "T02";
    public static final String SIMILAR_TUT_NAME = "T03";
    public static final Index VALID_TUT_INDEX = Index.fromOneBased(1);
    public static final Index VALID_ASSIGN_INDEX = Index.fromOneBased(1);
    public static final Index VALID_STUDENT_INDEX = Index.fromOneBased(1);
    public static final String VALID_ASSIGN_NAME = "Assignment Name";
    public static final Integer VALID_MAX_SCORE = 10;
    public static final Integer VALID_SCORE = 0;
    public static final Date VALID_START_DATE = new Date(0);
    public static final Date VALID_END_DATE = new Date();
    public static final Assignment VALID_ASSIGNMENT = new Assignment(
            VALID_ASSIGN_NAME,
            VALID_MAX_SCORE,
            VALID_START_DATE,
            VALID_END_DATE);
    public static final Supplier<Tutorial> VALID_TUTORIAL_SUPPLIER = () -> new TutorialBuilder()
            .withModCode(VALID_MOD_CODE)
            .withTutName(VALID_TUT_NAME)
            .withStudents(Arrays.asList(new StudentBuilder().build()))
            .withAssignments(Arrays.asList(VALID_ASSIGNMENT))
            .build();
    public static final Supplier<Module> VALID_MODULE_SUPPLIER = () -> new ModuleBuilder()
            .withModCode(VALID_MOD_CODE)
            .withTutorials(Arrays.asList(VALID_TUTORIAL_SUPPLIER.get()))
            .build();

    @Test
    public void execute_tutNameModCodeFormat_setAssignmentScoreSuccessful() throws CommandException {
        ModelStubSetAssignmentScoreCommand modelStub = new ModelStubSetAssignmentScoreCommand();
        CommandResult commandResult = new SetAssignmentScoreCommand(
                new ModCode(VALID_MOD_CODE),
                new TutName(VALID_TUT_NAME),
                null,
                VALID_ASSIGN_INDEX,
                VALID_STUDENT_INDEX,
                VALID_SCORE).execute(modelStub);

        assertEquals(String.format(SetAssignmentScoreCommand.MESSAGE_SET_SCORE_SUCCESS,
                new StudentBuilder().build().getName(),
                VALID_SCORE),
                commandResult.getFeedbackToUser());
        assertEquals(modelStub.getFilteredTutorialList()
                .get(0)
                .getAssignmentScore(VALID_ASSIGNMENT,
                new StudentBuilder().build()),
                VALID_SCORE);
    }

    @Test
    public void execute_tutIndexFormat_addAssignmentSuccessful() throws CommandException {
        ModelStubSetAssignmentScoreCommand modelStub = new ModelStubSetAssignmentScoreCommand();
        CommandResult commandResult = new SetAssignmentScoreCommand(
                null,
                null,
                VALID_TUT_INDEX,
                VALID_ASSIGN_INDEX,
                VALID_STUDENT_INDEX,
                VALID_SCORE).execute(modelStub);

        assertEquals(String.format(SetAssignmentScoreCommand.MESSAGE_SET_SCORE_SUCCESS,
                new StudentBuilder().build().getName(),
                VALID_SCORE),
                commandResult.getFeedbackToUser());
        assertEquals(modelStub.getFilteredTutorialList()
                .get(0)
                .getAssignmentScore(VALID_ASSIGNMENT,
                new StudentBuilder().build()),
                VALID_SCORE);
    }

    @Test
    public void execute_similarModCode_suggestedCommands() throws CommandException {
        ModelStubSetAssignmentScoreCommand modelStub = new ModelStubSetAssignmentScoreCommand();
        CommandResult commandResult = new SetAssignmentScoreCommand(
                new ModCode(SIMILAR_MOD_CODE),
                new TutName(VALID_TUT_NAME),
                null,
                VALID_ASSIGN_INDEX,
                VALID_STUDENT_INDEX,
                VALID_SCORE).execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                SIMILAR_MOD_CODE + " " + VALID_TUT_NAME)
                + "1. " + VALID_MOD_CODE + ", " + VALID_TUT_NAME + "\n",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_suggestedCommandsNotFound_throwsCommandException() throws CommandException {
        ModelStubSetAssignmentScoreCommand modelStub = new ModelStubSetAssignmentScoreCommand();
        SetAssignmentScoreCommand setAssignmentScoreCommand = new SetAssignmentScoreCommand(
                new ModCode(SIMILAR_MOD_CODE),
                new TutName(SIMILAR_TUT_NAME),
                null,
                VALID_ASSIGN_INDEX,
                VALID_STUDENT_INDEX,
                VALID_SCORE);

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE, () ->
            setAssignmentScoreCommand.execute(modelStub));
    }

    private class ModelStubSetAssignmentScoreCommand extends ModelStub {
        private List<Module> moduleList = new ArrayList<>(Arrays.asList(VALID_MODULE_SUPPLIER.get()));
        private List<Tutorial> tutorialList = new ArrayList<>(Arrays.asList(VALID_TUTORIAL_SUPPLIER.get()));
        private List<Student> studentList = new ArrayList<>(Arrays.asList(new StudentBuilder().build()));
        private Deque<Command> pendingCommands = new LinkedList<>();
        private List<Command> suggestedCommands = new ArrayList<>();
        private String suggestedCorrections;

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return FXCollections.observableArrayList(moduleList);
        }

        @Override
        public ObservableList<Tutorial> getFilteredTutorialList() {
            return FXCollections.observableArrayList(tutorialList);
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableArrayList(studentList);
        }

        @Override
        public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
            Module module = moduleList
                    .stream()
                    .filter(mod -> mod.getModCode().equals(modCode))
                    .findFirst()
                    .orElse(null);

            if (module == null) {
                return false;
            }

            Tutorial tutorial = module.getTutorials()
                    .stream()
                    .filter(tut -> tut.getTutName().equals(tutName))
                    .findFirst()
                    .orElse(null);

            return tutorial != null;
        }

        @Override
        public void storePendingCommand(Command command) {
            pendingCommands.addFirst(command);
        }

        @Override
        public void storeSuggestedCommands(List<Command> suggestedCommands, String suggestedCorrections) {
            this.suggestedCommands = suggestedCommands;
            this.suggestedCorrections = suggestedCorrections;
        }
    }
}
