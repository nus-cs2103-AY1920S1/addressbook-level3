package dukecooks.model.profile;

import static dukecooks.testutil.Assert.assertThrows;
import static dukecooks.testutil.profile.TypicalProfiles.ALICE;
import static dukecooks.testutil.profile.TypicalProfiles.getTypicalProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.exceptions.DuplicateProfileException;
import dukecooks.testutil.profile.PersonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserProfileTest {

    private final UserProfile userProfile = new UserProfile();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), userProfile.getUserProfileList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> userProfile.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        UserProfile newData = getTypicalProfiles();
        userProfile.resetData(newData);
        assertEquals(newData, userProfile);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withMedicalHistories(CommandTestUtil.VALID_HISTORY_DENGUE)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        UserProfileStub newData = new UserProfileStub(newPersons);

        assertThrows(DuplicateProfileException.class, () -> userProfile.resetData(newData));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> userProfile.getUserProfileList().remove(0));
    }

    /**
     * A stub ReadOnlyUserProfile whose persons list can violate interface constraints.
     */
    private static class UserProfileStub implements ReadOnlyUserProfile {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        UserProfileStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getUserProfileList() {
            return persons;
        }
    }

}
