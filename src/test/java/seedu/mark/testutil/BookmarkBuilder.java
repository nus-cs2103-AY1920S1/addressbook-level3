package seedu.mark.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;
import seedu.mark.model.util.SampleDataUtil;

/**
 * A utility class to help with building Bookmark objects.
 */
public class BookmarkBuilder {

    public static final String DEFAULT_NAME = "Alice Lee Website";
    public static final String DEFAULT_URL = "https://alice-lee.com";
    public static final String DEFAULT_REMARK = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Url url;
    private Remark remark;
    private Set<Tag> tags;

    public BookmarkBuilder() {
        name = new Name(DEFAULT_NAME);
        url = new Url(DEFAULT_URL);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the BookmarkBuilder with the data of {@code bookmarkToCopy}.
     */
    public BookmarkBuilder(Bookmark bookmarkToCopy) {
        name = bookmarkToCopy.getName();
        url = bookmarkToCopy.getUrl();
        remark = bookmarkToCopy.getRemark();
        tags = new HashSet<>(bookmarkToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Bookmark} that we are building.
     */
    public BookmarkBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Bookmark} that we are building.
     */
    public BookmarkBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Bookmark} that we are building.
     */
    public BookmarkBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code Bookmark} that we are building.
     */
    public BookmarkBuilder withUrl(String url) {
        this.url = new Url(url);
        return this;
    }

    public Bookmark build() {
        return new Bookmark(name, url, remark, tags);
    }

}
