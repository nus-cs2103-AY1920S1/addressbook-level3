package mams.logic.commands;

import static mams.commons.core.Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static mams.logic.commands.CommandTestUtil.assertCommandFailure;
import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.logic.commands.CommandTestUtil.showAll;
import static mams.logic.commands.CommandTestUtil.showAppealAtIndex;
import static mams.logic.commands.CommandTestUtil.showModuleAtIndex;
import static mams.logic.commands.CommandTestUtil.showStudentAtIndex;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_MAX_INT;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalIndexes.INDEX_THIRD;
import static mams.testutil.TypicalMams.getTypicalMams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 * Because {@code ViewCommand#verifyAllIndexesWithinBounds}
 */
public class ViewCommandTest {
    private Model model;
    private Model expectedModel;
    private ViewCommand.ViewCommandParameters params;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMams(), new UserPrefs());
        expectedModel = new ModelManager(model.getMams(), new UserPrefs());
        params = new ViewCommand.ViewCommandParameters();
    }

    @Test
    public void verifyAllIndexesWithinBounds_allWithinBound_success() {
        // if no parameters are present, then it should not throw an error
        // this is just a sanity check - ViewCommand#execute should have measures
        // to prevent acting on a empty ViewCommandParameter, which will be tested
        // in another test here.
        assertWithinBoundsSuccess(params, model);

        // module index is at one, appeal and student index not present -> success
        params.setModuleIndex(INDEX_FIRST);
        assertWithinBoundsSuccess(params, model);

        // student index is at one, module and appeal index not present -> success
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(INDEX_FIRST);
        assertWithinBoundsSuccess(params, model);

        // appeal index is at one, module and student index not present -> success
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        assertWithinBoundsSuccess(params, model);

        // appeal and module indexes are at one, student index not present -> success
        params.setModuleIndex(INDEX_FIRST);
        assertWithinBoundsSuccess(params, model);

        // all three indexes are one -> success
        params.setStudentIndex(INDEX_FIRST);
        assertWithinBoundsSuccess(params, model);

        // appeal index at the end of the list -> success
        params.setAppealIndex(Index.fromOneBased(model.getFilteredAppealList().size()));
        assertWithinBoundsSuccess(params, model);

        // all indexes at the end of their respective list indices -> success
        params.setModuleIndex(Index.fromOneBased(model.getFilteredModuleList().size()));
        params.setStudentIndex(Index.fromOneBased(model.getFilteredStudentList().size()));
        assertWithinBoundsSuccess(params, model);
    }

    @Test
    public void verifyAllIndexesWithinBounds_outOfBounds_exceptionThrown() {
        // appeal index out of bounds at an impossibly high number (Integer.MAX_VALUE)
        params.setAppealIndex(INDEX_MAX_INT);
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // appeal index just outside bounds of list (size of list + 1)
        params.setAppealIndex(Index.fromOneBased(model.getFilteredAppealList().size() + 1));
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // module index out of bounds at an impossibly high number (Integer.MAX_VALUE)
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(INDEX_MAX_INT);
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // module index just outside bounds of list (size of list + 1)
        params.setModuleIndex(Index.fromOneBased(model.getFilteredModuleList().size() + 1));
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // student index out of bounds at an impossibly high number (Integer.MAX_VALUE)
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(INDEX_MAX_INT);
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // student index just outside bounds of list (size of list + 1)
        params.setStudentIndex(Index.fromOneBased(model.getFilteredStudentList().size() + 1));
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));

        // all indexes out of bounds
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(Index.fromOneBased(model.getFilteredAppealList().size() + 1));
        params.setModuleIndex(Index.fromOneBased(model.getFilteredModuleList().size() + 1));
        params.setStudentIndex(Index.fromOneBased(model.getFilteredStudentList().size() + 1));
        assertThrows(AssertionError.class, () -> assertWithinBoundsSuccess(params, model));
    }

    /**
     * It is {@code ViewCommandParser}'s responsibility to check that the {@code ViewCommandParameter} object
     * contains at least one actionable Index. However, ViewCommand should defensively check the condition
     * as well.
     */
    @Test
    public void execute_viewCommandParameterHasNoIndexes_throwsException() {
        ViewCommand viewCommand = new ViewCommand(params);
        assertCommandFailure(viewCommand,
                model,
                ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_oneParameterWithinBounds_success() {
        // appeal index within bounds (testing first index) -> execute success
        params.setAppealIndex(INDEX_FIRST);
        showAppealAtIndex(expectedModel, INDEX_FIRST);
        String expectedAppealId = expectedModel.getFilteredAppealList().get(0).getAppealId();
        ViewCommand viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedAppealId),
                expectedModel);

        // appeal index within bounds (testing last index) -> execute success
        showAll(model);
        showAll(expectedModel);
        final Index appealLastIndex = Index.fromOneBased(expectedModel.getFilteredAppealList().size());
        params.setAppealIndex(appealLastIndex);
        showAppealAtIndex(expectedModel, appealLastIndex);
        expectedAppealId = expectedModel.getFilteredAppealList().get(0).getAppealId();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedAppealId),
                expectedModel);


        // module index within bounds (testing first index) -> execute success
        showAll(model);
        showAll(expectedModel);
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(INDEX_FIRST);
        showModuleAtIndex(expectedModel, INDEX_FIRST);
        String expectedModuleId = expectedModel.getFilteredModuleList().get(0).getModuleCode();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedModuleId),
                expectedModel);

        // module index within bounds (testing last index) -> execute success
        showAll(model);
        showAll(expectedModel);
        final Index moduleLastIndex = Index.fromOneBased(expectedModel.getFilteredModuleList().size());
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(moduleLastIndex);
        showModuleAtIndex(expectedModel, moduleLastIndex);
        expectedModuleId = expectedModel.getFilteredModuleList().get(0).getModuleCode();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedModuleId),
                expectedModel);

        // student index within bounds (testing first index) -> execute success
        showAll(model);
        showAll(expectedModel);
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(INDEX_FIRST);
        showStudentAtIndex(expectedModel, INDEX_FIRST);
        String expectedStudentId = expectedModel.getFilteredStudentList().get(0).getMatricId().toString();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedStudentId),
                expectedModel);

        // student index within bounds (testing last index) -> execute success
        showAll(model);
        showAll(expectedModel);
        final Index studentLastIndex = Index.fromOneBased(expectedModel.getFilteredStudentList().size());
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(studentLastIndex);
        showStudentAtIndex(expectedModel, studentLastIndex);
        expectedStudentId = expectedModel.getFilteredStudentList().get(0).getMatricId().toString();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedStudentId),
                expectedModel);
    }

    @Test
    public void execute_multipleParametersWithinBounds_success() {
        // appeal and module index within bounds -> execute success
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_SECOND);
        showAppealAtIndex(expectedModel, INDEX_FIRST);
        showModuleAtIndex(expectedModel, INDEX_SECOND);
        String expectedAppealId = expectedModel.getFilteredAppealList().get(0).getAppealId();
        String expectedModuleId = expectedModel.getFilteredModuleList().get(0).getModuleCode();
        ViewCommand viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedAppealId + " "
                        + expectedModuleId),
                expectedModel);

        // all indexes within bounds -> execute success
        showAll(model);
        params.setStudentIndex(INDEX_THIRD);
        showStudentAtIndex(expectedModel, INDEX_THIRD);
        String expectedStudentId = expectedModel.getFilteredStudentList().get(0).getMatricId().toString();
        viewCommand = new ViewCommand(params);
        assertCommandSuccess(viewCommand,
                model,
                String.format(ViewCommand.MESSAGE_VIEW_SUCCESS, expectedAppealId + " "
                        + expectedModuleId + " "
                        + expectedStudentId),
                expectedModel);

    }

    // for explanation of why no lists will be updated in event of failure,
    // refer to ViewCommand
    @Test
    public void execute_atLeastOneParameterOutOfBounds_exceptionThrownAndNoListsUpdated() {
        // student index out of bounds
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_MAX_INT); // out of bounds
        assertCommandFailure(new ViewCommand(params),
                model, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertEquals(model, expectedModel);

        // module index out of bounds
        params.setStudentIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_MAX_INT); // out of bounds
        assertCommandFailure(new ViewCommand(params),
                model, MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        assertEquals(model, expectedModel);

        // appeal index out of bounds
        params.setModuleIndex(INDEX_FIRST);
        params.setAppealIndex(INDEX_MAX_INT); // out of bounds
        assertCommandFailure(new ViewCommand(params),
                model, MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
        assertEquals(model, expectedModel);

        // both appeal and module index out of bounds
        params.setModuleIndex(INDEX_MAX_INT);
        assertCommandFailure(new ViewCommand(params),
                model, MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX + "\n"
                        + MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        assertEquals(model, expectedModel);

        // all indexes out of bounds
        params.setStudentIndex(INDEX_MAX_INT);
        assertCommandFailure(new ViewCommand(params),
                model, MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX + "\n"
                        + MESSAGE_INVALID_MODULE_DISPLAYED_INDEX + "\n"
                        + MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        assertEquals(model, expectedModel);
    }

    @Test
    public void equals() {
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        ViewCommand viewCommand = new ViewCommand(params);

        ViewCommand.ViewCommandParameters sameParams = new ViewCommand.ViewCommandParameters();
        sameParams.setAppealIndex(INDEX_FIRST);
        sameParams.setModuleIndex(INDEX_FIRST);
        sameParams.setStudentIndex(INDEX_FIRST);
        ViewCommand sameViewCommand = new ViewCommand(sameParams);

        ViewCommand.ViewCommandParameters otherParams = new ViewCommand.ViewCommandParameters();
        otherParams.setAppealIndex(INDEX_FIRST);
        otherParams.setModuleIndex(INDEX_FIRST);
        ViewCommand otherViewCommand = new ViewCommand(otherParams);

        ViewCommand.ViewCommandParameters otherParams1 = new ViewCommand.ViewCommandParameters();
        otherParams.setAppealIndex(INDEX_FIRST);
        otherParams.setModuleIndex(INDEX_FIRST);
        ViewCommand otherViewCommand1 = new ViewCommand(otherParams1);

        // same object -> returns true
        assertTrue(viewCommand.equals(viewCommand));

        // same values -> returns true
        assertTrue(viewCommand.equals(sameViewCommand));

        // different types -> returns false
        assertFalse(viewCommand.equals(1));

        // null -> returns false
        assertFalse(viewCommand.equals(null));

        // different internal values -> returns false
        assertFalse(viewCommand.equals(otherViewCommand));
        assertFalse(viewCommand.equals(otherViewCommand1));
    }

    /**
     * Runs {@code ViewCommand#verifyAllIndexesWithinBounds} on the given
     * {@code params} and {@code model}, and verify that it does not throw
     * an exception
     * @param params used to initialize the ViewCommand object
     * @param model acted on by the initialized ViewCommand object
     * @throws CommandException
     */
    public void assertWithinBoundsSuccess(ViewCommand.ViewCommandParameters params,
                                          Model model) {
        try {
            ViewCommand v = new ViewCommand(params);
            v.verifyAllIndexesWithinBounds(model.getFilteredAppealList(),
                    model.getFilteredModuleList(),
                    model.getFilteredStudentList());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of this method should not fail", ce);
        }
    }
}
