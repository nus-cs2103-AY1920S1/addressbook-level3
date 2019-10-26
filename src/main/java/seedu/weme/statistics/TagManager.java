package seedu.weme.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Tag manager in Statistics feature that reads tags from a {@code MemeBook}.
 */
public class TagManager {

    public static final int INITIAL_LIKE_COUNT = 0;

    private Set<Tag> tags;
    private List<TagWithCount> tagsWithCount;

    public TagManager() {
        tags = new HashSet<>();
        tagsWithCount = new ArrayList<>();
    }

    public Set<Tag> getTagsInSet() {
        return tags;
    }

    /**
     * Returns {@code TagWithCount} in List.
     */
    public List<TagWithCount> getTagsWithCountList(List<Meme> memeList) {
        parseMemeListForTags(memeList);

        return tagsWithCount;
    }

    /**
     * Resets the data.
     */
    public void purgeData() {
        tags.clear();
        tagsWithCount.clear();
    }

    /**
     * Parses a {@code ReadOnlyMemeBook} for tags.
     * @param memeList
     */
    public void parseMemeListForTags(List<Meme> memeList) {
        purgeData();
        Map<Tag, Integer> tagToCount = new HashMap<>();
        Set<Tag> memeTags;

        for (Meme meme : memeList) {
            memeTags = meme.getTags();
            tags.addAll(memeTags);
            for (Tag tag : memeTags) {
                int count = tagToCount.getOrDefault(tag, INITIAL_LIKE_COUNT);
                tagToCount.put(tag, count + 1);
            }
        }

        for (Map.Entry<Tag, Integer> mapEntry : tagToCount.entrySet()) {
            tagsWithCount.add(new TagWithCount(mapEntry.getKey(), mapEntry.getValue()));
        }

        tagsWithCount.sort(Comparator.naturalOrder());
    }

}
