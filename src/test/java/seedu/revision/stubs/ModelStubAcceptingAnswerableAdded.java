package seedu.revision.stubs;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.revision.model.ReadOnlyRevisionTool;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answerable;

/**
 * A Model stub that always accept the answerable being added.
 */
public class ModelStubAcceptingAnswerableAdded extends ModelStub {
    final ArrayList<Answerable> answerablesAdded = new ArrayList<>();

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return answerablesAdded.stream().anyMatch(answerable::isSameAnswerable);
    }

    @Override
    public void addAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        answerablesAdded.add(answerable);
    }

    @Override
    public ReadOnlyRevisionTool getRevisionTool() {
        return new RevisionTool();
    }

    public ArrayList<Answerable> getAnswerablesAdded() {
        return answerablesAdded;
    }
}

