package seedu.revision.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.MCQ_STUB;
import static seedu.revision.testutil.TypicalMcqs.getTypicalRevisionTool;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.exceptions.DuplicateAnswerableException;
import seedu.revision.testutil.McqBuilder;

public class RevisionToolTest {

    private final RevisionTool revisionTool = new RevisionTool();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), revisionTool.getAnswerableList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> revisionTool.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyRevisionTool_replacesData() {
        RevisionTool newData = getTypicalRevisionTool();
        revisionTool.resetData(newData);
        assertEquals(newData, revisionTool);
    }

    @Test
    public void resetData_withDuplicateAnswerable_throwsDuplicateAnswerableException() {
        // Two answerables with the same identity fields
        Answerable editedAlice = new McqBuilder(MCQ_STUB).withCategories(VALID_CATEGORY_GREENFIELD)
                .build();
        List<Answerable> newAnswerables = Arrays.asList(MCQ_STUB, editedAlice);
        RevisionToolStub newData = new RevisionToolStub(newAnswerables);

        assertThrows(DuplicateAnswerableException.class, () -> revisionTool.resetData(newData));
    }

    @Test
    public void hasAnswerable_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> revisionTool.hasAnswerable(null));
    }

    @Test
    public void hasAnswerable_answerableNotInRevisionTool_returnsFalse() {
        assertFalse(revisionTool.hasAnswerable(MCQ_STUB));
    }

    @Test
    public void hasAnswerable_answerableInRevisionTool_returnsTrue() {
        revisionTool.addAnswerable(MCQ_STUB);
        assertTrue(revisionTool.hasAnswerable(MCQ_STUB));
    }

    @Test
    public void getAnswerableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> revisionTool.getAnswerableList().remove(0));
    }

    /**
     * A stub ReadOnlyRevisionTool whose answerables list can violate interface constraints.
     */
    private static class RevisionToolStub implements ReadOnlyRevisionTool {
        private final ObservableList<Answerable> answerables = FXCollections.observableArrayList();

        RevisionToolStub(Collection<Answerable> answerables) {
            this.answerables.setAll(answerables);
        }

        @Override
        public ObservableList<Answerable> getAnswerableList() {
            return answerables;
        }
    }

}
