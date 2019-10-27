package seedu.tarence.logic.commands.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

public class DeleteAssignmentCommandTest {
    public static final String VALID_MOD_CODE = "ES1601";
    public static final String SIMILAR_MOD_CODE = "ES1061";
    public static final String VALID_TUT_NAME = "T02";
    public static final String SIMILAR_TUT_NAME = "T03";
    public static final Index VALID_TUT_INDEX = Index.fromOneBased(1);
    public static final Index VALID_ASSIGN_INDEX = Index.fromOneBased(1);
    public static final Supplier<Tutorial> VALID_TUTORIAL_SUPPLIER = () -> new TutorialBuilder()
            .withModCode(VALID_MOD_CODE)
            .withTutName(VALID_TUT_NAME)
            .build();
    public static final Supplier<Module> VALID_MODULE_SUPPLIER = () -> new ModuleBuilder()
            .withModCode(VALID_MOD_CODE)
            .withTutorials(Arrays.asList(VALID_TUTORIAL_SUPPLIER.get()))
            .build();
    public static final String VALID_ASSIGN_NAME = "Assignment Name";
    public static final Integer VALID_MAX_SCORE = 10;
    public static final Date VALID_START_DATE = new Date(0);
    public static final Date VALID_END_DATE = new Date();

    @Test
    public void execute_tutNameModCodeFormat_deleteAssignmentSuccessful() throws CommandException {
        ModelStubDeleteAssignmentCommand modelStub = new ModelStubDeleteAssignmentCommand();
        modelStub.getFilteredTutorialList()
                .get(0)
                .addAssignment(new Assignment(
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE));

        CommandResult commandResult = new DeleteAssignmentCommand(
                new ModCode(VALID_MOD_CODE),
                new TutName(VALID_TUT_NAME),
                null,
                null,
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE).execute(modelStub);

        assertEquals(String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                VALID_ASSIGN_NAME),
                commandResult.getFeedbackToUser());
        assertThrows(IndexOutOfBoundsException.class, () ->
                modelStub.getFilteredTutorialList().get(0).getAssignment(Index.fromOneBased(1)));
    }

    @Test
    public void execute_tutIndexFormat_addAssignmentSuccessful() throws CommandException {
        ModelStubDeleteAssignmentCommand modelStub = new ModelStubDeleteAssignmentCommand();
        modelStub.getFilteredTutorialList()
                .get(0)
                .addAssignment(new Assignment(
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE));

        CommandResult commandResult = new DeleteAssignmentCommand(
                null,
                null,
                VALID_TUT_INDEX,
                null,
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE).execute(modelStub);

        assertEquals(String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                VALID_ASSIGN_NAME),
                commandResult.getFeedbackToUser());
        assertThrows(IndexOutOfBoundsException.class, () ->
                modelStub.getFilteredTutorialList().get(0).getAssignment(Index.fromOneBased(1)));
    }

    @Test
    public void execute_assignIndexFormat_addAssignmentSuccessful() throws CommandException {
        ModelStubDeleteAssignmentCommand modelStub = new ModelStubDeleteAssignmentCommand();
        modelStub.getFilteredTutorialList()
                .get(0)
                .addAssignment(new Assignment(
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE));

        CommandResult commandResult = new DeleteAssignmentCommand(
                null,
                null,
                VALID_TUT_INDEX,
                VALID_ASSIGN_INDEX,
                null,
                null,
                null,
                null).execute(modelStub);

        assertEquals(String.format(DeleteAssignmentCommand.MESSAGE_DELETE_ASSIGNMENT_SUCCESS,
                VALID_ASSIGN_NAME),
                commandResult.getFeedbackToUser());
        assertThrows(IndexOutOfBoundsException.class, () ->
                modelStub.getFilteredTutorialList().get(0).getAssignment(Index.fromOneBased(1)));
    }

    @Test
    public void execute_similarModCode_suggestedCommands() throws CommandException {
        ModelStubDeleteAssignmentCommand modelStub = new ModelStubDeleteAssignmentCommand();

        CommandResult commandResult = new DeleteAssignmentCommand(
                new ModCode(SIMILAR_MOD_CODE),
                new TutName(VALID_TUT_NAME),
                null,
                null,
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE).execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_SUGGESTED_CORRECTIONS, "Tutorial",
                SIMILAR_MOD_CODE + " " + VALID_TUT_NAME)
                + "1. " + VALID_MOD_CODE + ", " + VALID_TUT_NAME + "\n",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_suggestedCommandsNotFound_throwsCommandException() throws CommandException {
        ModelStubDeleteAssignmentCommand modelStub = new ModelStubDeleteAssignmentCommand();

        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(
                new ModCode(SIMILAR_MOD_CODE),
                new TutName(SIMILAR_TUT_NAME),
                null,
                null,
                VALID_ASSIGN_NAME,
                VALID_MAX_SCORE,
                VALID_START_DATE,
                VALID_END_DATE);

        assertThrows(CommandException.class, () ->
                deleteAssignmentCommand.execute(modelStub),
                Messages.MESSAGE_INVALID_TUTORIAL_IN_MODULE);
    }

    private class ModelStubDeleteAssignmentCommand extends ModelStub {
        private List<Module> moduleList = new ArrayList<>(Arrays.asList(VALID_MODULE_SUPPLIER.get()));
        private List<Tutorial> tutorialList = new ArrayList<>(Arrays.asList(VALID_TUTORIAL_SUPPLIER.get()));
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
