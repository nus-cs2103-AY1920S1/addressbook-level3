package seedu.mark.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.mark.model.BookmarkManager;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.bookmark.Address;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Phone;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookmarkManager} with sample data.
 */
public class SampleDataUtil {
    public static Bookmark[] getSampleBookmarks() {
        return new Bookmark[] {
            new Bookmark(new Name("Alex Yeoh"), new Phone("87438807"), new Url("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Bookmark(new Name("Bernice Yu"), new Phone("99272758"), new Url("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Bookmark(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Url("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Bookmark(new Name("David Li"), new Phone("91031282"), new Url("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Bookmark(new Name("Irfan Ibrahim"), new Phone("92492021"), new Url("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Bookmark(new Name("Roy Balakrishnan"), new Phone("92624417"), new Url("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyBookmarkManager getSampleBookmarkManager() {
        BookmarkManager sampleBm = new BookmarkManager();
        for (Bookmark sampleBookmark : getSampleBookmarks()) {
            sampleBm.addBookmark(sampleBookmark);
        }
        return sampleBm;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
