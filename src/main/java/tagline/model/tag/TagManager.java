package tagline.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Represents the in-memory model of all tag data.
 */
public class TagManager implements TagModel {
    private final TagBook tagBook;

    /**
     * Initializes a TagManager with the given {@code tagBook}.
     */
    public TagManager(ReadOnlyTagBook tagBook) {
        this.tagBook = new TagBook(tagBook);
    }

    @Override
    public void setTagBook(ReadOnlyTagBook tagBook) {
        this.tagBook.resetData(tagBook);
    }

    @Override
    public ReadOnlyTagBook getTagBook() {
        return tagBook;
    }

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tagBook.hasTag(tag);
    }

    @Override
    public void addTag(Tag tag) {
        requireNonNull(tag);
        tagBook.addTag(tag);
    }

    @Override
    public Optional<Tag> findTag(Tag tag) {
        requireNonNull(tag);
        return tagBook.findTag(tag);
    }
}
