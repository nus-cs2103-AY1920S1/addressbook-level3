package seedu.sugarmummy.model.biography;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.sugarmummy.model.ModelStub;

/**
 * Class containing stubs for Model specifically for Bio.
 */
public class BioModelStub {

    public static final User VALID_USER = new User(new Name("testName"),
            new DisplayPicPath(""),
            new ProfileDesc(""),
            new Nric(""),
            new Gender(""),
            new DateOfBirth(""),
            List.of(new Phone("81234567")),
            List.of(new Phone("91234567")),
            List.of(new MedicalCondition("Type II Diabetes"),
                    new MedicalCondition("High Blood Pressure")),
            new Address(""),
            Collections.emptyList(),
            new OtherBioInfo(""));

    public static final User OTHER_VALID_USER = new User(new Name("secondTestName"),
            new DisplayPicPath(""),
            new ProfileDesc(""),
            new Nric(""),
            new Gender(""),
            new DateOfBirth(""),
            List.of(new Phone("81234567")),
            List.of(new Phone("91234567")),
            List.of(new MedicalCondition("Type II Diabetes"),
                    new MedicalCondition("High Blood Pressure")),
            new Address(""),
            Collections.emptyList(),
            new OtherBioInfo(""));

    /**
     * Model stub for Bio containing a UserList with a User.
     */
    public static class ModelStubWithUserList extends ModelStub {

        private UserList userList = new UserList();
        private FilteredList<User> filteredUserList = new FilteredList<>(this.userList.getUserList());

        public ModelStubWithUserList() {
            userList.addUser(VALID_USER);
        }

        @Override
        public boolean bioExists() {
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ModelStubWithUserList)) {
                return false;
            } else {
                return filteredUserList.equals(((ModelStubWithUserList) obj)
                        .filteredUserList);
            }
        }
    }

    /**
     * Model stub for Bio containing a UserList with a User for editing purposes.
     */
    public static class ModelStubWithUserListForEditing extends ModelStubWithUserList {
        @Override
        public void addUser(User user) {
            super.userList.addUser(user);
        }

        @Override
        public void setUser(User target, User editedUser) {
            super.userList.setUser(target, editedUser);
        }

        @Override
        public ObservableList<User> getFilteredUserList() {
            return super.filteredUserList;
        }

        @Override
        public void updateFilteredUserList(Predicate<User> predicate) {
            requireNonNull(predicate);
            super.filteredUserList.setPredicate(predicate);
        }

        @Override
        public void setUserList(ReadOnlyUserList userList) {
            super.userList.resetData(userList);
        }
    }

    /**
     * Model stub for Bio containing an empty UserList for editing purposes.
     */
    public static class ModelStubWithNoUserListForEditing extends ModelStubWithNoUserList {
        @Override
        public ObservableList<User> getFilteredUserList() {
            return super.filteredUserList;
        }
    }

    /**
     * Model stub for Bio containing an empty UserList.
     */
    public static class ModelStubWithNoUserList extends ModelStub {

        private UserList userList = new UserList();
        private FilteredList<User> filteredUserList = new FilteredList<>(this.userList.getUserList());

        @Override
        public boolean bioExists() {
            return false;
        }

        @Override
        public void addUser(User user) {
            this.userList.addUser(user);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ModelStubWithNoUserList)) {
                return false;
            } else {
                return filteredUserList.equals(((ModelStubWithNoUserList) obj)
                        .filteredUserList);
            }
        }
    }
}
