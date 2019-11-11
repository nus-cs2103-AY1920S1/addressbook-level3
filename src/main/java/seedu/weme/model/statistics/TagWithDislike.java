package seedu.weme.model.statistics;

import seedu.weme.model.tag.Tag;

/**
 * A wrapper class of {@code Tag} that carries the number of occurrence of such a tag
 * in a {@code MemeBook}for {@code TagManager} in statistics.
 */
public class TagWithDislike extends TagWithStats implements Comparable<TagWithDislike> {

    /**
     * Constructs a {@code TagWithCount}
     */
    public TagWithDislike(Tag tag, int dislike) {
        super(tag, dislike);
    }

    @Override
    public String toString() {
        return getTag().tagName + " dislikes: " + getData();
    }

    @Override
    public int compareTo(TagWithDislike o) {
        int count = getData();
        int targetCount = o.getData();
        // Reverse the order so that tags with more likes appears before.
        if (count < targetCount) {
            return 1;
        } else if (count > targetCount) {
            return -1;
        } else {
            return getTag().tagName.compareTo(o.getTag().tagName);
        }
    }
}
