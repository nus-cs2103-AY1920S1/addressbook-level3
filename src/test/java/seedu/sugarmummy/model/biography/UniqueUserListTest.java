package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.model.biography.UserTest.VALID_USER;
import static seedu.sugarmummy.model.biography.UserTest.VALID_USER_WITH_DIFFERENCES;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sugarmummy.model.biography.exceptions.UserNotFoundException;

class UniqueUserListTest {

    @Test
    public void contains_empty() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertFalse(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void contains_false_differentUsersTest() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertFalse(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void contains_trueTest() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        assertTrue(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void isEmpty_emptyList() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertTrue(uniqueUserList.isEmpty());
    }

    @Test
    public void isEmpty_listWithOneItem() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        assertFalse(uniqueUserList.isEmpty());
    }

    @Test
    public void isEmpty_listWithTwoItems() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        uniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertFalse(uniqueUserList.isEmpty());
    }

    @Test
    public void add() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        assertTrue(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void setUser() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        uniqueUserList.setUser(VALID_USER, VALID_USER_WITH_DIFFERENCES);
        assertTrue(uniqueUserList.contains(VALID_USER_WITH_DIFFERENCES));
        assertFalse(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void setNonExistingUser() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertThrows(UserNotFoundException.class,
                null, () -> uniqueUserList.setUser(VALID_USER, VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void remove() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        uniqueUserList.remove(VALID_USER);
        assertTrue(uniqueUserList.isEmpty());
        assertFalse(uniqueUserList.contains(VALID_USER));
    }

    @Test
    public void removeNonExistingUser() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertThrows(UserNotFoundException.class,
                null, () -> uniqueUserList.remove(VALID_USER));
    }

    @Test
    public void setUsers() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.setUsers(List.of(VALID_USER, VALID_USER_WITH_DIFFERENCES));
        assertFalse(uniqueUserList.isEmpty());
        assertTrue(uniqueUserList.contains(VALID_USER));
        assertTrue(uniqueUserList.contains(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void asUnmodifiableObservableList() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        uniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        ObservableList<User> userlist = uniqueUserList.asUnmodifiableObservableList();

        ObservableList<User> expectedList = FXCollections.observableArrayList();
        expectedList.addAll(VALID_USER, VALID_USER_WITH_DIFFERENCES);

        assertFalse(userlist.isEmpty());
        assertTrue(userlist.contains(VALID_USER));
        assertTrue(userlist.contains(VALID_USER_WITH_DIFFERENCES));
        assertEquals(expectedList, userlist);
    }

    @Test
    public void iterator() {
        Iterator<User> expectedIterator = List.of(VALID_USER, VALID_USER_WITH_DIFFERENCES).iterator();

        UniqueUserList uniqueUserList = new UniqueUserList();
        uniqueUserList.add(VALID_USER);
        uniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        Iterator<User> iterator = uniqueUserList.iterator();

        iterator.forEachRemaining(USER -> assertEquals(USER, expectedIterator.next()));
    }

    @Test
    public void testEquals_sameEmptyList() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        assertEquals(firstUniqueUserList, secondUniqueUserList);
    }

    @Test
    public void testEquals_emptyListAndFilledList() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        assertNotEquals(firstUniqueUserList, secondUniqueUserList);
    }

    @Test
    public void testEquals_listAndObject() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertNotEquals(uniqueUserList, new Object());
    }

    @Test
    public void testEquals_listsWithDifferentUsers() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUniqueUserList, secondUniqueUserList);
    }

    @Test
    public void testEquals_listsWithSameUsers() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        firstUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER);
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertEquals(firstUniqueUserList, secondUniqueUserList);
    }

    @Test
    public void testEquals_listsWithSameUsersDifferentOrder() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER);
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUniqueUserList, secondUniqueUserList);
    }

    @Test
    public void testHashCode_sameEmptyList() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        assertEquals(firstUniqueUserList.hashCode(), secondUniqueUserList.hashCode());
    }

    @Test
    public void testHashCode_emptyListAndFilledList() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        assertNotEquals(firstUniqueUserList.hashCode(), secondUniqueUserList.hashCode());
    }

    @Test
    public void testHashCode_listAndObject() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        assertNotEquals(uniqueUserList.hashCode(), new Object().hashCode());
    }

    @Test
    public void testHashCode_listsWithDifferentUsers() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUniqueUserList.hashCode(), secondUniqueUserList.hashCode());
    }

    @Test
    public void testHashCode_listsWithSameUsers() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER);
        firstUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER);
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertEquals(firstUniqueUserList.hashCode(), secondUniqueUserList.hashCode());
    }

    @Test
    public void testHashCode_listsWithSameUsersDifferentOrder() {
        UniqueUserList firstUniqueUserList = new UniqueUserList();
        firstUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        firstUniqueUserList.add(VALID_USER);
        UniqueUserList secondUniqueUserList = new UniqueUserList();
        secondUniqueUserList.add(VALID_USER);
        secondUniqueUserList.add(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUniqueUserList.hashCode(), secondUniqueUserList.hashCode());
    }

}
