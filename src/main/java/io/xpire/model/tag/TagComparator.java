package io.xpire.model.tag;

import java.util.Comparator;

/**
 * Comparator which specifies the lexicographical ordering of tags
 */
public class TagComparator implements Comparator<Tag> {

    @Override
    public int compare(Tag o1, Tag o2) {
        return o1.getTagName().compareTo(o2.getTagName());
    }

}
