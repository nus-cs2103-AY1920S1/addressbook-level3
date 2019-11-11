package seedu.ifridge.logic.parser.templatelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_NAME_DESC;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.ClearCommand;
import seedu.ifridge.logic.commands.DeleteCommand;
import seedu.ifridge.logic.commands.ExitCommand;
import seedu.ifridge.logic.commands.FindCommand;
import seedu.ifridge.logic.commands.HelpCommand;
import seedu.ifridge.logic.commands.ListCommand;
import seedu.ifridge.logic.commands.templatelist.AddTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.ListTemplateListCommand;
import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand;
import seedu.ifridge.logic.parser.GroceryListParser;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.food.NameContainsKeywordsPredicate;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.testutil.EditTemplateListDescriptorBuilder;
import seedu.ifridge.testutil.TemplateItemBuilder;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;

public class TemplateListParserTest {

    private final TemplateListParser parser = new TemplateListParser();

    @Test
    public void parseCommand_add() throws Exception {
        String userInput = AddTemplateListCommand.COMMAND_WORD + TEMPLATE_NAME_DESC;
        UniqueTemplateItems template = new UniqueTemplateItemsBuilder().build();
        AddTemplateListCommand command = (AddTemplateListCommand) parser.parseCommand(userInput);
        assertEquals(new AddTemplateListCommand(template), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        UniqueTemplateItems template = new UniqueTemplateItemsBuilder().build();
        EditTemplateListCommand.EditTemplateListDescriptor descriptor =
                new EditTemplateListDescriptorBuilder(template).build();
        EditTemplateListCommand command = (EditTemplateListCommand) parser.parseCommand(
                EditTemplateListCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + " " + getEditFoodDescriptorDetails(descriptor));
        assertEquals(new EditTemplatEListCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListTemplateListCommand.COMMAND_WORD) instanceof ListTemplateListCommand);
        assertTrue(parser.parseCommand(ListTemplateListCommand.COMMAND_WORD + " 3")
                instanceof ListTemplateListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_templateItemCommand() throws Exception {


    }
}
