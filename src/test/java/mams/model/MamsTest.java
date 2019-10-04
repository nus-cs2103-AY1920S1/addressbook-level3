package mams.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static mams.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mams.logic.commands.CommandTestUtil;
import mams.model.person.Person;
import mams.model.person.exceptions.DuplicatePersonException;
import mams.testutil.Assert;
import mams.testutil.PersonBuilder;
import mams.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MamsTest {

    private final Mams mams = new Mams();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mams.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mams.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMams_replacesData() {
        Mams newData = TypicalPersons.getTypicalMams();
        mams.resetData(newData);
        assertEquals(newData, mams);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        MamsStub newData = new MamsStub(newPersons);

        Assert.assertThrows(DuplicatePersonException.class, () -> mams.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> mams.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInMams_returnsFalse() {
        assertFalse(mams.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInMams_returnsTrue() {
        mams.addPerson(TypicalPersons.ALICE);
        assertTrue(mams.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInMams_returnsTrue() {
        mams.addPerson(TypicalPersons.ALICE);
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE)
                .withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(mams.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> mams.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyMams whose persons list can violate interface constraints.
     */
    private static class MamsStub implements ReadOnlyMams {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        MamsStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
