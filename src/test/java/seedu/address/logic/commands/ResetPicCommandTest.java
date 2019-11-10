package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ResetPicCommandTest {

    private Model model = new ModelManager(getTypicalNotebook(), new UserPrefs());

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        ResetDisplayPictureCommand resetCommand = new ResetDisplayPictureCommand(INDEX_FIRST_OBJECT);
        assertCommandFailure(resetCommand, model, ResetDisplayPictureCommand.MESSAGE_PICTURE_ALREADY_DEFAULT);
    }
    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        ResetDisplayPictureCommand editStudentCommand = new ResetDisplayPictureCommand(outOfBoundIndex);
        assertCommandFailure(editStudentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }
}
