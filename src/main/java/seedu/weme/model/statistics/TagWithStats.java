package seedu.weme.model.statistics;

import seedu.weme.model.tag.Tag;

/**
 * A wrapper class of {@code Tag} that carries the number of occurrence of such a tag
 * in a {@code MemeBook}for {@code TagManager} in statistics.
 */
public abstract class TagWithStats {

    private Tag tag;
    private int data;

    /**
     * Constructs a {@code TagWithStats}
     * @param tag
     * @param data
     */
    public TagWithStats(Tag tag, int data) {
        this.tag = tag;
        this.data = data;
    }

    public Tag getTag() {
        return tag;
    }

    public int getData() {
        return data;
    }

    public abstract String toString();

}
