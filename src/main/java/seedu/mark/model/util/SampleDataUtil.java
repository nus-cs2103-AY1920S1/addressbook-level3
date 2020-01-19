package seedu.mark.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import seedu.mark.model.Mark;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.autotag.AutotagController;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.reminder.Note;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.model.reminder.ReminderAssociation;
import seedu.mark.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Mark} with sample data.
 */
public class SampleDataUtil {

    private static final String SCHOOL = "School";
    private static final String INTERVIEW = "Interview";
    private static final String ENTERTAINMENT = "Entertainment";
    private static final String GENERAL = "General";
    private static final String MODULES = "Modules";
    private static final String VIDEOS = "Videos";

    public static Bookmark[] getSampleBookmarks() {
        return new Bookmark[] {
            new Bookmark(new Name("Mark User Guide"), new Url("https://ay1920s1-cs2103t-t13-4.github.io/main/"),
                    new Remark("User guide for Mark"),
                    new Folder(Folder.DEFAULT_FOLDER_NAME),
                    getTagSet(), new ArrayList<>()),
            new Bookmark(new Name("LumiNUS"), new Url("https://luminus.nus.edu.sg/"),
                    new Remark("Announcements, course materials"),
                    new Folder(GENERAL),
                    getTagSet("Favorite", "NUS"), new ArrayList<>()),
            new Bookmark(new Name("NUSMods"), new Url("https://nusmods.com/"),
                    new Remark("Timetable planning"),
                    new Folder(GENERAL),
                    getTagSet("NUS"), new ArrayList<>()),
            new Bookmark(new Name("CS2103 Website"), new Url("https://nus-cs2103-ay1920s1.github.io/website/"),
                    new Remark("Check for weekly updates"),
                    new Folder(MODULES),
                    getTagSet("NUS", "CS2103T"), new ArrayList<>()),
            new Bookmark(new Name("CS2103 Textbook"),
                    new Url("https://nus-cs2103-ay1920s1.github.io/website/se-book-adapted/index.html"),
                    new Remark("Learning materials for Software Engineering"),
                    new Folder(MODULES),
                    getTagSet("NUS", "CS2103T"), new ArrayList<>()),
            new Bookmark(new Name("CS2103T PollEv"), new Url("https://pollev.com/softeng"),
                    new Remark("Used for CS2103T in-lecture quiz"),
                    new Folder(MODULES),
                    getTagSet("NUS", "CS2103T"), new ArrayList<>()),
            new Bookmark(new Name("Python3 Tutorial"), new Url("https://docs.python.org/3/tutorial/"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("Python", "ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("PostgreSQL Documentation"),
                    new Url("https://www.postgresql.org/docs/current/tutorial.html"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("CS2102", "PostgreSQL"), new ArrayList<>()),
            new Bookmark(new Name("Java13 Documentation"),
                    new Url("https://docs.oracle.com/en/java/javase/13/docs/api/index.html"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("CS2030", "CS2040", "CS2103", "Java", "ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("LaTeX Tutorial"),
                    new Url("https://www.overleaf.com/learn/latex/Learn_LaTeX_in_30_minutes"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("NUS Libraries"),
                    new Url("https://libportal.nus.edu.sg/frontend/index"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(GENERAL),
                    getTagSet("NUS"), new ArrayList<>()),
            new Bookmark(new Name("Tech Interview Handbook"),
                    new Url("https://yangshun.github.io/tech-interview-handbook/"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(INTERVIEW),
                    getTagSet("ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("LeetCode"),
                    new Url("https://leetcode.com/"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(INTERVIEW),
                    getTagSet("ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("CS3243 Textbook"), new Url("http://aima.cs.berkeley.edu/"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("CS3243", "NUS"), new ArrayList<>()),
            new Bookmark(new Name("CS2103T Team GitHub"), new Url("https://github.com/AY1920S1-CS2103T-T13-4/main/"),
                    new Remark("Mark – Your Personal Bookmark Manager"),
                    new Folder(MODULES),
                    getTagSet("CS2103T", "NUS"), new ArrayList<>()),
            new Bookmark(new Name("PlantUML mix different diagram type elements"),
                    new Url("https://stackoverflow.com/questions/54020731/"
                            + "how-to-mix-different-plantuml-diagram-type-elements"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(MODULES),
                    getTagSet("StackOverflow", "PlantUML", "ProgrammingHelp"), new ArrayList<>()),
            new Bookmark(new Name("ThePianoGuys Rewrite The Stars"),
                    new Url("https://www.youtube.com/watch?v=5cINJwaAn4Y"),
                    new Remark("from The Greatest Showman"),
                    new Folder(VIDEOS),
                    getTagSet("Music", "Video", "WatchLater"), new ArrayList<>()),
            new Bookmark(new Name("IntelliJ IDEA Tips and Tricks"),
                    new Url("https://www.youtube.com/watch?v=9AMcN-wkspU"),
                    new Remark("for reference, watch on 2x speed"),
                    new Folder(MODULES),
                    getTagSet("ProgrammingHelp", "Video"), new ArrayList<>()),
            new Bookmark(new Name("BComp CS Curriculum"),
                    new Url("https://www.comp.nus.edu.sg/programmes/ug/cs/curr/"),
                    new Remark("Contains list of modules needed for graduation"),
                    new Folder(GENERAL),
                    getTagSet("Favorite"), new ArrayList<>()),
            new Bookmark(new Name("BComp CS Focus Areas"),
                    new Url("https://www.comp.nus.edu.sg/programmes/ug/focus/"),
                    new Remark("Contains list of modules needed for graduation"),
                    new Folder(GENERAL),
                    getTagSet(), new ArrayList<>()),
            new Bookmark(new Name("Get that job at Google"),
                    new Url("http://steve-yegge.blogspot.com/2008/03/get-that-job-at-google.html"),
                    new Remark("must read"),
                    new Folder(INTERVIEW),
                    getTagSet("Blog"), new ArrayList<>()),
            new Bookmark(new Name("I tried to reset my alarm clock"),
                    new Url("https://devblogs.microsoft.com/oldnewthing/20191104-01/?p=103052"),
                    new Remark(Remark.DEFAULT_VALUE),
                    new Folder(ENTERTAINMENT),
                    getTagSet("Funny", "IoT", "Blog"), new ArrayList<>()),
            new Bookmark(new Name("Why I left Google to join Grab"),
                    new Url("https://medium.com/@steve.yegge/why-i-left-google-to-join-grab-86dfffc0be84"),
                    new Remark("Insights into Google innovation culture and Grab's potential"),
                    new Folder(ENTERTAINMENT),
                    getTagSet("Blog"), new ArrayList<>()),
            new Bookmark(new Name("Bayesian Networks intro"),
                    new Url("https://towardsdatascience.com/introduction-to-bayesian-networks-81031eeed94e"),
                    new Remark("for last assignment of the sem"),
                    new Folder(MODULES),
                    getTagSet("ProgrammingHelp"), new ArrayList<>())
        };
    }

    public static FolderStructure getSampleFolderStructure() {
        FolderStructure general = new FolderStructure(new Folder(GENERAL), new ArrayList<>());
        FolderStructure modules = new FolderStructure(new Folder(MODULES), new ArrayList<>());
        FolderStructure school = new FolderStructure(new Folder(SCHOOL), List.of(general, modules));
        FolderStructure interview = new FolderStructure(new Folder(INTERVIEW), new ArrayList<>());
        FolderStructure videos = new FolderStructure(new Folder(VIDEOS), new ArrayList<>());
        FolderStructure entertainment = new FolderStructure(new Folder(ENTERTAINMENT), List.of(videos));
        FolderStructure root = new FolderStructure(Folder.ROOT_FOLDER, List.of(school, interview, entertainment));
        return root;
    }

    public static AutotagController getSampleAutotagController() {
        SelectiveBookmarkTagger videoAutotag = getTagger("Video",
                new BookmarkPredicate().withUrlKeywords(List.of("youtube.com")));
        SelectiveBookmarkTagger programmingHelpAutotag = getTagger("ProgrammingHelp",
                new BookmarkPredicate().withUrlKeywords(List.of("stackoverflow.com/questions")));
        SelectiveBookmarkTagger nusAutotag = getTagger("NUS",
                new BookmarkPredicate().withFolder(List.of(SCHOOL, GENERAL, MODULES)));
        return new AutotagController(
                FXCollections.observableArrayList(videoAutotag, programmingHelpAutotag, nusAutotag));
    }

    // NOTE: SampleReminder URLs must match URLs of SampleBookmarks
    public static List<Reminder> getSampleReminders() {
        return List.of(
                new Reminder(new Url("https://nus-cs2103-ay1920s1.github.io/website/se-book-adapted/index.html"),
                        LocalDateTime.of(2019, 11, 22, 12, 0),
                        new Note("Revise for finals")),
                new Reminder(new Url("https://nusmods.com/"),
                        LocalDateTime.of(2019, 12, 7, 10, 0),
                        new Note("Start planning next year's timetable")),
                new Reminder(new Url("https://www.comp.nus.edu.sg/programmes/ug/cs/curr/"),
                        LocalDateTime.of(2019, 12, 7, 10, 0),
                        new Note(Note.DEFAULT_VALUE))
        );
    }

    public static ReadOnlyMark getSampleMark() {
        Mark sampleMark = new Mark();
        Bookmark[] sampleBookmarks = getSampleBookmarks();
        for (Bookmark sampleBookmark : sampleBookmarks) {
            sampleMark.addBookmark(sampleBookmark);
        }
        sampleMark.setFolderStructure(getSampleFolderStructure());
        sampleMark.setAutotagController(getSampleAutotagController());

        ReminderAssociation reminderAssociation = new ReminderAssociation();
        for (Reminder sampleReminder : getSampleReminders()) {
            for (Bookmark sampleBookmark : sampleBookmarks) {
                if (sampleBookmark.getUrl().equals(sampleReminder.getUrl())) {
                    reminderAssociation.addReminder(sampleBookmark, sampleReminder);
                }
            }
        }
        sampleMark.setReminderAssociation(reminderAssociation);
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

    /**
     * Returns a {@code SelectiveBookmarkTagger} with the given tag name
     * and predicate.
     */
    public static SelectiveBookmarkTagger getTagger(String tagName, BookmarkPredicate predicate) {
        return new SelectiveBookmarkTagger(new Tag(tagName), predicate);
    }

}
