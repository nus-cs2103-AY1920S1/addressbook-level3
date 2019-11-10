package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClassrooms.CLASSROOM_TWO;
import static seedu.address.testutil.TypicalNotebook.getTypicalNotebook;

import org.junit.jupiter.api.Test;

//@@author weikiat97
public class CaretakerTest {

    private final Notebook notebook = new Notebook(getTypicalNotebook());
    private final Caretaker caretaker = new Caretaker(this.notebook);


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Caretaker(null));
    }

    @Test
    public void undo_withValidCommand_undo() {
        notebook.addClassroom(CLASSROOM_TWO);
        caretaker.saveState();
        ReadOnlyNotebook previousNotebook = caretaker.undo();
        assertEquals(caretaker.getInitialState(), previousNotebook);
    }

    @Test
    public void undo_withNoNewCommand_cannotUndo() {
        assertThrows(IndexOutOfBoundsException.class, () -> caretaker.undo());
    }

    @Test
    public void undo_withInvalidCommand_cannotUndo() {
        notebook.displayAssignments();
        assertThrows(IndexOutOfBoundsException.class, () -> caretaker.undo());
    }

    @Test
    public void redo_withValidCommand_redo() {
        notebook.addClassroom(CLASSROOM_TWO);
        caretaker.saveState();
        ReadOnlyNotebook currentNotebook = new Notebook(this.notebook);
        ReadOnlyNotebook previousNotebook = caretaker.undo();
        ReadOnlyNotebook redoneNotebook = caretaker.redo();
        assertEquals(currentNotebook, redoneNotebook);
    }

    @Test
    public void redo_withNoNewCommand_cannotRedo() {
        assertThrows(IndexOutOfBoundsException.class, () -> caretaker.redo());
    }

    @Test
    public void canRedo_withValidCommand_canUndo() {
        notebook.addClassroom(CLASSROOM_TWO);
        caretaker.saveState();
        caretaker.undo();
        boolean canRedo = caretaker.canRedo();
        assertEquals(canRedo, true);
    }

    @Test
    public void canRedo_nothingToRedo_cannotRedo() {
        boolean canRedo = caretaker.canRedo();
        assertEquals(canRedo, false);
    }
}
