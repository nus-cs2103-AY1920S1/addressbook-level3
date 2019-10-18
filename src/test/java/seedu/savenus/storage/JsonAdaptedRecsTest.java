package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

public class JsonAdaptedRecsTest {
    private static final String INVALID_CATEGORY = "#&*";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_LOCATION = "";

    private static final String VALID_LIKED_CATEGORY = "japanese";
    private static final String VALID_LIKED_TAGS = "ramen";
    private static final String VALID_LIKED_LOCATION = "the deck @ nus";

    private static final String VALID_DISLIKED_CATEGORY = "western";
    private static final String VALID_DISLIKED_TAGS = "healthy";
    private static final String VALID_DISLIKED_LOCATION = "the terrace @ nus";

    private static final Set<String> likedCategorySet = new HashSet<>();
    private static final Set<String> likedTagsSet = new HashSet<>();
    private static final Set<String> likedLocationSet = new HashSet<>();

    private static final Set<Category> expectedLikedCategorySet = new HashSet<>();
    private static final Set<Tag> expectedLikedTagSet = new HashSet<>();
    private static final Set<Location> expectedLikedLocationSet = new HashSet<>();

    private static final Set<String> dislikedCategorySet = new HashSet<>();
    private static final Set<String> dislikedTagsSet = new HashSet<>();
    private static final Set<String> dislikedLocationSet = new HashSet<>();

    private static final Set<Category> expectedDislikedCategorySet = new HashSet<>();
    private static final Set<Tag> expectedDislikedTagSet = new HashSet<>();
    private static final Set<Location> expectedDislikedLocationSet = new HashSet<>();

    private static final Set<String> invalidCategorySet = new HashSet<>();
    private static final Set<String> invalidTagsSet = new HashSet<>();
    private static final Set<String> invalidLocationSet = new HashSet<>();

    private static final Set<String> overlapCategorySet = new HashSet<>();
    private static final Set<String> overlapTagsSet = new HashSet<>();
    private static final Set<String> overlapLocationSet = new HashSet<>();

    @BeforeEach
    public void setUp() {
        likedCategorySet.add(VALID_LIKED_CATEGORY);
        likedTagsSet.add(VALID_LIKED_TAGS);
        likedLocationSet.add(VALID_LIKED_LOCATION);

        overlapCategorySet.add(VALID_LIKED_CATEGORY);
        overlapTagsSet.add(VALID_LIKED_TAGS);
        overlapLocationSet.add(VALID_LIKED_LOCATION);

        dislikedCategorySet.add(VALID_DISLIKED_CATEGORY);
        dislikedTagsSet.add(VALID_DISLIKED_TAGS);
        dislikedLocationSet.add(VALID_DISLIKED_LOCATION);

        expectedLikedCategorySet.add(new Category(VALID_LIKED_CATEGORY));
        expectedLikedTagSet.add(new Tag(VALID_LIKED_TAGS));
        expectedLikedLocationSet.add(new Location(VALID_LIKED_LOCATION));

        expectedDislikedCategorySet.add(new Category(VALID_DISLIKED_CATEGORY));
        expectedDislikedTagSet.add(new Tag(VALID_DISLIKED_TAGS));
        expectedDislikedLocationSet.add(new Location(VALID_DISLIKED_LOCATION));

        invalidCategorySet.add(INVALID_CATEGORY);
        invalidTagsSet.add(INVALID_TAG);
        invalidLocationSet.add(INVALID_LOCATION);
    }

    @Test
    public void toModelType_validRecDetails_returnsRec() throws Exception {
        JsonAdaptedRecs recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);

        UserRecommendations expectedRecs
                = new UserRecommendations(expectedLikedCategorySet, expectedLikedTagSet, expectedLikedLocationSet,
                expectedDislikedCategorySet, expectedDislikedTagSet, expectedDislikedLocationSet);
        assertEquals(recs.toModelType(), recs.toModelType());
        assertEquals(recs.toModelType(), expectedRecs);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(invalidCategorySet, likedTagsSet, likedLocationSet,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                invalidCategorySet, dislikedTagsSet, dislikedLocationSet);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(null, likedTagsSet, likedLocationSet,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                null, dislikedTagsSet, dislikedLocationSet);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapCategory_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                        overlapCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, likedTagsSet, invalidLocationSet,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                dislikedCategorySet, dislikedTagsSet, invalidLocationSet);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, likedTagsSet, null,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                dislikedCategorySet, dislikedTagsSet, null);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapLocation_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                        dislikedCategorySet, dislikedTagsSet, overlapLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, invalidTagsSet, likedLocationSet,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                dislikedCategorySet, invalidTagsSet, dislikedLocationSet);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_nullTag_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, null, likedLocationSet,
                        dislikedCategorySet, dislikedTagsSet, dislikedLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);

        recs = new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                dislikedCategorySet, null, dislikedLocationSet);
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }

    @Test
    public void toModelType_overlapTag_throwsIllegalValueException() {
        JsonAdaptedRecs recs =
                new JsonAdaptedRecs(likedCategorySet, likedTagsSet, likedLocationSet,
                        dislikedCategorySet, overlapTagsSet, dislikedLocationSet);
        String expectedMessage =
                String.format(JsonAdaptedRecs.DUPLICATE_ENTRY_OPPOSING_FOUND, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, recs::toModelType);
    }
}
