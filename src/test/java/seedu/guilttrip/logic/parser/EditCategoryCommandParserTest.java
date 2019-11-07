package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_FOR_EDIT_NEW;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_NAME_FOR_EDIT_OLD;
import static seedu.guilttrip.logic.commands.CommandTestUtil.CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.INVALID_CATEGORY_TYPE_BUDGET;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_TYPE_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_NEW_CATEGORY_NAME;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_OLD_CATEGORY_NAME;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.logic.parser.editcommandparsers.EditCategoryCommandParser;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.testutil.EditCategoryDescriptorBuilder;

public class EditCategoryCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCategoryCommand.MESSAGE_USAGE);

    private EditCategoryCommandParser parser = new EditCategoryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no category type specified
        assertParseFailure(parser, CATEGORY_NAME_FOR_EDIT_OLD
                + CATEGORY_NAME_FOR_EDIT_NEW , MESSAGE_INVALID_FORMAT);

        // no old category name specified
        assertParseFailure(parser, CATEGORY_TYPE_EXPENSE
                + CATEGORY_NAME_FOR_EDIT_NEW , MESSAGE_INVALID_FORMAT);

        // no new category name specified
        assertParseFailure(parser, CATEGORY_TYPE_EXPENSE
                + CATEGORY_NAME_FOR_EDIT_OLD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid Category type
        assertParseFailure(parser, INVALID_CATEGORY_TYPE_BUDGET + CATEGORY_NAME_FOR_EDIT_OLD
                + CATEGORY_NAME_FOR_EDIT_NEW, Category.MESSAGE_CONSTRAINTS_TYPE); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = CATEGORY_TYPE_EXPENSE + CATEGORY_NAME_FOR_EDIT_OLD + CATEGORY_NAME_FOR_EDIT_NEW;
        Category oldCategoryToEdit = new Category(VALID_OLD_CATEGORY_NAME, VALID_CATEGORY_TYPE_EXPENSE);
        EditCategoryCommand.EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategoryName(VALID_NEW_CATEGORY_NAME).withCategoryType(VALID_CATEGORY_TYPE_EXPENSE).build();
        EditCategoryCommand expectedCommand = new EditCategoryCommand(oldCategoryToEdit, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
