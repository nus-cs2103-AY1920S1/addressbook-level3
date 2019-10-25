package seedu.mark.model.predicates;

import java.util.function.Predicate;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;

/**
 * Tests that if {@code Bookmark} contains tag Favorite.
 */
public class FavoriteBookmarkPredicate implements Predicate<Bookmark> {
    @Override
    public boolean test(Bookmark bookmark) {
        return bookmark.getTags().contains(Tag.FAVORITE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof FavoriteBookmarkPredicate;
    }

}
