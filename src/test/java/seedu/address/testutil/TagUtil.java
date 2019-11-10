package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.model.tag.Tag;

/**
 * Helper class used to help generate the relevant objects for Tags.
 */
public class TagUtil {

    /**
     * Generates a list of strings from the tag keyword strings specified.
     * @param args strings of tags
     * @return list of tag strings
     */
    public static String[] generateKeywordList(String... args) {
        ArrayList<String> keywords = new ArrayList<>();
        for (String s: args) {
            keywords.add(s);
        }
        return keywords.toArray(new String[keywords.size()]);
    }

    /**
     * Generates a set of tags from the string arguments.
     * @param args strings of tags
     * @return set of tags
     */
    public static HashSet<Tag> generateTagSetFromStrings(String... args) {
        HashSet<Tag> tags = new HashSet<>();
        for (String tag: args) {
            tags.add(new Tag(tag));
        }
        return tags;
    }
}
