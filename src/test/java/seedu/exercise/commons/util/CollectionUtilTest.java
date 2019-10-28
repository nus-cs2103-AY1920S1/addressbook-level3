package seedu.exercise.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CollectionUtilTest {
    @Test
    public void requireAllNonNullVarargs() {
        // no arguments
        assertNullPointerExceptionNotThrown();

        // any non-empty argument list
        assertNullPointerExceptionNotThrown(new Object(), new Object());
        assertNullPointerExceptionNotThrown("test");
        assertNullPointerExceptionNotThrown("");

        // argument lists with just one null at the beginning
        assertNullPointerExceptionThrown((Object) null);
        assertNullPointerExceptionThrown(null, "", new Object());
        assertNullPointerExceptionThrown(null, new Object(), new Object());

        // argument lists with nulls in the middle
        assertNullPointerExceptionThrown(new Object(), null, null, "test");
        assertNullPointerExceptionThrown("", null, new Object());

        // argument lists with one null as the last argument
        assertNullPointerExceptionThrown("", new Object(), null);
        assertNullPointerExceptionThrown(new Object(), new Object(), null);

        // null reference
        assertNullPointerExceptionThrown((Object[]) null);

        // confirms nulls inside lists in the argument list are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(containingNull, new Object());
    }

    @Test
    public void requireAllNonNullCollection() {
        // lists containing nulls in the front
        assertNullPointerExceptionThrown(Arrays.asList((Object) null));
        assertNullPointerExceptionThrown(Arrays.asList(null, new Object(), ""));

        // lists containing nulls in the middle
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, new Object()));
        assertNullPointerExceptionThrown(Arrays.asList("spam", null, "eggs", null, new Object()));

        // lists containing nulls at the end
        assertNullPointerExceptionThrown(Arrays.asList("spam", new Object(), null));
        assertNullPointerExceptionThrown(Arrays.asList(new Object(), null));

        // null reference
        assertNullPointerExceptionThrown((Collection<Object>) null);

        // empty list
        assertNullPointerExceptionNotThrown(Collections.emptyList());

        // list with all non-null elements
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object(), "ham", Integer.valueOf(1)));
        assertNullPointerExceptionNotThrown(Arrays.asList(new Object()));

        // confirms nulls inside nested lists are not considered
        List<Object> containingNull = Arrays.asList((Object) null);
        assertNullPointerExceptionNotThrown(Arrays.asList(containingNull, new Object()));
    }

    @Test
    public void isAnyNonNull() {
        assertFalse(CollectionUtil.isAnyNonNull());
        assertFalse(CollectionUtil.isAnyNonNull((Object) null));
        assertFalse(CollectionUtil.isAnyNonNull((Object[]) null));
        assertTrue(CollectionUtil.isAnyNonNull(new Object()));
        assertTrue(CollectionUtil.isAnyNonNull(new Object(), null));
    }

    @Test
    public void append_listsNonEmpty_correctAppendedList() {
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        first.add("1");
        second.add("2");

        expected.add("1");
        expected.add("2");

        assertEquals(expected, CollectionUtil.append(first, second));
    }

    @Test
    public void append_listEmpty_correctAppendedList() {
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        second.add("2");
        expected.add("2");

        //Second list empty
        assertEquals(expected, CollectionUtil.append(first, second));

        //Reset for next test
        second.clear();
        expected.clear();

        first.add("1");
        expected.add("1");

        //First list empty
        assertEquals(expected, CollectionUtil.append(first, second));

        first.clear();
        second.clear();
        expected.clear();

        //Both lists empty
        assertEquals(expected, CollectionUtil.append(first, second));
    }

    @Test
    public void areListsEmpty_listsEmpty_returnsTrue() {
        assertTrue(CollectionUtil.areListsEmpty(
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Test
    public void areListsEmpty_someListEmpty_returnsFalse() {
        List<String> nonEmpty = new ArrayList<>();
        nonEmpty.add("a");
        assertFalse(CollectionUtil.areListsEmpty(
                new ArrayList<>(),
                nonEmpty
        ));
    }

    @Test
    public void append_nullArguments_throwsNullPointerExceptino() {
        //Both null
        assertThrows(NullPointerException.class, () -> CollectionUtil.append(null, null));

        //One null
        assertThrows(NullPointerException.class, () -> CollectionUtil.append(null, new ArrayList<>()));
    }

    @Test
    public void areListsEmpty_nullArguments_throwsNullPointerException() {
        //Both null
        assertThrows(NullPointerException.class, () -> CollectionUtil.areListsEmpty(null, null));

        //One null
        assertThrows(NullPointerException.class, () -> CollectionUtil.areListsEmpty(null, new ArrayList<>()));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Object...)} throw {@code NullPointerException}
     * if {@code objects} or any element of {@code objects} is null.
     */
    private void assertNullPointerExceptionThrown(Object... objects) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(objects));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Collection<?>)} throw {@code NullPointerException}
     * if {@code collection} or any element of {@code collection} is null.
     */
    private void assertNullPointerExceptionThrown(Collection<?> collection) {
        assertThrows(NullPointerException.class, () -> requireAllNonNull(collection));
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        requireAllNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> collection) {
        requireAllNonNull(collection);
    }
}
