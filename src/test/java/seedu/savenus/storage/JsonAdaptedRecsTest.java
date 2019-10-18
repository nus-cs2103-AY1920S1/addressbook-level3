package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_CATEGORY_SET_STRING;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_LOCATION_SET_STRING;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.DISLIKED_TAG_SET_STRING;
import static seedu.savenus.testutil.TypicalRecs.INVALID_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.INVALID_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.INVALID_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_CATEGORY_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_CATEGORY_SET_STRING;
import static seedu.savenus.testutil.TypicalRecs.LIKED_LOCATION_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_LOCATION_SET_STRING;
import static seedu.savenus.testutil.TypicalRecs.LIKED_TAG_SET;
import static seedu.savenus.testutil.TypicalRecs.LIKED_TAG_SET_STRING;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

public class JsonAdaptedRecsTest {


    @Test
    public void toModelType_validRecDetails_returnsRec() throws Exception {
        JsonAdaptedRecs recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING,
                LIKED_LOCATION_SET_STRING, DISLIKED_CATEGORY_SET_STRING,
                DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);

        UserRecommendations expectedRecs =
                new UserRecommendations(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET,
                        DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET);
        assertEquals(recs.toModelType(), recs.toModelType());
        assertEquals(recs.toModelType(), expectedRecs);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(INVALID_CATEGORY_SET, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                INVALID_CATEGORY_SET, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(null, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                null, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                        LIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, INVALID_LOCATION_SET,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, INVALID_LOCATION_SET);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, null,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, null);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, INVALID_TAG_SET, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                DISLIKED_CATEGORY_SET_STRING, INVALID_TAG_SET, DISLIKED_LOCATION_SET_STRING);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullTag_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, null, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, DISLIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                DISLIKED_CATEGORY_SET_STRING, null, DISLIKED_LOCATION_SET_STRING);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapTag_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(LIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, LIKED_LOCATION_SET_STRING,
                        DISLIKED_CATEGORY_SET_STRING, LIKED_TAG_SET_STRING, DISLIKED_LOCATION_SET_STRING);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }
}
