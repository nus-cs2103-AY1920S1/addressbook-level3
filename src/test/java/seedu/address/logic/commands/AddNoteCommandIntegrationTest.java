package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddNoteCommand}.
 */
public class AddNoteCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Note validNote = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addNote(validNote);

        assertCommandSuccess(new AddNoteCommand(validNote), model,
                String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Note noteInList = model.getAddressBook().getNoteList().get(0);
        assertCommandFailure(new AddNoteCommand(noteInList), model, AddNoteCommand.MESSAGE_DUPLICATE_TITLE);
    }

}
