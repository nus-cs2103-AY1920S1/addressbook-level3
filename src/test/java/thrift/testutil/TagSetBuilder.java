package thrift.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thrift.model.tag.Tag;
import thrift.model.transaction.Transaction;

/**
 * A utility class to help with building Tag sets.
 *
 * Example usage: <br>
 *     {@code Set<Tag> tagSet = new TagSetBuilder("Food").build();}
 */
public class TagSetBuilder {

    private final Set<Tag> tagSet;

    /**
     * Creates a blank TagSetBuilder with no tags
     */
    public TagSetBuilder() {
        this.tagSet = new HashSet<Tag>();
    }

    /**
     * Creates a TagSetBuilder with tags made from {@param tagNames}
     *
     * @param tagNames whose tags are used as reference
     */
    public TagSetBuilder(String... tagNames) {
        this.tagSet = (Stream.of(tagNames).map(Tag::new).collect(Collectors.toSet()));
    }

    /**
     * Creates a TagSetBuilder with tags from {@param tagSet}
     *
     * @param tagSet whose tags are used as reference
     */
    public TagSetBuilder(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    /**
     * Creates a TagSetBuilder with tags from {@param transaction}
     *
     * @param transaction whose tags are used as reference
     */
    public TagSetBuilder(Transaction transaction) {
        this.tagSet = new HashSet<Tag>(transaction.getTags());
    }

    /**
     * Returns a {@code TagSetBuilder} with a tag having tagName appended
     *
     * @param tagName to be made into a {@code Tag} object and appended
     * @return {@code TagSetBuilder} with a tag having tagName appended
     */
    public TagSetBuilder addTag(String tagName) {
        tagSet.add(new Tag(tagName));
        return new TagSetBuilder(tagSet);
    }

    /**
     * Returns a {@code TagSetBuilder} with tags having tagNames appended
     *
     * @param tagNames to be made into {@code Tag} objects and appended
     * @return {@code TagSetBuilder} with tags having tagNames appended
     */
    public TagSetBuilder addTags(String... tagNames) {
        Set<Tag> tagSet = new HashSet<Tag>(this.tagSet);
        tagSet.addAll(Stream.of(tagNames).map(Tag::new).collect(Collectors.toSet()));
        return new TagSetBuilder(tagSet);
    }

    public Set<Tag> build() {
        return tagSet;
    }
}
