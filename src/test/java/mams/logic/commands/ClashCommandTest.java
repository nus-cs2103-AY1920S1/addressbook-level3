package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.logic.commands.CommandTestUtil.assertCommandFailure;

import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_FOURTH;
import static mams.testutil.TypicalIndexes.INDEX_MAX_INT;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalIndexes.INDEX_THIRD;
import static mams.testutil.TypicalModules.CS1010;
import static mams.testutil.TypicalModules.CS1020;
import static mams.testutil.TypicalModules.CS1231;
import static mams.testutil.TypicalModules.CS2040;

import static mams.testutil.TypicalMams.getTypicalMams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

import mams.model.module.Module;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ClashCommandTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());

    @Test
    public void equal() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        params.setModuleCodes("cs1010", "cs1020");
        params.setStudentIndex(INDEX_FIRST);

        ClashCommand command = new ClashCommand(params);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        ClashCommand commandCopy = new ClashCommand(params);
        assertTrue(command.equals(commandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));
        assertFalse(command.equals("abc"));

        // null -> returns false
        assertFalse(command.equals(null));

        ClashCommand.ClashCommandParameters anotherParams = new ClashCommand.ClashCommandParameters();

        // different appeal index -> returns false
        anotherParams.setAppealIndex(INDEX_SECOND);
        anotherParams.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        anotherParams.setModuleCodes("cs1010", "cs1020");
        anotherParams.setStudentIndex(INDEX_FIRST);
        ClashCommand secondCommand = new ClashCommand(anotherParams);
        assertFalse(command.equals(secondCommand));

        // different module indices -> returns false
        anotherParams.setAppealIndex(INDEX_FIRST);
        anotherParams.setModuleIndices(INDEX_FIRST, INDEX_THIRD);
        secondCommand = new ClashCommand(anotherParams);
        assertFalse(command.equals(secondCommand));

        // different module codes -> returns false
        anotherParams.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        anotherParams.setModuleCodes("cs1010", "cs2030");
        secondCommand = new ClashCommand(anotherParams);
        assertFalse(command.equals(secondCommand));

        // different student index -> returns false
        anotherParams.setModuleCodes("cs1010", "cs1020");
        anotherParams.setStudentIndex(INDEX_SECOND);
        secondCommand = new ClashCommand(anotherParams);
        assertFalse(command.equals(secondCommand));
    }

    @Test
    public void execute_validAppealIndex_success() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        ClashCommand command = new ClashCommand(params);

        // add module appeal -> has clash, show clash details
        params.setAppealIndex(INDEX_FIRST);
        String expectedMessage = ClashCommand.MESSAGE_CLASH_DETECTED;
        expectedMessage += getClashDetails(CS1231, CS1010);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // add module appeal -> no clash, show message
        params.setAppealIndex(INDEX_FOURTH);
        assertCommandSuccess(command,
                model,
                ClashCommand.MESSAGE_CLASH_NOT_DETECTED,
                expectedModel);

        // drop module appeal
        params.setAppealIndex(INDEX_SECOND);
        expectedMessage = ClashCommand.MESSAGE_CLASH_DETECTED;
        expectedMessage += getClashDetails(CS1231, CS1010);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // appeal at index is not an add/drop module appeal
        params.setAppealIndex(INDEX_THIRD);
        assertCommandSuccess(command, model, ClashCommand.MESSAGE_NO_NEED_TO_CHECK_CLASH, expectedModel);
    }

    @Test
    public void execute_invalidAppealIndex_throwsInvalidIndexException() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        params.setAppealIndex(INDEX_MAX_INT);
        ClashCommand command = new ClashCommand(params);

        // index > size of current appeal list
        assertCommandFailure(command, model, ClashCommand.MESSAGE_INVALID_INDEX);

    }

    @Test
    public void execute_validModuleIndices_success() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        ClashCommand command = new ClashCommand(params);

        // clash detected
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        String expectedMessage = ClashCommand.MESSAGE_CLASH_DETECTED;
        expectedMessage += getClashDetails(CS1010, CS1231);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // no clash detect
        params.setModuleIndices(INDEX_FIRST, INDEX_THIRD);
        assertCommandSuccess(command, model, ClashCommand.MESSAGE_CLASH_NOT_DETECTED, expectedModel);
    }

    @Test
    public void execute_atLeastOneInvalidModuleIndices_throwsInvalidIndexException() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        ClashCommand command = new ClashCommand(params);

        // first index > size of current module list
        params.setModuleIndices(INDEX_MAX_INT, INDEX_SECOND);
        assertCommandFailure(command, model, ClashCommand.MESSAGE_INVALID_INDEX);

        // second index > size of current module list
        params.setModuleIndices(INDEX_FIRST, INDEX_MAX_INT);
        assertCommandFailure(command, model, ClashCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_duplicateModuleIndices_throwsException() {}

    @Test
    public void execute_validModuleCodes_success() {}

    @Test
    public void execute_atLeastOneInvalidModuleCodes_throwsModuleNotFoundException() {

        // -> module not found

    }

    @Test
    public void execute_duplicateModuleCodes_throwsException() {}

    @Test
    public void execute_validStudentIndex_success() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();

        // clash detected
        params.setStudentIndex(INDEX_SECOND);
        ClashCommand command = new ClashCommand(params);
        String expectedMessage = ClashCommand.MESSAGE_CLASH_DETECTED;
        expectedMessage += getClashDetails(CS1231, CS1010);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // no clash detected
        params.setStudentIndex(INDEX_FIRST);
        assertCommandSuccess(command,
                model,
                ClashCommand.MESSAGE_CLASH_NOT_DETECTED,
                expectedModel);
    }

    @Test
    public void execute_invalidStudentIndex_throwsInvalidIndexException() {

        ClashCommand.ClashCommandParameters params = new ClashCommand.ClashCommandParameters();
        params.setStudentIndex(INDEX_MAX_INT);
        ClashCommand command = new ClashCommand(params);

        // index > size of current student list
        assertCommandFailure(command, model, ClashCommand.MESSAGE_INVALID_INDEX);

    }

    /*

     */
    private String getClashDetails(Module firstModule, Module secondModule) {
        String details = firstModule.getModuleCode();
        details += "  ";
        details += secondModule.getModuleCode();
        details += "\n";
        details += firstModule.getModuleTimeTableToString();
        details += "\n";
        return details;
    }

}
