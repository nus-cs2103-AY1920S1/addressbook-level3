package seedu.savenus.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

/**
 * A utility class containing a list of sets to be used in recommendation tests.
 */
public class TypicalRecs {
    public static final String INVALID_CATEGORY = "#&*";
    public static final String INVALID_TAG = "#friend";
    public static final String INVALID_LOCATION = "";

    public static final String VALID_LIKED_CATEGORY = "japanese";
    public static final String VALID_LIKED_TAGS = "ramen";
    public static final String VALID_LIKED_LOCATION = "the deck @ nus";

    public static final String VALID_DISLIKED_CATEGORY = "western";
    public static final String VALID_DISLIKED_TAGS = "healthy";
    public static final String VALID_DISLIKED_LOCATION = "the terrace @ nus";

    public static final Set<String> LIKED_CATEGORY_SET_STRING = new HashSet<>() {
        {
            add(VALID_LIKED_CATEGORY);
        }
    };
    public static final Set<String> LIKED_TAG_SET_STRING = new HashSet<>() {
        {
            add(VALID_LIKED_TAGS);
        }
    };

    public static final Set<String> LIKED_LOCATION_SET_STRING = new HashSet<>() {
        {
            add(VALID_LIKED_LOCATION);
        }
    };

    public static final Set<Category> LIKED_CATEGORY_SET = new HashSet<>() {
        {
            add(new Category(VALID_LIKED_CATEGORY));
        }
    };
    public static final Set<Tag> LIKED_TAG_SET = new HashSet<>() {
        {
            add(new Tag(VALID_LIKED_TAGS));
        }
    };
    public static final Set<Location> LIKED_LOCATION_SET = new HashSet<>() {
        {
            add(new Location(VALID_LIKED_LOCATION));
        }
    };

    public static final Set<String> DISLIKED_CATEGORY_SET_STRING = new HashSet<>() {
        {
            add(VALID_DISLIKED_CATEGORY);
        }
    };
    public static final Set<String> DISLIKED_TAG_SET_STRING = new HashSet<>() {
        {
            add(VALID_DISLIKED_TAGS);
        }
    };
    public static final Set<String> DISLIKED_LOCATION_SET_STRING = new HashSet<>() {
        {
            add(VALID_DISLIKED_LOCATION);
        }
    };

    public static final Set<Category> DISLIKED_CATEGORY_SET = new HashSet<>() {
        {
            add(new Category(VALID_DISLIKED_CATEGORY));
        }
    };
    public static final Set<Tag> DISLIKED_TAG_SET = new HashSet<>() {
        {
            add(new Tag(VALID_DISLIKED_TAGS));
        }
    };
    public static final Set<Location> DISLIKED_LOCATION_SET = new HashSet<>() {
        {
            add(new Location(VALID_DISLIKED_LOCATION));
        }
    };

    public static final Set<String> INVALID_CATEGORY_SET = new HashSet<>() {
        {
            add(INVALID_CATEGORY);
        }
    };
    public static final Set<String> INVALID_TAG_SET = new HashSet<>() {
        {
            add(INVALID_TAG);
        }
    };
    public static final Set<String> INVALID_LOCATION_SET = new HashSet<>() {
        {
            add(INVALID_LOCATION);
        }
    };

    public static final Set<Category> ADDED_CATEGORY_SET = new HashSet<>() {
        {
            add(new Category("added category"));
        }
    };
    public static final Set<Tag> ADDED_TAG_SET = new HashSet<>() {
        {
            add(new Tag("newtag"));
        }
    };
    public static final Set<Location> ADDED_LOCATION_SET = new HashSet<>() {
        {
            add(new Location("new location"));
        }
    };

    public static final Set<Category> ADDED_ANOTHER_CATEGORY_SET = new HashSet<>() {
        {
            add(new Category("another added category"));
        }
    };
    public static final Set<Tag> ADDED_ANOTHER_TAG_SET = new HashSet<>() {
        {
            add(new Tag("newtag1234567890"));
        }
    };
    public static final Set<Location> ADDED_ANOTHER_LOCATION_SET = new HashSet<>() {
        {
            add(new Location("another location"));
        }
    };

    public UserRecommendations getTypicalRecs() {
        return new UserRecommendations(LIKED_CATEGORY_SET, LIKED_TAG_SET, LIKED_LOCATION_SET,
                DISLIKED_CATEGORY_SET, DISLIKED_TAG_SET, DISLIKED_LOCATION_SET);
    }
}
