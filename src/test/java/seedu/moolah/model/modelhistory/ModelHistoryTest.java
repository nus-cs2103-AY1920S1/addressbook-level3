package seedu.moolah.model.modelhistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TestUtil.makeModelChangesStack;

import java.util.Optional;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Hybrid (Unit and Integration) tests for ModelHistory.
 */
public class ModelHistoryTest {

    private ModelHistory history;

    @BeforeEach
    public void setup() {
        history = new ModelHistory();
    }

    @Test
    public void emptyConstructor() {
        assertTrue(history.isPastChangesEmpty());
        assertTrue(history.isFutureChangesEmpty());
    }

    @Test
    public void copyConstructor() {
        assertEquals(history, new ModelHistory(history));
    }

    @Test
    public void copy() {
        assertEquals(history, history.copy());
    }

    @Test
    public void resetData_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.resetData(null));
    }

    @Test
    public void resetData_success() {
        ModelHistory other = new ModelHistory(makeModelChangesStack("past"), makeModelChangesStack("future"));
        history.resetData(other);
        assertEquals(other, history);
    }

    @Test
    public void setPastChanges_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.setPastChanges(null));
    }

    @Test
    public void setPastChanges_success() {
        Stack<ModelChanges> pastChanges = makeModelChangesStack("test");
        history.setPastChanges(pastChanges);
        assertEquals(pastChanges, history.getPastChanges());
    }

    @Test
    public void setFutureChanges_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.setFutureChanges(null));
    }

    @Test
    public void setFutureChanges_success() {
        Stack<ModelChanges> futureChanges = makeModelChangesStack("test");
        history.setFutureChanges(futureChanges);
        assertEquals(futureChanges, history.getFutureChanges());
    }

    @Test
    public void addToPastChanges_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.addToPastChanges(null));
    }

    @Test
    public void addToPastChanges_success() {
        String changeMessage = "test";
        ModelChanges changes = new ModelChanges(changeMessage);
        history.addToPastChanges(changes);
        assertEquals(makeModelChangesStack(changeMessage), history.getPastChanges());
    }

    @Test
    public void addToFutureChanges_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.addToPastChanges(null));
    }

    @Test
    public void addToFutureChanges_success() {
        String changeMessage = "test";
        ModelChanges changes = new ModelChanges(changeMessage);
        history.addToFutureChanges(changes);
        assertEquals(makeModelChangesStack(changeMessage), history.getFutureChanges());
    }

    @Test
    public void clearFutureModels_success() {
        history.clearFutureChanges();
        assertTrue(history.isFutureChangesEmpty());
    }

    @Test
    public void getPrevChanges_noChanges_returnsEmptyOptional() {
        assertTrue(history.getPrevChanges().isEmpty());
    }

    @Test
    public void getPrevChanges_hasChanges_returnsChangesOptional() {
        String changeMessage = "test";
        ModelChanges changes = new ModelChanges(changeMessage);
        history.addToPastChanges(changes);
        Optional<ModelChanges> prevChanges = history.getPrevChanges();
        assertTrue(prevChanges.isPresent());
        assertEquals(changes, prevChanges.get());
    }

    @Test
    public void getNextChanges_noChanges_returnsEmptyOptional() {
        assertTrue(history.getNextChanges().isEmpty());
    }

    @Test
    public void getNextChanges_hasChanges_returnsChangesOptional() {
        String changeMessage = "test";
        ModelChanges changes = new ModelChanges(changeMessage);
        history.addToFutureChanges(changes);
        Optional<ModelChanges> nextChanges = history.getNextChanges();
        assertTrue(nextChanges.isPresent());
        assertEquals(changes, nextChanges.get());
    }

    @Test
    public void equals() {
        Stack<ModelChanges> oneChangesInStack = makeModelChangesStack("dummy");

        // To same object
        assertEquals(history, history);
        // To null
        assertNotEquals(null, history);
        // To objects of other classes
        assertFalse(history.equals(1));
        // To ModelHistory objects but different in past changes
        assertNotEquals(history, new ModelHistory(oneChangesInStack, history.getFutureChanges()));
        // To ModelHistory objects but different in future changes
        assertNotEquals(history, new ModelHistory(history.getPastChanges(), oneChangesInStack));
        // To ModelHistory objects but different in past and future models
        assertNotEquals(history, new ModelHistory(oneChangesInStack, oneChangesInStack));
        // To ModelHistory objects and equal in both models
        assertEquals(history, new ModelHistory(history.getPastChanges(), history.getFutureChanges()));
    }

    @Test
    public void toString_success() {
        assertEquals("0 past and 0 future changes", history.toString());
    }
}
