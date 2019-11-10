package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NoteDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteDescription(null));
    }

    @Test
    void isValidDescription() {

        assertFalse(NoteDescription.isValidDescription(""));
        assertFalse(NoteDescription.isValidDescription(" ")); //single whitespace

        assertTrue(NoteDescription.isValidDescription("1")); //number
        assertTrue(NoteDescription.isValidDescription("diary")); //lowercase
        assertTrue(NoteDescription.isValidDescription("DIARY"));  //uppercase
        assertTrue(NoteDescription.isValidDescription("DIARY$@#^")); //special characters
        assertTrue(NoteDescription.isValidDescription("DIARY$#^ is special diary")); //long description
    }

}