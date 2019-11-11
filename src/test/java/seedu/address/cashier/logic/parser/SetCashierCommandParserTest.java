package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_STORYBOOK;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.SetCashierCommand;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class SetCashierCommandParserTest {

    private SetCashierCommandParser parser = new SetCashierCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_validCashier_success() {
        Person p = personModel.getPersonByName("Alice Pauline");
        SetCashierCommand setCashierCommand = new SetCashierCommand(p);
        assertCommandParserSuccess(parser, " Alice Pauline", setCashierCommand, model,
                (CheckAndGetPersonByNameModel) personModel);
        model.resetCashier();
    }

    @Test
    public void parse_nonExistingCashier_failure() {
        assertCommandParserFailure(parser, " Bob", NO_SUCH_PERSON, model,
                (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_validCashierWithOtherPrefix_success() {
        assertCommandParserFailure(parser, " Alice Pauline" + DESC_DESCRIPTION_STORYBOOK,
                NO_SUCH_PERSON, model, (CheckAndGetPersonByNameModel) personModel);
        model.resetCashier();
    }

}


