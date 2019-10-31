package tagline.testutil.tag;

import tagline.model.tag.Tag;
import tagline.model.tag.TagBook;

/**
 * A utility class to help with building Groupbook objects.
 * Example usage: <br>
 * {@code GroupBook ab = new GroupBookBuilder().withGroup(ULTRON).build();}
 */
public class TagBookBuilder {

    private TagBook tagBook;

    public TagBookBuilder() {
        tagBook = new TagBook();
    }

    public TagBookBuilder(TagBook tagBook) {
        this.tagBook = tagBook;
    }

    /**
     * Adds a new {@code Group} to the {@code GroupBook} that we are building.
     */
    public TagBookBuilder withTag(Tag tag) {
        tagBook.addTag(tag);
        return this;
    }

    public TagBook build() {
        return tagBook;
    }
}
