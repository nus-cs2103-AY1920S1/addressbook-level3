package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.INVALID_NAME_DESC;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.INVALID_TAG_DESC;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.NAME_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.TAG_DESC_BANANA;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.TAG_DESC_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_EXPIRY_DATE_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.grocerylist.EditGroceryCommand;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.tag.Tag;

public class EditGroceryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroceryCommand.MESSAGE_USAGE);

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private EditGroceryCommandParser parser = new EditGroceryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_NUTS + VALID_AMOUNT_NUTS + VALID_EXPIRY_DATE_NUTS,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditGroceryCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_NUTS + TAG_DESC_BANANA + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_NUTS + TAG_EMPTY + TAG_DESC_BANANA, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_NUTS + TAG_DESC_BANANA, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured.
        // But bcuz of conflicts, we do only one INVALID ONE IN THIS CASE.
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }
}
