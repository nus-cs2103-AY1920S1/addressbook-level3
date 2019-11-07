package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.model.tag.Tag;

public class TagUtil {

    public static ArrayList<String> generateKeywordList(String... args) {
        ArrayList<String> keywords = new ArrayList<>();
        for (String s: args) {
            keywords.add(s);
        }
        return keywords;
    }

    public static HashSet<Tag> generateTagSetFromStrings(String... args) {
        HashSet<Tag> tags = new HashSet<>();
        for (String tag: args) {
            tags.add(new Tag(tag));
        }
        return tags;
    }
}
