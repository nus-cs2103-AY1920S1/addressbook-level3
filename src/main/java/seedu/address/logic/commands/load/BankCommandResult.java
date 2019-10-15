package seedu.address.logic.commands.load;

import seedu.address.logic.commands.CommandResult;

public class BankCommandResult extends CommandResult {

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Displaying available wordbanks\n Choose one";

    private String selectedWordBankName;

    public BankCommandResult(String selectedWordBankName) {
        super(MESSAGE_LIST_ACKNOWLEDGEMENT , false, false);
        this.selectedWordBankName = selectedWordBankName;
    }

    public String getSelectedWordBankName() {
        return selectedWordBankName;
    }
}
