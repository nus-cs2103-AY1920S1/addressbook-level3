package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.storage.JsonAdaptedBookmark.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;

public class JsonAdaptedBookmarkTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_URL = "exam?ple.com?";
    private static final String INVALID_REMARK = "t/ means tag";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_URL = BENSON.getUrl().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_FOLDER = BENSON.getFolder().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validBookmarkDetails_returnsBookmark() throws Exception {
        JsonAdaptedBookmark bookmark = new JsonAdaptedBookmark(BENSON);
        assertEquals(BENSON, bookmark.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark =
                new JsonAdaptedBookmark(INVALID_NAME, VALID_URL, VALID_REMARK, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark = new JsonAdaptedBookmark(null, VALID_URL, VALID_REMARK, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidUrl_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark =
                new JsonAdaptedBookmark(VALID_NAME, INVALID_URL, VALID_REMARK, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = Url.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_nullUrl_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark = new JsonAdaptedBookmark(VALID_NAME, null, VALID_REMARK, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark =
                new JsonAdaptedBookmark(VALID_NAME, VALID_URL, INVALID_REMARK, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedBookmark bookmark = new JsonAdaptedBookmark(VALID_NAME, VALID_URL, null, VALID_FOLDER, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, bookmark::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedBookmark bookmark =
                new JsonAdaptedBookmark(VALID_NAME, VALID_URL, VALID_REMARK, VALID_FOLDER, invalidTags);
        assertThrows(IllegalValueException.class, bookmark::toModelType);
    }

}
