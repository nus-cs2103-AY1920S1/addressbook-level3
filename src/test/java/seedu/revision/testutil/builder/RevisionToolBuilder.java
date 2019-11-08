package seedu.revision.testutil.builder;

import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answerable;

/**
 * A utility class to help with building Revision Tool objects.
 * Example usage: <br>
 *     {@code RevisionTool ab = new RevisionToolBuilder().withAnswerable("John", "Doe").build();}
 */
public class RevisionToolBuilder {

    private RevisionTool revisionTool;

    public RevisionToolBuilder() {
        revisionTool = new RevisionTool();
    }

    public RevisionToolBuilder(RevisionTool revisionTool) {
        this.revisionTool = revisionTool;
    }

    /**
     * Adds a new {@code Answerable} to the {@code RevisionTool} that we are building.
     */
    public RevisionToolBuilder withAnswerable(Answerable answerable) {
        revisionTool.addAnswerable(answerable);
        return this;
    }

    public RevisionTool build() {
        return revisionTool;
    }
}
