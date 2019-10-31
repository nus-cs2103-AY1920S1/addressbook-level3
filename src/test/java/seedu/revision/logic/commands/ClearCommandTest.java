package seedu.revision.logic.commands;

import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.AddressBook;
import seedu.revision.model.History;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() throws ParseException {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() throws ParseException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new History());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new History());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
