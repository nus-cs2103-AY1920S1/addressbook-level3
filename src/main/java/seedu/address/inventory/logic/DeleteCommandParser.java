package seedu.address.inventory.logic;

import seedu.address.inventory.commands.DeleteCommand;
import seedu.address.inventory.logic.exception.NotANumberException;
import seedu.address.transaction.ui.TransactionUi;

public class DeleteCommandParser {

    public static DeleteCommand parse(String userInput) throws NotANumberException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));

        } catch (Exception e) {
            throw new NotANumberException(TransactionUi.NOT_A_NUMBER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}
