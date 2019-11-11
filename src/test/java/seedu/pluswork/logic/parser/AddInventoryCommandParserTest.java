package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_MEMBERID_DESC_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_NAME_DESC_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_PRICE_DESC_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.INVENTORY_TASKID_DESC_SPORTS;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_SPORTS;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pluswork.testutil.TypicalInventories.SPORTS;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.inventory.AddInventoryCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.inventory.AddInventoryCommandParser;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.testutil.InventoryBuilder;

public class AddInventoryCommandParserTest {
    private AddInventoryCommandParser parser = new AddInventoryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws CommandException {
        Inventory expectedInv = new InventoryBuilder(SPORTS).build();

        // whitespace only preamble
        assertParseSuccess(parser, INVENTORY_NAME_DESC_SPORTS + INVENTORY_PRICE_DESC_SPORTS
                        + INVENTORY_TASKID_DESC_SPORTS + INVENTORY_MEMBERID_DESC_SPORTS,
                new AddInventoryCommand(new Index(0), expectedInv.getName(),
                        expectedInv.getPrice(), new MemberId("AR")));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws CommandException {
        Inventory expectedInv = new InventoryBuilder(SPORTS).build();

        // whitespace only preamble
        assertParseSuccess(parser, INVENTORY_NAME_DESC_SPORTS + INVENTORY_TASKID_DESC_SPORTS
                        + INVENTORY_MEMBERID_DESC_SPORTS,
                new AddInventoryCommand(new Index(0), expectedInv.getName(), new MemberId("AR")));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws CommandException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInventoryCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_INVENTORY_NAME_SPORTS, expectedMessage);
    }
}
