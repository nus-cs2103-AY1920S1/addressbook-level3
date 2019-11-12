package seedu.address.model.note;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Note}'s {@code Tag} matches any of the keywords given.
 */
public class NoteContainsTagPredicate implements Predicate<Note> {
    private final Set<Tag> tags;

    private final Logger logger = LogsCenter.getLogger(NoteContainsTagPredicate.class.getName());

    public NoteContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
        logger.info("Filtering Notes that contain at least the specified tags");
    }

    // test on the note to see if he has the tag
    @Override
    public boolean test(Note note) {
        boolean hasMatchingTags;
        if (tags.isEmpty()) {
            hasMatchingTags = false;
        } else {
            hasMatchingTags = tags.stream()
                    .allMatch(note::containsTag);
        }
        return hasMatchingTags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteContainsTagPredicate // instanceof handles nulls
                && tags.equals(((NoteContainsTagPredicate) other).tags)); // state check
    }
}
