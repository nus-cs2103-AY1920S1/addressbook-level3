package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.storage.JsonAdaptedMeme.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;

public class JsonAdaptedMemeTest {
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_URL = "hello world";

    private static final String VALID_DESCRIPTION = JOKER.getDescription().toString();
    private static final String VALID_URL = JOKER.getFilePath().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = JOKER.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMemeDetails_returnsMeme() throws Exception {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(JOKER);
        assertEquals(JOKER, meme.toModelType());
    }

    @Test
    public void toModelType_nullPath_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(null, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ImagePath.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_invalidPath_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(INVALID_URL, VALID_DESCRIPTION, VALID_TAGS);
        assertThrows(IllegalValueException.class, meme::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(VALID_URL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(VALID_URL, VALID_DESCRIPTION, invalidTags);
        assertThrows(IllegalValueException.class, meme::toModelType);
    }

}
