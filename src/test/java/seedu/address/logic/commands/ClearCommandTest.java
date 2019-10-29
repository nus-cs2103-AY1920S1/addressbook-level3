package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.bio.UserList;
import seedu.address.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserList(), new UniqueFoodList(),
            new UniqueRecordList(), new Calendar());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserList(),
            new UniqueFoodList(), new UniqueRecordList(), new Calendar());
        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
