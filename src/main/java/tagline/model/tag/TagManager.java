package tagline.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of all tag data.
 */
public class TagManager implements TagModel {
    private final TagBook tagBook;
    private final FilteredList<Tag> filteredTags;


    /**
     * Initializes a TagManager with the given {@code tagBook}.
     */
    public TagManager(ReadOnlyTagBook tagBook) {
        this.tagBook = new TagBook(tagBook);
        filteredTags = new FilteredList<>(this.tagBook.getTagList());
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
    public void deleteTag(Tag tag) {
        requireNonNull(tag);
        tagBook.removeTag(tag);
    }

    /**
     * Find tag in the tag book.
     * @param tag A tag to be searched.
     * @return An optional of {@code Tag} if the {@code Tag} exists in the tag book.
     */
    public Optional<Tag> findTag(Tag tag) {
        requireNonNull(tag);
        return tagBook.findTag(tag);
    }

    //=========== Filtered Contact List Accessors =============================================================

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    @Override
    public ObservableList<Tag> getFilteredTagListWithPredicate(Predicate<Tag> predicate) {
        requireNonNull(predicate);

        FilteredList<Tag> filteredTagsCopy = new FilteredList<>(tagBook.getTagList());
        filteredTagsCopy.setPredicate(predicate);
        return filteredTagsCopy;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof TagManager)) {
            return false;
        }

        // state check
        TagManager other = (TagManager) obj;
        return tagBook.equals(other.tagBook)
            && filteredTags.equals(other.filteredTags);
    }
}
