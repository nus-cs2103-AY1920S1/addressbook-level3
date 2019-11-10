package seedu.address.testutil;

import static seedu.address.testutil.TypicalClassrooms.getTypicalClassroom;
import static seedu.address.testutil.TypicalLessons.getTypicalLessons;

import seedu.address.model.Notebook;

/**
 * A utility class containing a list of {@code Notebook} objects to be used in tests.
 */
public class TypicalNotebook {

    public static final Notebook NOTEBOOK = new NotebookBuilder()
            .withClassrooms(getTypicalClassroom())
            .withLessons(getTypicalLessons()).build();

    public TypicalNotebook() {};

    public static Notebook getTypicalNotebook() {
        return new Notebook(NOTEBOOK);
    }
}
