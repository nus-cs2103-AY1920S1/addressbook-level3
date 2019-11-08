package seedu.address.testutil;

import seedu.address.model.Notebook;

import static seedu.address.testutil.TypicalClassrooms.getTypicalClassroom;
import static seedu.address.testutil.TypicalLessons.getTypicalLessons;

public class TypicalNotebook {

    public static final Notebook NOTEBOOK = new NotebookBuilder()
            .withClassrooms(getTypicalClassroom())
            .withLessons(getTypicalLessons()).build();

    public TypicalNotebook() {};

    public static Notebook getTypicalNotebook() {
        return new Notebook(NOTEBOOK);
    }
}
