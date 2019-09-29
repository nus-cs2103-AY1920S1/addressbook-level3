package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class WordBankTest {

    private final WordBank wordBank = new WordBank();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), wordBank.getCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        WordBank newData = getTypicalAddressBook();
        wordBank.resetData(newData);
        assertEquals(newData, wordBank);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Card editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Card> newPersons = Arrays.asList(ALICE, editedAlice);
        WordBankStub newData = new WordBankStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> wordBank.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> wordBank.hasCard(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(wordBank.hasCard(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        wordBank.addCard(ALICE);
        assertTrue(wordBank.hasCard(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        wordBank.addCard(ALICE);
        Card editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(wordBank.hasCard(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> wordBank.getCardList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class WordBankStub implements ReadOnlyWordBank {
        private final ObservableList<Card> persons = FXCollections.observableArrayList();

        WordBankStub(Collection<Card> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Card> getCardList() {
            return persons;
        }
    }

}
