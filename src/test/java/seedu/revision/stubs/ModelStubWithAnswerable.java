package seedu.revision.stubs;

import static java.util.Objects.requireNonNull;

import seedu.revision.model.answerable.Answerable;

/**
 * A Model stub that contains a single answerable.
 */
public class ModelStubWithAnswerable extends ModelStub {
    private final Answerable answerable;

    public ModelStubWithAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        this.answerable = answerable;
    }

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return this.answerable.isSameAnswerable(answerable);
    }
}

