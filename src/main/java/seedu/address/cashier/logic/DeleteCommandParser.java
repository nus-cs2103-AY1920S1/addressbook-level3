package seedu.address.cashier.logic;

import seedu.address.cashier.ui.CashierUi;
import seedu.address.cashier.commands.DeleteCommand;
import seedu.address.cashier.logic.exception.NotANumberException;

public class DeleteCommandParser {

    public static DeleteCommand parse(String userInput) throws NotANumberException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));

        } catch (Exception e) {
            throw new NotANumberException(CashierUi.NOT_A_NUMBER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}
