package seedu.ifridge.logic.parser.templatelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC_APPLE_PIE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.VALID_TEMPLATE_NAME_APPLE_PIE;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.templatelist.AddTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.ClearTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.DeleteTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.ListTemplateListCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.testutil.EditTemplateListDescriptorBuilder;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;

public class TemplateListParserTest {

    private final TemplateListParser parser = new TemplateListParser();

    @Test
    public void parseCommand_add() throws Exception {
        String userInput = AddTemplateListCommand.COMMAND_WORD + TEMPLATE_NAME_DESC_APPLE_PIE;
        UniqueTemplateItems template = new UniqueTemplateItemsBuilder(new Name(VALID_TEMPLATE_NAME_APPLE_PIE)).build();
        AddTemplateListCommand command = (AddTemplateListCommand) parser.parseCommand(userInput);
        assertEquals(new AddTemplateListCommand(template), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearTemplateListCommand.COMMAND_WORD) instanceof ClearTemplateListCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        String userInput = DeleteTemplateListCommand.COMMAND_WORD
                + " " + INDEX_FIRST.getOneBased();
        DeleteTemplateListCommand command = (DeleteTemplateListCommand) parser.parseCommand(userInput);
        assertEquals(new DeleteTemplateListCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        UniqueTemplateItems template = new UniqueTemplateItemsBuilder().build();
        EditTemplateListCommand.EditTemplateListDescriptor descriptor =
                new EditTemplateListDescriptorBuilder().withName(VALID_TEMPLATE_NAME_APPLE_PIE).build();
        EditTemplateListCommand command = (EditTemplateListCommand) parser.parseCommand(
                EditTemplateListCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + " " + TEMPLATE_NAME_DESC_APPLE_PIE);
        assertEquals(new EditTemplateListCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListTemplateListCommand.COMMAND_WORD) instanceof ListTemplateListCommand);
        assertTrue(parser.parseCommand(ListTemplateListCommand.COMMAND_WORD + " 3")
                instanceof ListTemplateListCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_templateItemCommand() throws Exception {


    }
}
