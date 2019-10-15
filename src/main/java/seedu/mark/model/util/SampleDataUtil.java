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

    private static final String SCHOOL = "School";
    private static final String ENTERTAINMENT = "Entertainment";
    private static final String GENERAL = "General";

    public static Bookmark[] getSampleBookmarks() {
        return new Bookmark[] {
            new Bookmark(new Name("Luminus"), new Url("https://luminus.nus.edu.sg/"),
                    new Remark("Announcements, course materials"),
                    new Folder(GENERAL),
                    getTagSet("NUS")),
            new Bookmark(new Name("NUSMods"), new Url("https://nusmods.com/"),
                    new Remark("Timetable planning"),
                    new Folder(GENERAL),
                    getTagSet("NUS")),
            new Bookmark(new Name("CS2103T Website"), new Url("https://nus-cs2103-ay1920s1.github.io/website/"),
                    new Remark("Learning materials for Software Engineering"),
                    new Folder(SCHOOL),
                    getTagSet("NUS", "CS2103T")),
            new Bookmark(new Name("Facebook"), new Url("https://www.facebook.com/"),
                    new Remark("-"),
                    new Folder(ENTERTAINMENT),
                    getTagSet("SocialMedia")),
            new Bookmark(new Name("Reddit"), new Url("https://www.reddit.com/"),
                new Remark(Remark.DEFAULT_VALUE),
                    new Folder(ENTERTAINMENT),
                    getTagSet("SocialMedia")),
            new Bookmark(new Name("McDelivery"), new Url("https://www.mcdelivery.com.sg/sg/"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(Folder.DEFAULT_FOLDER_NAME),
                    getTagSet("Food"))
        };
    }

    public static FolderStructure getSampleFolderStructure() {
        FolderStructure general = new FolderStructure(new Folder(GENERAL), new ArrayList<>());
        FolderStructure school = new FolderStructure(new Folder(SCHOOL), List.of(general));
        FolderStructure entertainment = new FolderStructure(new Folder(ENTERTAINMENT), new ArrayList<>());
        FolderStructure root = new FolderStructure(Folder.ROOT_FOLDER, List.of(school, entertainment));
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
