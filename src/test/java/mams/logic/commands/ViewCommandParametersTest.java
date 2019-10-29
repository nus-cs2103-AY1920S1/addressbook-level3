package mams.logic.commands;

import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewCommandParametersTest {

    private ViewCommand.ViewCommandParameters params;

    @BeforeEach
    public void setUp() {
        params = new ViewCommand.ViewCommandParameters();
    }

    @Test
    public void isAtLeastOneParameterPresent() {

        // no parameters present
        assertFalse(params.isAtLeastOneParameterPresent());

        // appeal parameter present
        params.setAppealIndex(INDEX_FIRST);
        assertTrue(params.isAtLeastOneParameterPresent());

        // module parameter present
        params = new ViewCommand.ViewCommandParameters();
        params.setModuleIndex(INDEX_FIRST);
        assertTrue(params.isAtLeastOneParameterPresent());

        // student parameter present
        params = new ViewCommand.ViewCommandParameters();
        params.setStudentIndex(INDEX_FIRST);
        assertTrue(params.isAtLeastOneParameterPresent());

        // two parameters present
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        assertTrue(params.isAtLeastOneParameterPresent());

        // all parameters present
        params = new ViewCommand.ViewCommandParameters();
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);
        assertTrue(params.isAtLeastOneParameterPresent());
    }

    @Test
    public void settersAndGetters_setMultipleTimes_acceptLastValue() {
        // appeal index
        params.setAppealIndex(INDEX_FIRST);
        assertEquals(INDEX_FIRST, params.getAppealIndex().get());
        params.setAppealIndex(INDEX_SECOND);
        assertEquals(INDEX_SECOND, params.getAppealIndex().get());

        // module index
        params.setModuleIndex(INDEX_FIRST);
        assertEquals(INDEX_FIRST, params.getModuleIndex().get());
        params.setModuleIndex(INDEX_SECOND);
        assertEquals(INDEX_SECOND, params.getModuleIndex().get());

        // student index
        params.setStudentIndex(INDEX_FIRST);
        assertEquals(INDEX_FIRST, params.getStudentIndex().get());
        params.setStudentIndex(INDEX_SECOND);
        assertEquals(INDEX_SECOND, params.getStudentIndex().get());
    }

    @Test
    public void equals() {
        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndex(INDEX_FIRST);
        params.setStudentIndex(INDEX_FIRST);

        ViewCommand.ViewCommandParameters sameParams =
                new ViewCommand.ViewCommandParameters();
        sameParams.setAppealIndex(INDEX_FIRST);
        sameParams.setModuleIndex(INDEX_FIRST);
        sameParams.setStudentIndex(INDEX_FIRST);

        ViewCommand.ViewCommandParameters otherParams =
                new ViewCommand.ViewCommandParameters();
        otherParams.setAppealIndex(INDEX_FIRST);

        ViewCommand.ViewCommandParameters anotherParams =
                new ViewCommand.ViewCommandParameters();
        anotherParams.setAppealIndex(INDEX_FIRST);
        anotherParams.setModuleIndex(INDEX_FIRST);


        // same object -> returns true
        assertTrue(params.equals(params));

        // same values -> returns true
        assertTrue(params.equals(sameParams));

        // different types -> returns false
        assertFalse(params.equals(0));

        // null -> returns false
        assertFalse(params.equals(null));

        // different internal values -> returns false
        assertFalse(params.equals(otherParams));
        assertFalse(params.equals(anotherParams));
    }
}
