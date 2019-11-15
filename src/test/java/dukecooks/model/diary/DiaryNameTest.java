package dukecooks.model.diary;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.DiaryName;

public class DiaryNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DiaryName(null));
    }

    @Test
    public void constructor_invalidDiaryName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new DiaryName(invalidName));
    }

    @Test
    public void isValidDiaryName() {
        // null name
        assertThrows(NullPointerException.class, () -> DiaryName.isValidName(null));

        // invalid name
        assertFalse(DiaryName.isValidName("")); // empty string
        assertFalse(DiaryName.isValidName(" ")); // spaces only
        assertFalse(DiaryName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(DiaryName.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(DiaryName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names > 25 characters

        // valid name
        assertTrue(DiaryName.isValidName("peter jack")); // alphabets only
        assertTrue(DiaryName.isValidName("12345")); // numbers only
        assertTrue(DiaryName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(DiaryName.isValidName("Capital Tan")); // with capital letters
    }
}
