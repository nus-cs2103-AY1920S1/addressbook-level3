package seedu.address.cashier.logic;

import static seedu.address.cashier.commands.CommandTestUtil.DESC_BUILDER_QUANTITY;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.logic.CommandParserTestUtil.assertCommandParserSuccess;
import static seedu.address.testutil.ItemBuilder.DEFAULT_QUANTITY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.logic.parser.AddCommandParser;
import seedu.address.cashier.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();
    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void parse_allFieldsPresent_success() {

        model.addItem(TypicalItem.FISH_BURGER);
        // whitespace only preamble
        System.out.println("desc: " + VALID_DESCRIPTION_FISH_BURGER);
        System.out.println(model.hasItemInInventory(VALID_DESCRIPTION_FISH_BURGER));
        System.out.println("input: " + PREAMBLE_WHITESPACE + DESC_DESCRIPTION_FISH_BURGER
                + DESC_BUILDER_QUANTITY);
        assertCommandParserSuccess(parser, PREAMBLE_WHITESPACE + DESC_DESCRIPTION_FISH_BURGER
                        + DESC_BUILDER_QUANTITY,
                new AddCommand(VALID_DESCRIPTION_FISH_BURGER, DEFAULT_QUANTITY), model, personModel);

 /*       //no whitespace preamble
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_BENSEN + DESC_BUILDER_DATE + DESC_BUILDER_DESC
                + DESC_BUILDER_CATEGORY + DESC_BUILDER_AMOUNT, new AddCommand(expectedTransaction2), personModel);

        // multiple phones - last description accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_DESC + DESC_BUILDER_DESC
                        + DESC_BUILDER_AMOUNT + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction), personModel);

        // multiple emails - last category accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_CATEGORY + DESC_BUILDER_CATEGORY
                        + DESC_BUILDER_DESC + DESC_BUILDER_DATE + DESC_BUILDER_AMOUNT,
                new AddCommand(expectedTransaction), personModel);

        // multiple addresses - last amount accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_AMOUNT + DESC_BUILDER_AMOUNT
                        + DESC_BUILDER_DESC + DESC_BUILDER_CATEGORY + DESC_BUILDER_DATE,
                new AddCommand(expectedTransaction), personModel);

        // multiple addresses - last date accepted
        assertCommandParseWithPersonModelSuccess(parser, DESC_NAME_ALICE + DESC_BUILDER_AMOUNT + DESC_BUILDER_DESC
                        + DESC_BUILDER_CATEGORY + DESC_DATE + DESC_BUILDER_DATE , new AddCommand(expectedTransaction),
                personModel);  */
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

    }
}
