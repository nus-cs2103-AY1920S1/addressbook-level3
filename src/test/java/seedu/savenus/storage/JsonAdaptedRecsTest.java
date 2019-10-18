package seedu.savenus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private static final Set<String> likedCategory = new HashSet<>();
    private static final Set<String> likedTags = new HashSet<>();
    private static final Set<String> likedLocation = new HashSet<>();

    private static final Set<String> dislikedCategory = new HashSet<>();
    private static final Set<String> dislikedTags = new HashSet<>();
    private static final Set<String> dislikedLocation = new HashSet<>();

    private static final Set<String> invalidLikedCategory = new HashSet<>();
    private static final Set<String> invalidLikedTags = new HashSet<>();
    private static final Set<String> invalidLikedLocation = new HashSet<>();

    @BeforeEach
    public void setUp() {
        likedCategory.add(VALID_LIKED_CATEGORY);
        likedTags.add(VALID_LIKED_TAGS);
        likedLocation.add(VALID_LIKED_LOCATION);

        dislikedCategory.add(VALID_DISLIKED_CATEGORY);
        dislikedTags.add(VALID_DISLIKED_TAGS);
        dislikedLocation.add(VALID_DISLIKED_LOCATION);
    }

    @Test
    public void toModelType_validRecDetails_returnsRec() throws Exception {
        JsonAdaptedRecs recs = new JsonAdaptedRecs(likedCategory, likedTags, likedLocation,
                dislikedCategory, dislikedTags, dislikedLocation);

        assertEquals(recs.toModelType(), recs.toModelType());
    }

}
