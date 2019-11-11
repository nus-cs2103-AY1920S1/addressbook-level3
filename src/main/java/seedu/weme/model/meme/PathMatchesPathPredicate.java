package seedu.weme.model.meme;

import java.util.function.Predicate;

import seedu.weme.model.path.ImagePath;

/**
 * Tests that a {@code Meme}'s {@code Path} matches a path given.
 */
public class PathMatchesPathPredicate implements Predicate<Meme> {
    private final ImagePath path;

    public PathMatchesPathPredicate(ImagePath path) {
        this.path = path;
    }

    @Override
    public boolean test(Meme meme) {
        return path.equals(meme.getImagePath());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PathMatchesPathPredicate // instanceof handles nulls
                && path.equals(((PathMatchesPathPredicate) other).path)); // state check
    }

}
