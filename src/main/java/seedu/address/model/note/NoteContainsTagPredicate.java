package seedu.address.model.note;

import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.function.Predicate;

public class NoteContainsTagPredicate implements Predicate<Note> {
    private final Set<Tag> tags;

    public NoteContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
    }

    // test on the note to see if he has the tag
    @Override
    public boolean test(Note note) {
        return tags.stream()
                .anyMatch(note::containsTag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteContainsTagPredicate // instanceof handles nulls
                && tags.equals(((NoteContainsTagPredicate) other).tags)); // state check
    }
}
