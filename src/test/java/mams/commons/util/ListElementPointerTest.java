package mams.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Although the ListElementPointer is primarily used to iterate a List of InputOutput
 * objects in MAMS, the unit tests will focus only on seeing if it works with String since
 * it is not within the scope of a ListElementPointer unit test to check if it works with InputOutput.
 */
public class ListElementPointerTest {
    private static final String FIRST_ELEMENT = "first";
    private static final String SECOND_ELEMENT = "second";

    private List<String> stringElements;
    private ListElementPointer<String> stringPointer;

    @BeforeEach
    public void setUp() {
        stringElements = new ArrayList<>();
        stringElements.add(FIRST_ELEMENT);
        stringElements.add(SECOND_ELEMENT);
    }

    @Test
    public void constructor_defensiveCopy_backingListUnmodified() {
        List<String> list = new ArrayList<>();
        stringPointer = new ListElementPointer<String>(list);
        list.add(FIRST_ELEMENT);
        ListElementPointer<String> emptyPointer = new ListElementPointer<>(Collections.emptyList());
        assertEquals(emptyPointer, stringPointer);
    }

    @Test
    public void emptyList() {
        stringPointer = new ListElementPointer<String>(new ArrayList<>());
        assertCurrentFailure();
        assertPreviousFailure();
        assertNextFailure();
        stringPointer.add(FIRST_ELEMENT);
        assertNextSuccess(FIRST_ELEMENT);
    }

    @Test
    public void singleElementList() {
        List<String> list = new ArrayList<>();
        list.add(FIRST_ELEMENT);
        stringPointer = new ListElementPointer<String>(list);

        assertCurrentSuccess(FIRST_ELEMENT);
        assertPreviousFailure();
        assertCurrentSuccess(FIRST_ELEMENT);
        assertNextFailure();
        assertCurrentSuccess(FIRST_ELEMENT);

        stringPointer.add(SECOND_ELEMENT);
        assertNextSuccess(SECOND_ELEMENT);
    }

    @Test
    public void multipleElementsList() {
        stringPointer = new ListElementPointer<String>(stringElements);
        String thirdElement = "third";
        stringPointer.add(thirdElement);

        assertCurrentSuccess(SECOND_ELEMENT);

        assertNextSuccess(thirdElement);
        assertNextFailure();

        assertPreviousSuccess(SECOND_ELEMENT);
        assertPreviousSuccess(FIRST_ELEMENT);
        assertPreviousFailure();
    }

    @Test
    public void equals() {
        ListElementPointer firstPointer = new ListElementPointer<String>(stringElements);

        // same object -> returns true
        assertTrue(firstPointer.equals(firstPointer));

        // same values -> returns true
        ListElementPointer firstPointerCopy = new ListElementPointer<String>(stringElements);
        assertTrue(firstPointer.equals(firstPointerCopy));

        // different types -> returns false
        assertFalse(firstPointer.equals(1));

        // null -> returns false
        assertFalse(firstPointer.equals(null));

        // different elements -> returns false
        ListElementPointer differentElementPointer =
                new ListElementPointer<String>(Collections.singletonList(SECOND_ELEMENT));
        assertFalse(firstPointer.equals(differentElementPointer));

        // different index -> returns false
        firstPointerCopy.previous();
        assertFalse(firstPointer.equals(firstPointerCopy));
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns true and the return value
     * of {@code pointer#next()} equals to {@code element}.
     */
    private void assertNextSuccess(String element) {
        assertTrue(stringPointer.hasNext());
        assertEquals(element, stringPointer.next());
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns true and the return value
     * of {@code pointer#previous()} equals to {@code element}.
     */
    private void assertPreviousSuccess(String element) {
        assertTrue(stringPointer.hasPrevious());
        assertEquals(element, stringPointer.previous());
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns true and the return value
     * of {@code pointer#current()} equals to {@code element}.
     */
    private void assertCurrentSuccess(String element) {
        assertTrue(stringPointer.hasCurrent());
        assertEquals(element, stringPointer.current());
    }

    /**
     * Asserts that {@code pointer#hasNext()} returns false and the following
     * {@code pointer#next()} call throws {@code NoSuchElementException}.
     */
    private void assertNextFailure() {
        assertFalse(stringPointer.hasNext());
        try {
            stringPointer.next();
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code pointer#hasPrevious()} returns false and the following
     * {@code pointer#previous()} call throws {@code NoSuchElementException}.
     */
    private void assertPreviousFailure() {
        assertFalse(stringPointer.hasPrevious());
        try {
            stringPointer.previous();
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }

    /**
     * Asserts that {@code pointer#hasCurrent()} returns false and the following
     * {@code pointer#current()} call throws {@code NoSuchElementException}.
     */
    private void assertCurrentFailure() {
        assertFalse(stringPointer.hasCurrent());
        try {
            stringPointer.current();
            throw new AssertionError("The expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            // expected exception thrown
        }
    }
}
