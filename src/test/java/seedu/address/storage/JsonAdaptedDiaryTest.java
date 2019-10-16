package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDiary.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDiaries.BRUNCH;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryName;

public class JsonAdaptedDiaryTest {
    private static final String INVALID_DIARY_NAME = "R@chel";

    private static final String VALID_NAME = BRUNCH.getDiaryName().toString();
    private static final List<JsonAdaptedPage> VALID_PAGES = BRUNCH.getPages().stream()
            .map(JsonAdaptedPage::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validDiaryDetails_returnsDiary() throws Exception {
        JsonAdaptedDiary diary = new JsonAdaptedDiary(BRUNCH);
        assertEquals(BRUNCH, diary.toModelType());
    }

    @Test
    public void toModelType_invalidDiaryName_throwsIllegalValueException() {
        JsonAdaptedDiary diary =
                new JsonAdaptedDiary(INVALID_DIARY_NAME, VALID_PAGES);
        String expectedMessage = DiaryName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, diary::toModelType);
    }

    @Test
    public void toModelType_nullDiaryName_throwsIllegalValueException() {
        JsonAdaptedDiary diary = new JsonAdaptedDiary(null, VALID_PAGES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DiaryName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, diary::toModelType);
    }

}
