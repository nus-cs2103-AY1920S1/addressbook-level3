package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.model.biography.UserTest.VALID_USER;
import static seedu.sugarmummy.model.biography.UserTest.VALID_USER_WITH_DIFFERENCES;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sugarmummy.model.biography.exceptions.UserNotFoundException;

class UserListTest {

    @Test
    public void isEmpty_emptyList() {
        UserList userList = new UserList();
        assertTrue(userList.isEmpty());
    }

    @Test
    public void isEmpty_removedUser() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.removeUser(VALID_USER);
        assertTrue(userList.isEmpty());
    }

    @Test
    public void setUsers() {
        UserList userList = new UserList();
        userList.setUsers(List.of(VALID_USER, VALID_USER_WITH_DIFFERENCES));
        assertFalse(userList.isEmpty());
        assertTrue(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void resetData_firstTest() {
        UserList userList = new UserList();
        UserList userListWithUsers = new UserList();
        userListWithUsers.addUser(VALID_USER);
        userListWithUsers.addUser(VALID_USER_WITH_DIFFERENCES);
        userList.resetData(userListWithUsers);

        assertFalse(userList.isEmpty());
        assertTrue(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
        assertEquals(userList, userListWithUsers);
    }

    @Test
    public void resetData_secondTest() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);
        UserList userListWithoutUsers = new UserList();
        userList.resetData(userListWithoutUsers);

        assertTrue(userList.isEmpty());
        assertFalse(userList.hasUser(VALID_USER));
        assertFalse(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
        assertEquals(userList, userListWithoutUsers);
    }

    @Test
    public void hasUser_listWithUsers() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);

        assertTrue(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void hasUser_listWithoutUsers() {
        UserList userList = new UserList();
        assertFalse(userList.hasUser(VALID_USER));
        assertFalse(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void addUser() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);

        assertTrue(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void setUser() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.setUser(VALID_USER, VALID_USER_WITH_DIFFERENCES);

        assertFalse(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void setNonExistingUser() {
        UserList userList = new UserList();
        assertThrows(UserNotFoundException.class,
                null, () -> userList.setUser(VALID_USER, VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void removeUser() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);

        userList.removeUser(VALID_USER);
        assertFalse(userList.hasUser(VALID_USER));
        assertTrue(userList.hasUser(VALID_USER_WITH_DIFFERENCES));
    }

    @Test
    public void removeNonExistingUser() {
        UserList userList = new UserList();
        assertThrows(UserNotFoundException.class,
                null, () -> userList.removeUser(VALID_USER));
    }

    @Test
    public void testToString() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);

        assertEquals("2 users", userList.toString());
    }

    @Test
    public void getUserList_listWithUsers() {
        UserList userList = new UserList();
        userList.addUser(VALID_USER);
        userList.addUser(VALID_USER_WITH_DIFFERENCES);

        ObservableList<User> expectedList = FXCollections.observableArrayList();
        expectedList.addAll(VALID_USER, VALID_USER_WITH_DIFFERENCES);

        assertEquals(expectedList, userList.getUserList());
    }

    @Test
    public void getUserList_listWithoutUsers() {
        UserList userList = new UserList();
        ObservableList<User> expectedList = FXCollections.observableArrayList();

        assertTrue(userList.getUserList().isEmpty());
        assertEquals(expectedList, userList.getUserList());
    }

    @Test
    public void testEquals_sameEmptyList() {
        UserList firstUserList = new UserList();
        UserList secondUserList = new UserList();
        assertEquals(firstUserList, secondUserList);
    }

    @Test
    public void testEquals_emptyListAndFilledList() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        assertNotEquals(firstUserList, secondUserList);
    }

    @Test
    public void testEquals_listAndObject() {
        UserList uniqueUserList = new UserList();
        assertNotEquals(uniqueUserList, new Object());
    }

    @Test
    public void testEquals_listsWithDifferentUsers() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUserList, secondUserList);
    }

    @Test
    public void testEquals_listsWithSameUsers() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        firstUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER);
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertEquals(firstUserList, secondUserList);
    }

    @Test
    public void testEquals_listsWithSameUsersDifferentOrder() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER);
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUserList, secondUserList);
    }

    @Test
    public void testHashCode_sameEmptyList() {
        UserList firstUserList = new UserList();
        UserList secondUserList = new UserList();
        assertEquals(firstUserList.hashCode(), secondUserList.hashCode());
    }

    @Test
    public void testHashCode_emptyListAndFilledList() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        assertNotEquals(firstUserList.hashCode(), secondUserList.hashCode());
    }

    @Test
    public void testHashCode_listAndObject() {
        UserList uniqueUserList = new UserList();
        assertNotEquals(uniqueUserList.hashCode(), new Object().hashCode());
    }

    @Test
    public void testHashCode_listsWithDifferentUsers() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUserList.hashCode(), secondUserList.hashCode());
    }

    @Test
    public void testHashCode_listsWithSameUsers() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER);
        firstUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER);
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertEquals(firstUserList.hashCode(), secondUserList.hashCode());
    }

    @Test
    public void testHashCode_listsWithSameUsersDifferentOrder() {
        UserList firstUserList = new UserList();
        firstUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        firstUserList.addUser(VALID_USER);
        UserList secondUserList = new UserList();
        secondUserList.addUser(VALID_USER);
        secondUserList.addUser(VALID_USER_WITH_DIFFERENCES);
        assertNotEquals(firstUserList.hashCode(), secondUserList.hashCode());
    }
}
