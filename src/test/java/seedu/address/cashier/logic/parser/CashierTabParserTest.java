package seedu.address.cashier.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_INDEX_1;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.CheckoutCommand;
import seedu.address.cashier.logic.commands.ClearCommand;
import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.logic.commands.SetCashierCommand;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CashierTabParserTest {

    private final CashierTabParser parser = new CashierTabParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + VALID_INDEX_1, model,
                (CheckAndGetPersonByNameModel) personModel);
        assertEquals(new DeleteCommand(1), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        ClearCommand clearCommand1 = (ClearCommand) parser.parseCommand(ClearCommand.COMMAND_WORD, model,
                (CheckAndGetPersonByNameModel) personModel);
        ClearCommand clearCommand2 = (ClearCommand) parser.parseCommand(ClearCommand.COMMAND_WORD + " 9",
                model, (CheckAndGetPersonByNameModel) personModel);
        assertEquals(new ClearCommand(), clearCommand1);
        assertTrue(clearCommand2 instanceof ClearCommand);
    }

    @Test
    public void parseCommand_setCashier() throws Exception {
        SetCashierCommand command = (SetCashierCommand) parser.parseCommand(
                SetCashierCommand.COMMAND_WORD + " Alice Pauline", model,
                (CheckAndGetPersonByNameModel) personModel);
        assertEquals(new SetCashierCommand(personModel.getPersonByName("Alice Pauline")), command);
    }

    @Test
    public void parseCommand_checkout() throws Exception {
        model.setCashier(personModel.getPersonByName("Alice Pauline"));
        CheckoutCommand command = (CheckoutCommand) parser.parseCommand(
                CheckoutCommand.COMMAND_WORD + " 9999", model, (CheckAndGetPersonByNameModel) personModel);
        assertTrue(command instanceof CheckoutCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(Exception.class, CashierMessages.MESSAGE_NO_COMMAND, () ->
                parser.parseCommand("", model, (CheckAndGetPersonByNameModel) personModel));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(Exception.class, CashierMessages.MESSAGE_NO_COMMAND, () ->
                parser.parseCommand("unknownCommand", model, (CheckAndGetPersonByNameModel) personModel));
    }

}
