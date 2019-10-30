package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_STORYBOOK;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_INDEX_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_QUANTITY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_QUANTITY_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_INDEX_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_INDEX_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_INDEX_3;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_INDEX_4;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_3;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.ui.CashierMessages.INDEX_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INVALID_EDITCOMMAND_FORMAT;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_TO_EDIT_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_POSITIVE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class EditCommandParserTest {

    private EditCommandParser parser = new EditCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_missingParts_failure() {
        model.addItem(TypicalItem.FISH_BURGER);

        // no index specified
        assertCommandParserFailure(parser, DESC_DESCRIPTION_STORYBOOK, MESSAGE_INVALID_EDITCOMMAND_FORMAT, model,
                personModel);

        //only quantity specified
        assertCommandParserFailure(parser, DESC_QUANTITY_1, MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);

        // no index and no field specified
        assertCommandParserFailure(parser, "", MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);
        model.clearSalesList();
    }

    @Test
    public void parse_invalidPreamble_failure() {
        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        // invalid arguments being parsed as preamble
        assertCommandParserFailure(parser, DESC_INDEX_1 + " some random string",
                MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);
        assertCommandParserFailure(parser, DESC_QUANTITY_1 + " some random string",
                MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);

        // invalid prefix being parsed as preamble
        assertCommandParserFailure(parser, DESC_INDEX_1 + " c/ string",
                MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);
        assertCommandParserFailure(parser, DESC_QUANTITY_1 + " c/ string",
                MESSAGE_INVALID_EDITCOMMAND_FORMAT, model, personModel);
        model.clearSalesList();
    }

    @Test
    public void parse_invalidValue_failure() {
        model.addItem(TypicalItem.FISH_BURGER);

        // negative index
        assertCommandParserFailure(parser, INVALID_INDEX_1 + DESC_QUANTITY_2,
                NO_SUCH_INDEX_CASHIER, model, personModel);

        // zero index
        assertCommandParserFailure(parser, INVALID_INDEX_4 + DESC_QUANTITY_2,
                NO_SUCH_INDEX_CASHIER, model, personModel);

        // invalid string index
        assertCommandParserFailure(parser, INVALID_INDEX_3 + DESC_QUANTITY_1,
                INDEX_NOT_A_NUMBER, model, personModel);

        // index out of bounds
        assertCommandParserFailure(parser, INVALID_INDEX_2 + DESC_QUANTITY_1,
                NO_SUCH_INDEX_CASHIER, model, personModel);

        // invalid string quantity
        assertCommandParserFailure(parser, DESC_INDEX_1 + INVALID_QUANTITY_1, QUANTITY_NOT_A_NUMBER, model,
                personModel);

        // invalid negative quantity
        assertCommandParserFailure(parser, DESC_INDEX_1 + INVALID_QUANTITY_2, QUANTITY_NOT_POSITIVE, model,
                personModel);

        // invalid description of non-existing item
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + DESC_QUANTITY_1,
                NO_SUCH_ITEM_TO_EDIT_CASHIER, model, personModel);

        // invalid description of item not available for sale
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_2 + DESC_QUANTITY_1,
                NO_SUCH_ITEM_TO_EDIT_CASHIER, model, personModel);

        //invalid quantity and description
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + INVALID_QUANTITY_1,
                NO_SUCH_ITEM_TO_EDIT_CASHIER, model, personModel);

        //invalid quantity and index
        assertCommandParserFailure(parser, INVALID_INDEX_1 + INVALID_QUANTITY_1,
                NO_SUCH_INDEX_CASHIER, model, personModel);

        // invalid negative quantity
        String message1 = String.format(MESSAGE_INSUFFICIENT_STOCK, TypicalItem.FISH_BURGER.getQuantity(),
                TypicalItem.FISH_BURGER.getDescription());
        assertCommandParserFailure(parser, DESC_INDEX_1 + INVALID_QUANTITY_3, message1,
                model, personModel);

        model.clearSalesList();
    }

    /*@Test
    public void parse_indexFieldsSpecified_success() {
        model.clearSalesList();
        model.addItem(TypicalItem.STORYBOOK);
        String userInput = DESC_INDEX_1 + DESC_QUANTITY_2;

        EditCommand expectedCommand = new EditCommand(VALID_INDEX_1, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);
        model.clearSalesList();
    }*/

    /*@Test
    public void parse_indexFieldsSpecified_success() {
        model.clearSalesList();
        model.addItem(TypicalItem.STORYBOOK);
        String userInput = DESC_INDEX_1 + DESC_QUANTITY_2;

        EditCommand expectedCommand = new EditCommand(VALID_INDEX_1, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);
        model.clearSalesList();
    }*/

    /*@Test
    public void parse_descriptionFieldsSpecified_success() {
        model.addItem(TypicalItem.FISH_BURGER);

        String userInput = DESC_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_2;

        EditCommand expectedCommand = new EditCommand(VALID_INDEX_1, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);
        model.clearSalesList();
    }*/

    @Test
    public void parse_oneFieldSpecified_failure() {
        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        // only quantity
        assertCommandParserFailure(parser, DESC_QUANTITY_1, MESSAGE_INVALID_EDITCOMMAND_FORMAT,
                model, personModel);

        // only index
        assertCommandParserFailure(parser, DESC_INDEX_1, MESSAGE_INVALID_EDITCOMMAND_FORMAT,
                model, personModel);

        // only description
        assertCommandParserFailure(parser, DESC_DESCRIPTION_STORYBOOK, MESSAGE_INVALID_EDITCOMMAND_FORMAT,
                model, personModel);
        model.clearSalesList();
    }

    /*@Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        // repeat quantity
        String userInput = DESC_INDEX_2 + DESC_QUANTITY_1 + DESC_QUANTITY_2;
        EditCommand expectedCommand = new EditCommand(VALID_INDEX_2, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);

        // repeat index
        userInput = DESC_INDEX_1 + DESC_INDEX_2 + DESC_QUANTITY_2;
        expectedCommand = new EditCommand(VALID_INDEX_2, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);

        // repeat description
        userInput = DESC_DESCRIPTION_FISH_BURGER + DESC_DESCRIPTION_STORYBOOK + DESC_QUANTITY_2;
        expectedCommand = new EditCommand(VALID_INDEX_2, VALID_QUANTITY_2);
        assertCommandParserSuccess(parser, userInput, expectedCommand, model, personModel);
        model.clearSalesList();
    }*/

}


