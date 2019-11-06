package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_validArgsWithinBounds_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(1);
        assertCommandParserSuccess(parser, " 1", deleteCommand, model,
                (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_invalidArgs_notANumberPrefix() {
        assertCommandParserFailure(parser, " abc", CashierMessages.INDEX_NOT_A_NUMBER, model,
                (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_invalidArgs_negativePrefix() {
        assertCommandParserFailure(parser, " -5", NO_SUCH_INDEX_CASHIER, model,
                (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_validArgsWithOtherPrefix_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(1);
        assertCommandParserSuccess(parser, " 1" + DESC_DESCRIPTION_FISH_BURGER,
                deleteCommand, model, (CheckAndGetPersonByNameModel) personModel);
    }
}
