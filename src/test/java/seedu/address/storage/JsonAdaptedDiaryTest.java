package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.Name;

public class JsonAdaptedDiaryTest {
    private static final String INVALID_NAME = "R@chel";

    @Test
    public void toModelType_validDiaryDetails_returnsDiary() throws Exception {
        JsonAdaptedDiary diary = new JsonAdaptedDiary(BENSON);
        assertEquals(BENSON, diary.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDiary diary =
                new JsonAdaptedDiary(INVALID_NAME);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, diary::toModelType);
    }
}
