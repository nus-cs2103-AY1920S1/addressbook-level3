package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalDiaries.BENSON;

import org.junit.jupiter.api.Test;

public class JsonAdaptedDiaryTest {
    private static final String INVALID_NAME = "R@chel";

    @Test
    public void toModelType_validDiaryDetails_returnsDiary() throws Exception {
        JsonAdaptedDiary diary = new JsonAdaptedDiary(BENSON);
        assertEquals(BENSON, diary.toModelType());
    }
}
