package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.ClearCommand;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class ClearCommandParserTest {
    private ClearCommandParser parser = new ClearCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_noArgs_returnsClearCommand() {
        ClearCommand clearCommand = new ClearCommand();
        assertCommandParserSuccess(parser, "", clearCommand, model, (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_redundantArgs_returnsClearCommand() {
        ClearCommand clearCommand = new ClearCommand();
        assertCommandParserSuccess(parser, "34", clearCommand, model, (CheckAndGetPersonByNameModel) personModel);
    }
}

