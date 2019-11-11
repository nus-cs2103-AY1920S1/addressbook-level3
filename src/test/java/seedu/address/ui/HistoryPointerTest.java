package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HistoryPointerTest {

    private static final String CLEAR_COMMAND = "clear";
    private static final String EXPENSE_COMMAND = "expense";
    private static final String DUMMY_COMMAND = "dummy";
    private List<String> pointerElements;
    private HistoryPointer pointer;

    @BeforeEach
    public void setUp() {
        pointerElements = new ArrayList<>();
        pointerElements.add(CLEAR_COMMAND);
        pointerElements.add(EXPENSE_COMMAND);
    }

    @Test
    public void historyPointer_defensiveCopy_unmodifiedList() {
        List<String> list = new ArrayList<>();
        pointer = new HistoryPointer(list);
        list.add(CLEAR_COMMAND);

        HistoryPointer historyPointer = new HistoryPointer(new ArrayList<>());
        assertEquals(historyPointer, pointer);
    }

    @Test
    public void emptyList() {
        pointer = new HistoryPointer(new ArrayList<>());
        hasNoCurrentHistory_failure();
        hasNoPrevHistory_failure();
        hasNoNextHistory_failure();

        pointer.add(CLEAR_COMMAND);
        hasNextHistory_success(CLEAR_COMMAND);
    }

    @Test
    public void singleElementList() {
        List<String> list = new ArrayList<>();
        list.add(CLEAR_COMMAND);
        pointer = new HistoryPointer(list);

        hasNoPrevHistory_failure();
        hasNoNextHistory_failure();
        hasCurrentHistory_success(CLEAR_COMMAND);
        hasCurrentHistory_success(CLEAR_COMMAND);
        hasCurrentHistory_success(CLEAR_COMMAND);

        pointer.add(EXPENSE_COMMAND);
        hasNextHistory_success(EXPENSE_COMMAND);
    }

    @Test
    public void multipleElementsList() {
        pointer = new HistoryPointer(pointerElements);
        pointer.add(DUMMY_COMMAND);

        hasCurrentHistory_success(EXPENSE_COMMAND);

        hasNextHistory_success(DUMMY_COMMAND);
        hasNoNextHistory_failure();

        hasPrevHistory_success(EXPENSE_COMMAND);
        hasPrevHistory_success(CLEAR_COMMAND);
        hasNoPrevHistory_failure();
    }

    @Test
    public void equals() {
        HistoryPointer firstHistoryPointer = new HistoryPointer(pointerElements);

        // same object -> returns true
        assertEquals(firstHistoryPointer, firstHistoryPointer);

        // same values -> returns true
        HistoryPointer firstPointerCopy = new HistoryPointer(pointerElements);
        assertEquals(firstHistoryPointer, firstPointerCopy);

        // different types -> returns false
        assertNotEquals(1, firstHistoryPointer);

        // null -> returns false
        assertNotEquals(null, firstHistoryPointer);

        // different elements -> returns false
        HistoryPointer differentElementPointer = new HistoryPointer(Collections.singletonList(EXPENSE_COMMAND));
        assertNotEquals(firstHistoryPointer, differentElementPointer);

        // different index -> returns false
        firstPointerCopy.previous();
        assertNotEquals(firstHistoryPointer, firstPointerCopy);
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns true and the return value
     * of {@code pointer#next()} equals to {@code element}.
     */
    private void hasNextHistory_success(String element) {
        assertTrue(pointer.hasNext());
        assertEquals(element, pointer.next());
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns true and the return value
     * of {@code pointer#previous()} equals to {@code element}.
     */
    private void hasPrevHistory_success(String element) {
        assertTrue(pointer.hasPrevious());
        assertEquals(element, pointer.previous());
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns true and the return value
     * of {@code pointer#current()} equals to {@code element}.
     */
    private void hasCurrentHistory_success(String element) {
        assertTrue(pointer.hasCurrent());
        assertEquals(element, pointer.current());
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns false.
     */
    private void hasNoNextHistory_failure() {
        assertFalse(pointer.hasNext());
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns false.
     */
    private void hasNoPrevHistory_failure() {
        assertFalse(pointer.hasPrevious());
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns false.
     */
    private void hasNoCurrentHistory_failure() {
        assertFalse(pointer.hasCurrent());
    }
}
