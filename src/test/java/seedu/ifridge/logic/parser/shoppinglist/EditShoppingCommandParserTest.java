package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.AMOUNT_DESC_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.AMOUNT_DESC_ORANGES;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.NAME_DESC_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.shoppinglist.ShoppingCommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_FOOD;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_SECOND_FOOD;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_THIRD_FOOD;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.shoppinglist.EditShoppingCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.testutil.EditShoppingItemDescriptorBuilder;

public class EditShoppingCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditShoppingCommand.MESSAGE_USAGE);

    private EditShoppingCommandParser parser = new EditShoppingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_NUTS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditShoppingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_NUTS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_NUTS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name

        // multiple invalid values, but only the first invalid value is captured.
        // But bcuz of conflicts, we do only one INVALID ONE IN THIS CASE.
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FOOD;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_NUTS;

        EditShoppingCommand.EditShoppingItemDescriptor descriptor = new EditShoppingItemDescriptorBuilder()
                .withName(VALID_NAME_NUTS).withAmount(VALID_AMOUNT_NUTS).build();
        EditShoppingCommand expectedCommand = new EditShoppingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = "" + targetIndex.getOneBased() + AMOUNT_DESC_NUTS;

        EditShoppingCommand.EditShoppingItemDescriptor descriptor = new EditShoppingItemDescriptorBuilder()
                .withAmount(VALID_AMOUNT_NUTS).build();
        EditShoppingCommand expectedCommand = new EditShoppingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_FOOD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_NUTS;
        System.out.println(userInput);
        EditShoppingCommand.EditShoppingItemDescriptor descriptor = new EditShoppingItemDescriptorBuilder()
                .withName(VALID_NAME_NUTS).build();
        EditShoppingCommand expectedCommand = new EditShoppingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_NUTS;
        descriptor = new EditShoppingItemDescriptorBuilder().withAmount(VALID_AMOUNT_NUTS).build();
        expectedCommand = new EditShoppingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = targetIndex.getOneBased()
                + AMOUNT_DESC_NUTS + AMOUNT_DESC_ORANGES + AMOUNT_DESC_NUTS;

        EditShoppingCommand.EditShoppingItemDescriptor descriptor = new EditShoppingItemDescriptorBuilder()
                .withAmount(VALID_AMOUNT_NUTS).build();
        EditShoppingCommand expectedCommand = new EditShoppingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FOOD;
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_NUTS;
        EditShoppingCommand.EditShoppingItemDescriptor descriptor = new EditShoppingItemDescriptorBuilder()
                .withName(VALID_NAME_NUTS).build();
        EditShoppingCommand expectedCommand = new EditShoppingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC
                + NAME_DESC_NUTS;
        descriptor = new EditShoppingItemDescriptorBuilder().withName(VALID_NAME_NUTS)
                .build();
        expectedCommand = new EditShoppingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
