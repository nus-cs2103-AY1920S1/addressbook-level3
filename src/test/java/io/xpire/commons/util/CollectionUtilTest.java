package io.xpire.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.testutil.Assert;

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
    public void stringifyCollection_emptyCollection_noStrings() {
        Object[] objects = new Object[] {};
        String[] objectStrings = new String[] {};

        Collection<Object> objectCollection = Arrays.asList(objects);
        Collection<String> expected = Arrays.asList(objectStrings);
        assertEquals(expected, CollectionUtil.stringifyCollection(objectCollection));
    }

    @Test
    public void stringifyCollection_singleElement_singleString() {
        Object obj1 = new Object();
        Object[] objects = new Object[] {obj1};
        String[] objectStrings = new String[] {obj1.toString()};

        Collection<Object> objectCollection = Arrays.asList(objects);
        Collection<String> expected = Arrays.asList(objectStrings);
        assertEquals(expected, CollectionUtil.stringifyCollection(objectCollection));
    }

    @Test
    public void stringifyCollection_multipleElements_multipleStrings() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object[] objects = new Object[] {obj1, obj2, obj3};
        String[] objectStrings = new String[] {obj1.toString(), obj2.toString(), obj3.toString()};

        Collection<Object> objectCollection = Arrays.asList(objects);
        Collection<String> expected = Arrays.asList(objectStrings);
        assertEquals(expected, CollectionUtil.stringifyCollection(objectCollection));
    }

    @Test
    public void stringifyCollection_multipleMappersOrderSensitive_multipleMappedStrings() {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Object[] objects = new Object[] {obj1, obj2, obj3};
        String[] objectStrings = new String[] {
                obj1.toString().toUpperCase().concat("test"),
                obj2.toString().toUpperCase().concat("test"),
                obj3.toString().toUpperCase().concat("test")
        };

        Collection<Object> objectCollection = Arrays.asList(objects);
        Collection<String> expected = Arrays.asList(objectStrings);
        assertEquals(expected, CollectionUtil.stringifyCollection(objectCollection,
            item -> item.toUpperCase(),
            item -> item.concat("test")));
        assertNotEquals(expected, CollectionUtil.stringifyCollection(objectCollection,
            item -> item.concat("test"),
            item -> item.toUpperCase()));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Object...)} throw {@code NullPointerException}
     * if {@code objects} or any element of {@code objects} is null.
     */
    private void assertNullPointerExceptionThrown(Object... objects) {
        Assert.assertThrows(NullPointerException.class, () -> CollectionUtil.requireAllNonNull(objects));
    }

    /**
     * Asserts that {@code CollectionUtil#requireAllNonNull(Collection<?>)} throw {@code NullPointerException}
     * if {@code collection} or any element of {@code collection} is null.
     */
    private void assertNullPointerExceptionThrown(Collection<?> collection) {
        Assert.assertThrows(NullPointerException.class, () -> CollectionUtil.requireAllNonNull(collection));
    }

    private void assertNullPointerExceptionNotThrown(Object... objects) {
        CollectionUtil.requireAllNonNull(objects);
    }

    private void assertNullPointerExceptionNotThrown(Collection<?> collection) {
        CollectionUtil.requireAllNonNull(collection);
    }
}
