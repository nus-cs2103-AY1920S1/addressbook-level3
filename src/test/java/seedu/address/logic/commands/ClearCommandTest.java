package seedu.address.logic.commands;


public class ClearCommandTest {

    @Test
    public void execute_emptyExpenseList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExpenseList_success() {
        Model model = new ModelManager(getTypicalExpenseList(), getTypicalExchangeData(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExpenseList(), getTypicalExchangeData(), new UserPrefs());
        expectedModel.setExpenseList(new ExpenseList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
