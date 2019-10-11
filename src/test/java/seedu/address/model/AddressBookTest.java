package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.A_ANSWERABLE;
import static seedu.address.testutil.TypicalAnswerables.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.exceptions.DuplicateAnswerableException;
import seedu.address.testutil.AnswerableBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getAnswerableList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateAnswerable_throwsDuplicateAnswerableException() {
        // Two answerables with the same identity fields
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Answerable> newAnswerables = Arrays.asList(A_ANSWERABLE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newAnswerables);

        assertThrows(DuplicateAnswerableException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasAnswerable_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAnswerable(null));
    }

    @Test
    public void hasAnswerable_answerableNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAnswerable(A_ANSWERABLE));
    }

    @Test
    public void hasAnswerable_answerableInAddressBook_returnsTrue() {
        addressBook.addAnswerable(A_ANSWERABLE);
        assertTrue(addressBook.hasAnswerable(A_ANSWERABLE));
    }

    @Test
    public void hasAnswerable_answerableWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addAnswerable(A_ANSWERABLE);
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasAnswerable(editedAlice));
    }

    @Test
    public void getAnswerableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getAnswerableList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose answerables list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Answerable> answerables = FXCollections.observableArrayList();

        AddressBookStub(Collection<Answerable> answerables) {
            this.answerables.setAll(answerables);
        }

        @Override
        public ObservableList<Answerable> getAnswerableList() {
            return answerables;
        }
    }

}
