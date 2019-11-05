package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CheatSheet objects.
 */
public class CheatSheetBuilder {

    public static final String DEFAULT_TITLE = "title";

    private Title title;
    private Set<Content> contents;
    private Set<Tag> tags;

    public CheatSheetBuilder() {
        title = new Title(DEFAULT_TITLE);
        this.contents = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the CheatSheetBuilder with the data of {@code cheatSheetToCopy}.
     */
    public CheatSheetBuilder(CheatSheet cheatSheetToCopy) {
        title = cheatSheetToCopy.getTitle();
        contents = cheatSheetToCopy.getContents();
        tags = new HashSet<>(cheatSheetToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code CheatSheet} that we are building.
     */
    public CheatSheetBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code CheatSheet} that we are building.
     */
    public CheatSheetBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code contents} into a {@code Set<Content>}
     * and set it to the {@code CheatSheet} that we are building.
     */
    public CheatSheetBuilder withContents(String ... contents) {
        this.contents = SampleDataUtil.getContentSet(contents);
        return this;
    }

    public CheatSheet build() {
        return new CheatSheet(title, contents, tags);
    }

}
