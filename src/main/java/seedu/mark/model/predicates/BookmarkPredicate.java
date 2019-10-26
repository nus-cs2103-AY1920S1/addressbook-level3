package seedu.mark.model.predicates;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * A wrapper for {@code Predicate<Bookmark>} that also stores the keywords used
 * in the current predicate.
 */
public class BookmarkPredicate implements Predicate<Bookmark> {
    private final List<String> nameKeywords;
    private final List<String> notNameKeywords;
    private final List<String> urlKeywords;
    private final List<String> notUrlKeywords;

    private final Predicate<Bookmark> predicate;

    private boolean isEmpty;

    /**
     * Constructs a new, empty {@code BookmarkPredicate} that returns true whenever
     * {@code predicate#test(Bookmark)} is called.
     */
    public BookmarkPredicate() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), bookmark -> true);
        this.isEmpty = true;
    }

    private BookmarkPredicate(List<String> nameKeywords, List<String> notNameKeywords,
                             List<String> urlKeywords, List<String> notUrlKeywords, Predicate<Bookmark> predicate) {
        this.nameKeywords = nameKeywords;
        this.notNameKeywords = notNameKeywords;
        this.urlKeywords = urlKeywords;
        this.notUrlKeywords = notUrlKeywords;
        this.predicate = predicate;
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return predicate.test(bookmark);
    }

    /**
     * Checks whether this {@code BookmarkPredicate} is empty (i.e. has no keywords).
     * An empty predicate returns true whenever {@code predicate#test(Bookmark)} is called.
     *
     * @return true if the predicate is empty and false otherwise.
     */
    public boolean isEmpty() {
        return this.isEmpty;
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and matches any of the keywords in {@code nameKeywords}.
     *
     * @param nameKeywords keywords that a bookmark's name should contain.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new name keywords.
     */
    public BookmarkPredicate withNameKeywords(List<String> nameKeywords) {
        requireNonNull(nameKeywords);
        isEmpty = false;

        List<String> newNameKeywords = new ArrayList<>(nameKeywords);
        newNameKeywords.addAll(nameKeywords);
        return new BookmarkPredicate(newNameKeywords,
                this.notNameKeywords, this.urlKeywords,
                this.notUrlKeywords, predicate.and(new NameContainsKeywordsPredicate(nameKeywords)));
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and does not match any of the keywords in
     * {@code notNameKeywords}.
     *
     * @param notNameKeywords keywords that a bookmark's name should not contain.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new not-name keywords.
     */
    public BookmarkPredicate withoutNameKeywords(List<String> notNameKeywords) {
        requireNonNull(notNameKeywords);
        isEmpty = false;

        List<String> newNotNameKeywords = new ArrayList<>(notNameKeywords);
        newNotNameKeywords.addAll(notNameKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                newNotNameKeywords, this.urlKeywords,
                this.notUrlKeywords, predicate.and(new NameContainsKeywordsPredicate(notNameKeywords).negate()));
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and matches any of the keywords in {@code urlKeywords}.
     *
     * @param urlKeywords keywords that a bookmark's name should contain.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new URL keywords.
     */
    public BookmarkPredicate withUrlKeywords(List<String> urlKeywords) {
        requireNonNull(urlKeywords);
        isEmpty = false;

        List<String> newUrlKeywords = new ArrayList<>(urlKeywords);
        newUrlKeywords.addAll(urlKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, newUrlKeywords,
                this.notUrlKeywords, predicate.and(new UrlContainsKeywordsPredicate(urlKeywords)));
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and does not match any of the keywords in
     * {@code notUrlKeywords}.
     *
     * @param notUrlKeywords keywords that a bookmark's URL should not contain.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new not-URL keywords.
     */
    public BookmarkPredicate withoutUrlKeywords(List<String> notUrlKeywords) {
        requireNonNull(notUrlKeywords);
        isEmpty = false;

        List<String> newNotUrlKeywords = new ArrayList<>(notUrlKeywords);
        newNotUrlKeywords.addAll(notUrlKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, this.urlKeywords,
                newNotUrlKeywords, predicate.and(new UrlContainsKeywordsPredicate(notUrlKeywords).negate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkPredicate // instanceof handles nulls
                && nameKeywords.equals(((BookmarkPredicate) other).nameKeywords)); // state check
    }

}
