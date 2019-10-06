package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.ALICE;
import static seedu.weme.testutil.TypicalMemes.BOB;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.MemeBuilder;

public class MemeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Meme meme = new MemeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> meme.getTags().remove(0));
    }

    @Test
    public void isSameMeme() {
        // same object -> returns true
        assertTrue(ALICE.isSameMeme(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMeme(null));

        // different name -> returns false
        Meme editedAlice = new MemeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameMeme(editedAlice));

        // same name, different attributes -> returns true
        editedAlice = new MemeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMeme(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meme aliceCopy = new MemeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different meme -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Meme editedAlice = new MemeBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different weme -> returns false
        editedAlice = new MemeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MemeBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
