package tagline.testutil.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tagline.model.tag.Tag;
import tagline.model.tag.TagBook;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTags {

    public static final Tag CONTACT_TAG = new ContactTagBuilder().withContactId("12345").build();

    public static final Tag HASH_TAG = new HashTagBuilder().withContent("temp-hash").build();

    public static final Tag GROUP_TAG = new GroupTagBuilder().withGroupName("group-name").build();


    private TypicalTags() {
    } // prevents instantiation

    /**
     * Returns an {@code TagBook} with all the typical persons.
     */
    public static TagBook getTypicalTagBook() {
        TagBook tb = new TagBook();
        for (Tag tag : getTypicalTags()) {
            tb.addTag(tag);
        }
        return tb;
    }

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(CONTACT_TAG, HASH_TAG, GROUP_TAG));
    }
}
