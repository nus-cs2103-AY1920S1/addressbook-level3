package seedu.address.transaction.logic;

import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.logic.exception.NotANumberException;
import seedu.address.transaction.ui.TransactionMessages;

public class DeleteCommandParser {

    public static DeleteCommand parse(String userInput) throws NotANumberException {
        int index;
        try {
            index = Integer.parseInt(userInput.substring(1));

        } catch (Exception e) {
            throw new NotANumberException(TransactionMessages.NOT_A_NUMBER);
        }

        DeleteCommand deleteCommand = new DeleteCommand(index);
        return deleteCommand;
    }
}
