package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.DuplicateElementException;

class UniqueOrderedSetTest {

    @Test
    void add_duplicateElement_failure() {
        UniqueOrderedSet<Integer> set = new UniqueOrderedSet<>();
        set.add(1);
        assertThrows(DuplicateElementException.class, () -> set.add(1));
    }

    @Test
    void modify_unmodifiableList_failure() {
        UniqueOrderedSet<Integer> set = new UniqueOrderedSet<>();
        List<Integer> unmodifiableList = set.toUnmodifiableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(1));
    }

    @Test
    void iterate_sameAsInsertionOrder_success() {
        UniqueOrderedSet<Integer> set = new UniqueOrderedSet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        Iterator<Integer> iterator = set.iterator();
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
    }

    // Insertion order does not matter when comparing equality of sets.
    @Test
    void equals_differentInsertionOrder_success() {
        UniqueOrderedSet<Integer> set1 = new UniqueOrderedSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        UniqueOrderedSet<Integer> set2 = new UniqueOrderedSet<>();
        set2.add(3);
        set2.add(2);
        set2.add(1);

        assertEquals(set1, set2);
    }

    @Test
    void equals_differentObject_failure() {
        UniqueOrderedSet<Integer> set1 = new UniqueOrderedSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        assertNotEquals(set1, Set.of(1, 2, 3));
    }
}
