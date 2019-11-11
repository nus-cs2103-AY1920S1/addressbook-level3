package seedu.ifridge.logic.parser.templatelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.*;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.*;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand;
import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand.EditTemplateItemDescriptor;
import seedu.ifridge.logic.parser.templatelist.template.EditTemplateItemCommandParser;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.testutil.EditTemplateItemDescriptorBuilder;

public class EditTemplateItemCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTemplateItemCommand.MESSAGE_USAGE);

    private EditTemplateItemCommandParser parser = new EditTemplateItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no template index specified
        assertParseFailure(parser, INDEX_DESC + NAME_DESC_CHEESE, MESSAGE_INVALID_FORMAT);

        //no index index specified
        assertParseFailure(parser, "1" + NAME_DESC_CHEESE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1" + INDEX_DESC, EditTemplateItemCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + INDEX_DESC + NAME_DESC_CHEESE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + INDEX_DESC + NAME_DESC_CHEESE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 t/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INDEX_DESC + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INDEX_DESC + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount

        // multiple invalid values, but only the first invalid value is captured.
        // But bcuz of conflicts, we do only one INVALID ONE IN THIS CASE.
        assertParseFailure(parser, "1" + INDEX_DESC + INVALID_NAME_DESC + INVALID_AMOUNT_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetTemplateIndex = INDEX_FIRST;
        Index targetItemIndex = INDEX_FIRST;
        String userInput = targetTemplateIndex.getOneBased() + targetItemIndex.getOneBased() + AMOUNT_DESC_CHEESE
                + NAME_DESC_CHEESE;

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_CHEESE)
                .withAmount(VALID_AMOUNT_CHEESE).build();
        EditTemplateItemCommand expectedCommand = new EditTemplateItemCommand(targetTemplateIndex,
                targetItemIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetTemplateIndex = INDEX_FIRST;
        Index targetItemIndex = INDEX_FIRST;
        String userInput = targetTemplateIndex.getOneBased() + INDEX_DESC + AMOUNT_DESC_MILK;

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder()
                .withAmount(VALID_AMOUNT_MILK).build();
        EditTemplateItemCommand expectedCommand = new EditTemplateItemCommand(targetTemplateIndex,
                targetItemIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_FIRST;
        Index itemIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INDEX_DESC + NAME_DESC_MILK;
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder()
                .withName(VALID_NAME_MILK).withAmount("1L").build();
        EditTemplateItemCommand expectedCommand = new EditTemplateItemCommand(targetIndex, itemIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + INDEX_DESC + AMOUNT_DESC_MILK;
        descriptor = new EditTemplateItemDescriptorBuilder().withName("Red Wine").withAmount(VALID_AMOUNT_MILK).build();
        expectedCommand = new EditTemplateItemCommand(targetIndex, itemIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        Index itemIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INDEX_DESC + AMOUNT_DESC_CHEESE + AMOUNT_DESC_MILK;

        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder()
                .withName("Red Wine").withAmount(VALID_AMOUNT_MILK).build();
        EditTemplateItemCommand expectedCommand = new EditTemplateItemCommand(targetIndex, itemIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        Index itemIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INDEX_DESC + INVALID_NAME_DESC + NAME_DESC_MILK;
        EditTemplateItemDescriptor descriptor = new EditTemplateItemDescriptorBuilder()
                .withName(VALID_NAME_MILK).withAmount("1L").build();
        EditTemplateItemCommand expectedCommand = new EditTemplateItemCommand(targetIndex, itemIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INDEX_DESC + INVALID_NAME_DESC + NAME_DESC_MILK;
        descriptor = new EditTemplateItemDescriptorBuilder().withName(VALID_NAME_MILK).withAmount("1L").build();
        expectedCommand = new EditTemplateItemCommand(targetIndex, itemIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
