package seedu.address.model.note;

import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.function.Predicate;

public class NoteFragmentContainsTagPredicate implements Predicate<NoteFragment> {
    private final Set<Tag> tags;

    public NoteFragmentContainsTagPredicate (Set<Tag> tags) {
        this.tags = tags;
    }

    // test on the note to see if he has the tag
    @Override
    public boolean test(NoteFragment noteFragment) {
        return tags.stream()
                .anyMatch(noteFragment::containsTag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteFragmentContainsTagPredicate // instanceof handles nulls
                && tags.equals(((NoteFragmentContainsTagPredicate) other).tags)); // state check
    }
}
