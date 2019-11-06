package seedu.address.logic.commands.itinerary.days.edit;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TravelPal;
import seedu.address.model.UserPrefs;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.Budget;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class CancelEditDayCommandTest {
    ModelManager model = AddCommand.

    @Test
    public void execute_emptyTravelPalCreate_success() {
        PageStatus pageStatus = new PageStatus(PageType.ADD_DAY, null, null, null, null,
                null, null, null, null ,null,
                null, null, null, null);
        Model model = new ModelManager();
        model.setPageStatus(pageStatus);
        Model expectedModel = new ModelManager();
        expectedModel.setPageStatus(pageStatus.withNewPageType(PageType.OVERALL_VIEW));

        assertCommandSuccess(new CancelEditDayCommand(), model, CancelEditDayCommand.MESSAGE_CANCEL_CREATE_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_emptyTravelPalEdit_success() {
        EditDayFieldCommand.EditDayDescriptor editDayDescriptor = new EditDayFieldCommand.EditDayDescriptor();
        PageStatus pageStatus = new PageStatus(PageType.ADD_DAY, null, null, null, null,
                null, null, null, editDayDescriptor ,null,
                null, null, null, null);
        Model model = new ModelManager();
        model.setPageStatus(pageStatus);

        Model expectedModel = new ModelManager();
        expectedModel.setPageStatus(pageStatus.withNewPageType(PageType.OVERALL_VIEW));
        EditDayFieldCommand.EditDayDescriptor editDayDescriptorAfter = new EditDayFieldCommand.EditDayDescriptor(editDayDescriptor);
        editDayDescriptorAfter.setBudget(new Budget(123));

        assertCommandSuccess(new CancelEditDayCommand(), model, CancelEditDayCommand.MESSAGE_CANCEL_EDIT_SUCCESS, expectedModel, true);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setTravelPal(new TravelPal());

        assertCommandSuccess(new CancelEditDayCommand(), model, CancelEditDayCommand.MESSAGE_CANCEL_CREATE_SUCCESS, expectedModel);
    }

}