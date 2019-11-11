package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_BUILDER_QUANTITY;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_CATEGORY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_CHIPS;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_DESCRIPTION_STORYBOOK;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_QUANTITY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.DESC_QUANTITY_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_2;
import static seedu.address.cashier.logic.commands.CommandTestUtil.INVALID_QUANTITY_3;
import static seedu.address.cashier.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_CATEGORY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_QUANTITY_1;
import static seedu.address.cashier.logic.commands.CommandTestUtil.VALID_QUANTITY_2;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserFailure;
import static seedu.address.cashier.logic.parser.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_TOTAL_AMOUNT_EXCEEDED;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_POSITIVE;
import static seedu.address.cashier.ui.CashierMessages.itemsByCategory;
import static seedu.address.cashier.ui.CashierMessages.noSuchItemRecommendation;
import static seedu.address.testutil.TypicalItem.CHIPS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public void setInventoryList() {
        InventoryList inventoryList = new InventoryList();
        CHIPS.setQuantity(99999999);
        inventoryList.add(CHIPS);
        model = new seedu.address.cashier.model.ModelManager(new seedu.address.cashier.util.InventoryList(
                inventoryList.getInventoryListInArrayList()),
                TypicalTransactions.getTypicalTransactionList());
    }

    @Test
    public void parse_allFieldsPresent_success() throws NoSuchIndexException {
        setInventoryList();
        model.clearSalesList();
        // optional category included
        assertCommandParserSuccess(parser, DESC_CATEGORY_1 + DESC_DESCRIPTION_CHIPS + DESC_QUANTITY_1
                        + DESC_QUANTITY_2,
                new AddCommand(CHIPS.getDescription(), VALID_QUANTITY_2), model,
                (CheckAndGetPersonByNameModel) personModel);
        model.clearSalesList();
    }

    @Test
    public void parse_itemInvalidAmount_failure() {
        // with total amount exceeded
        setInventoryList();
        assertCommandParserFailure(parser, DESC_DESCRIPTION_CHIPS
                        + INVALID_QUANTITY_3,
                MESSAGE_TOTAL_AMOUNT_EXCEEDED, model,
                (CheckAndGetPersonByNameModel) personModel);

        CHIPS.setQuantity(85); //reset back quantity
    }

    @Test
    public void parse_itemNotInInventory_failure() throws NoSuchIndexException {
        // with item not found in inventory
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1
                        + DESC_BUILDER_QUANTITY,
                noSuchItemRecommendation(model.getRecommendedItems(INVALID_DESCRIPTION_1)), model,
                (CheckAndGetPersonByNameModel) personModel);
    }


    @Test
    public void parse_itemNotForSale_failure() {
        // with description of item that is not available for sale
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_2 + DESC_BUILDER_QUANTITY,
                NO_SUCH_ITEM_FOR_SALE_CASHIER, model, (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws NoSuchIndexException {

        String expectedMessage = CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT;

        // missing description prefix
        assertCommandParserFailure(parser, VALID_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_1,
                expectedMessage, model, (CheckAndGetPersonByNameModel) personModel);

        // missing quantity prefix
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + VALID_QUANTITY_1,
                expectedMessage, model, (CheckAndGetPersonByNameModel) personModel);

        // all prefixes missing
        assertCommandParserFailure(parser, VALID_DESCRIPTION_FISH_BURGER + VALID_QUANTITY_1,
                expectedMessage, model, (CheckAndGetPersonByNameModel) personModel);

        //with optional category field, but missing description
        ArrayList<String> listItems = model.getDescriptionByCategory(VALID_CATEGORY_1);
        assertCommandParserFailure(parser, DESC_CATEGORY_1,
                itemsByCategory(listItems), model, (CheckAndGetPersonByNameModel) personModel);

    }

    @Test
    public void parse_invalidValue_failure() throws NoSuchIndexException {

        // invalid description
        ArrayList<String> recommendedItems = model.getRecommendedItems(INVALID_DESCRIPTION_1);
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + DESC_QUANTITY_1,
                noSuchItemRecommendation(recommendedItems), model, (CheckAndGetPersonByNameModel) personModel);

        // invalid non-integer quantity
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + INVALID_QUANTITY_1,
                QUANTITY_NOT_A_NUMBER, model, (CheckAndGetPersonByNameModel) personModel);

        // invalid negative quantity
        assertCommandParserFailure(parser, DESC_DESCRIPTION_FISH_BURGER + INVALID_QUANTITY_2,
                QUANTITY_NOT_POSITIVE, model, (CheckAndGetPersonByNameModel) personModel);

        // two invalid values, only first invalid value reported
        assertCommandParserFailure(parser, INVALID_DESCRIPTION_1 + INVALID_QUANTITY_1,
                QUANTITY_NOT_A_NUMBER, model, (CheckAndGetPersonByNameModel) personModel);

        // non-empty preamble
        assertCommandParserFailure(parser, PREAMBLE_NON_EMPTY + DESC_DESCRIPTION_STORYBOOK
                        + DESC_QUANTITY_1, CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT, model,
                (CheckAndGetPersonByNameModel) personModel);

        setInventoryList();

        CHIPS.setQuantity(85); //reset back quantity
        // insufficient qty to add
        String message1 = String.format(MESSAGE_INSUFFICIENT_STOCK, CHIPS.getQuantity(), CHIPS.getDescription());
        assertCommandParserFailure(parser, DESC_DESCRIPTION_CHIPS + INVALID_QUANTITY_3,
                message1, model, (CheckAndGetPersonByNameModel) personModel);

        CHIPS.setQuantity(85); //reset back quantity

    }

}
