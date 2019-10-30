package seedu.mark.model.predicates;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;

/**
 * A wrapper for {@code Predicate<Bookmark>} that also stores the keywords used
 * in the current predicate.
 */
public class BookmarkPredicate implements Predicate<Bookmark> {
    // TODO: Find a way to reduce code duplication

    private final Set<String> nameKeywords;
    private final Set<String> notNameKeywords;
    private final Set<String> urlKeywords;
    private final Set<String> notUrlKeywords;
    private final Set<String> folderNames;
    private final Set<String> notFolderNames;

    private final Predicate<Bookmark> predicate;

    private boolean isEmpty;

    /**
     * Constructs a new, empty {@code BookmarkPredicate} that returns true whenever
     * {@code predicate#test(Bookmark)} is called.
     */
    public BookmarkPredicate() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(),
                new HashSet<>(), new HashSet<>(), bookmark -> true);
        this.isEmpty = true;
    }

    private BookmarkPredicate(Set<String> nameKeywords, Set<String> notNameKeywords,
                              Set<String> urlKeywords, Set<String> notUrlKeywords,
                              Set<String> folderNames, Set<String> notFolderNames,
                              Predicate<Bookmark> predicate) {
        this.nameKeywords = new HashSet<>(nameKeywords);
        this.notNameKeywords = new HashSet<>(notNameKeywords);
        this.urlKeywords = new HashSet<>(urlKeywords);
        this.notUrlKeywords = new HashSet<>(notUrlKeywords);
        this.folderNames = folderNames;
        this.notFolderNames = notFolderNames;
        this.predicate = predicate;
    }

    public Set<String> getNameKeywords() {
        return nameKeywords;
    }

    public Set<String> getNotNameKeywords() {
        return notNameKeywords;
    }

    public Set<String> getUrlKeywords() {
        return urlKeywords;
    }

    public Set<String> getNotUrlKeywords() {
        return notUrlKeywords;
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

        Set<String> newNameKeywords = new HashSet<>(this.nameKeywords);
        newNameKeywords.addAll(nameKeywords);
        return new BookmarkPredicate(newNameKeywords,
                this.notNameKeywords, this.urlKeywords, this.notUrlKeywords,
                this.folderNames, this.notFolderNames,
                predicate.and(new NameContainsKeywordsPredicate(nameKeywords)));
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

        Set<String> newNotNameKeywords = new HashSet<>(this.notNameKeywords);
        newNotNameKeywords.addAll(notNameKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                newNotNameKeywords, this.urlKeywords, this.notUrlKeywords,
                this.folderNames, this.notFolderNames,
                predicate.and(new NameContainsKeywordsPredicate(notNameKeywords).negate()));
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

        Set<String> newUrlKeywords = new HashSet<>(this.urlKeywords);
        newUrlKeywords.addAll(urlKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, newUrlKeywords, this.notUrlKeywords,
                this.folderNames, this.notFolderNames, predicate.and(new UrlContainsKeywordsPredicate(urlKeywords)));
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

        Set<String> newNotUrlKeywords = new HashSet<>(this.notUrlKeywords);
        newNotUrlKeywords.addAll(notUrlKeywords);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, this.urlKeywords, newNotUrlKeywords,
                this.folderNames, this.notFolderNames,
                predicate.and(new UrlContainsKeywordsPredicate(notUrlKeywords).negate()));
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and is in one of the folders in {@code folderNames}.
     *
     * @param folderNames names of folders that a bookmark can be in.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new Folder names.
     */
    public BookmarkPredicate withFolder(List<String> folderNames) {
        requireNonNull(folderNames);
        isEmpty = false;

        Set<String> newFolderNames = new HashSet<>(this.folderNames);
        newFolderNames.addAll(folderNames);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, this.urlKeywords, this.notUrlKeywords,
                newFolderNames, this.notFolderNames,
                predicate.and(new FolderContainsKeywordsPredicate(folderNames)));
    }

    /**
     * Creates a new {@code BookmarkPredicate} that checks that a bookmark matches
     * the current predicate and is not in any of the folders in {@code notFolderNames}.
     *
     * @param notFolderNames names of folders that a bookmark should not be in.
     * @return a new {@code BookmarkPredicate} that contains all of the current
     *         information and includes the new notFolderNames.
     */
    public BookmarkPredicate withoutFolder(List<String> notFolderNames) {
        requireNonNull(notFolderNames);
        isEmpty = false;

        Set<String> newNotFolderNames = new HashSet<>(this.folderNames);
        newNotFolderNames.addAll(notFolderNames);
        return new BookmarkPredicate(this.nameKeywords,
                this.notNameKeywords, this.urlKeywords, this.notUrlKeywords,
                this.folderNames, newNotFolderNames,
                predicate.and(new FolderContainsKeywordsPredicate(notFolderNames).negate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkPredicate // instanceof handles nulls
                && nameKeywords.equals(((BookmarkPredicate) other).nameKeywords)
                && notNameKeywords.equals(((BookmarkPredicate) other).notNameKeywords)
                && urlKeywords.equals(((BookmarkPredicate) other).urlKeywords)
                && notUrlKeywords.equals((((BookmarkPredicate) other).notUrlKeywords))); // state check
    }

    @Override
    public String toString() {
        return "[name: " + nameKeywords + ", not name: " + notNameKeywords + ", url: "
                + urlKeywords + ", not url: " + notUrlKeywords + "]";
        // TODO: Improve toString()
    }
}
