package seedu.mark.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.mark.model.Mark;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Mark} with sample data.
 */
public class SampleDataUtil {

    private static final String CONTACTS = "contacts";
    private static final String FAMILY = "family";

    public static Bookmark[] getSampleBookmarks() {
        return new Bookmark[]{
            new Bookmark(new Name("Alex Yeoh"), new Url("https://www.alexyeoh.com/"),
                    new Remark("Blk 30 Geylang Street 29, #06-40"),
                    new Folder(CONTACTS), getTagSet("friends")),
            new Bookmark(new Name("Bernice Yu"), new Url("https://www.berniceyu.com/"),
                    new Remark("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Folder(CONTACTS), getTagSet("colleagues", "friends")),
            new Bookmark(new Name("Charlotte Oliveiro"), new Url("http://charlotte.sg/"),
                    new Remark("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Folder(CONTACTS), getTagSet("neighbours")),
            new Bookmark(new Name("David Li"), new Url("https://david.org/"),
                    new Remark("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Folder(FAMILY), getTagSet("family")),
            new Bookmark(new Name("Irfan Ibrahim"), new Url("https://irfan-i.com/"),
                    new Remark("Blk 47 Tampines Street 20, #17-35"),
                    new Folder(CONTACTS), getTagSet("classmates")),
            new Bookmark(new Name("Roy Balakrishnan"), new Url("https://roy.facebook.com/"),
                    new Remark("Blk 45 Aljunied Street 85, #11-31"),
                    new Folder(CONTACTS), getTagSet("colleagues"))
        };
    }

    public static FolderStructure getSampleFolderStructure() {
        FolderStructure family = new FolderStructure(new Folder(FAMILY), new ArrayList<>());
        FolderStructure contacts = new FolderStructure(new Folder(CONTACTS), List.of(family));
        FolderStructure root = new FolderStructure(Folder.ROOT_FOLDER, List.of(contacts));
        return root;
    }

    public static ReadOnlyMark getSampleMark() {
        Mark sampleMark = new Mark();
        for (Bookmark sampleBookmark : getSampleBookmarks()) {
            sampleMark.addBookmark(sampleBookmark);
        }
        sampleMark.setFolderStructure(getSampleFolderStructure());
        return sampleMark;
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
