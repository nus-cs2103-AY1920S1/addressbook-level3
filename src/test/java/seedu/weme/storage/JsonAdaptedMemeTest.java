package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.storage.JsonAdaptedMeme.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.JOKER;
import static seedu.weme.testutil.TypicalMemes.PIKACHU;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.meme.Description;
import seedu.weme.model.path.ImagePath;

public class JsonAdaptedMemeTest extends ApplicationTest {
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_URL = "hello world";
    private static final Path MEME_IMAGE_FOLDER = Paths.get("src", "test", "data", "memes");

    private static String validDescription;
    private static String validPath;
    private static List<JsonAdaptedTag> validTags;

    @BeforeEach
    public void setup() {
        validDescription = JOKER.getDescription().toString();
        validPath = MEME_IMAGE_FOLDER.relativize(JOKER.getImagePath().getFilePath()).toString(); // json different
        validTags = JOKER.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList());
    }

    @Test
    public void toModelType_validMemeDetails_returnsMeme() throws Exception {
        // unarchived
        JsonAdaptedMeme meme = new JsonAdaptedMeme(JOKER, MEME_IMAGE_FOLDER);
        assertEquals(JOKER, meme.toModelType(MEME_IMAGE_FOLDER));

        // archived
        meme = new JsonAdaptedMeme(PIKACHU, MEME_IMAGE_FOLDER);
        assertEquals(PIKACHU, meme.toModelType(MEME_IMAGE_FOLDER));
    }

    @Test
    public void toModelType_nullPath_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(null, validDescription, validTags, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ImagePath.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> meme.toModelType(MEME_IMAGE_FOLDER));
    }

    @Test
    public void toModelType_invalidPath_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(INVALID_URL, validDescription, validTags, false);
        assertThrows(IllegalValueException.class, () -> meme.toModelType(MEME_IMAGE_FOLDER));
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(validPath, null, validTags, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> meme.toModelType(MEME_IMAGE_FOLDER));
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(validTags);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(validPath, validDescription, invalidTags, false);
        assertThrows(IllegalValueException.class, () -> meme.toModelType(MEME_IMAGE_FOLDER));
    }

}
