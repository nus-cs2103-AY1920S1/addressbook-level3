package dukecooks.storage.diary;

import static dukecooks.testutil.diary.TypicalDiaries.BRUNCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.testutil.Assert;

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
        Assert.assertThrows(IllegalValueException.class, expectedMessage, diary::toModelType);
    }

    @Test
    public void toModelType_nullDiaryName_throwsIllegalValueException() {
        JsonAdaptedDiary diary = new JsonAdaptedDiary(null, VALID_PAGES);
        String expectedMessage = String.format(JsonAdaptedDiary.MISSING_FIELD_MESSAGE_FORMAT,
                DiaryName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, diary::toModelType);
    }

}
