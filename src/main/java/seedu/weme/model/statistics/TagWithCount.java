package seedu.weme.model.statistics;

import seedu.weme.model.tag.Tag;

/**
 * A wrapper class of {@code Tag} that carries the number of occurrence of such a tag
 * in a {@code memeList}for {@code TagManager} in statistics.
 */
public class TagWithCount extends TagWithStats implements Comparable<TagWithCount> {

    /**
     * Constructs a {@code TagWithCount}
     */
    public TagWithCount(Tag tag, int count) {
        super(tag, count);
    }

    @Override
    public String toString() {
        return getTag().tagName + " count: " + getData();
    }

    @Override
    public int compareTo(TagWithCount o) {
        int count = getData();
        int targetCount = o.getData();
        // Reverse the order so that tags with more occurrence appears before.
        if (count < targetCount) {
            return 1;
        } else if (count > targetCount) {
            return -1;
        } else {
            return getTag().tagName.compareTo(o.getTag().tagName);
        }
    }
}
