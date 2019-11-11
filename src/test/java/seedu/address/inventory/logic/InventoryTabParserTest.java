package seedu.address.inventory.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.inventory.logic.commands.AddCommand;
import seedu.address.inventory.logic.commands.Command;
import seedu.address.inventory.logic.commands.DeleteCommand;
import seedu.address.inventory.logic.commands.EditCommand;
import seedu.address.inventory.logic.commands.SortCategoryCommand;
import seedu.address.inventory.logic.commands.SortDescriptionCommand;
import seedu.address.inventory.logic.commands.SortQuantityCommand;
import seedu.address.inventory.logic.commands.exception.NoSuchSortException;
import seedu.address.inventory.logic.commands.exception.NotANumberException;
import seedu.address.inventory.logic.parser.InventoryTabParser;
import seedu.address.inventory.logic.parser.exception.InvalidNumberException;
import seedu.address.inventory.logic.parser.exception.OnCashierModeException;
import seedu.address.inventory.logic.parser.exception.ParseException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;

public class InventoryTabParserTest {
    private static final InventoryTabParser parser = new InventoryTabParser();

    @Test
    public void parser_invalidCommand_exceptionThrown() {
        assertThrows(Exception.class, () -> parser.parseCommand("command", new InventoryList()));
    }

    @Test
    public void parser_addCommand_missingPrefixesUnsuccessful() {
        Command command = null;
        String input = "add d/item";

        try {
            command = parser.parseCommand(input, new InventoryList());
        } catch (Exception e) {
            assertEquals(InventoryMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT, e.toString());
        }
    }

    @Test
    public void parser_addCommand_notANumberUnsuccessful() {
        Command command = null;
        String input = "add d/item c/test q/number co/number p/number";

        try {
            command = parser.parseCommand(input, new InventoryList());
        } catch (Exception e) {
            assertEquals(InventoryMessages.MESSAGE_NOT_A_NUMBER, e.getMessage());
        }
    }

    @Test
    public void parser_addCommand_successful() {
        Command command1 = null;
        Command command2 = null;
        String input1 = "add d/item1 c/test q/4 co/6";
        String input2 = "add d/item2 c/test q/3 co/4 p/7";

        try {
            command1 = parser.parseCommand(input1, new InventoryList());
            command2 = parser.parseCommand(input2, new InventoryList());
        } catch (Exception e) {
            fail();
        }

        assertTrue(command1 instanceof AddCommand);
        assertTrue(command2 instanceof AddCommand);
    }

    @Test
    public void parser_editCommand_successful() {
        Command command = null;

        try {
            command = parser.parseCommand("edit 1 d/new c/test q/6 co/9 p/8", new InventoryList());
        } catch (Exception e) {
            fail();
        }

        assertTrue(command instanceof EditCommand);
    }

    @Test
    public void parser_deleteCommand_successful() {
        Command command = null;

        try {
            command = parser.parseCommand("delete 1", new InventoryList());
        } catch (Exception e) {
            fail();
        }

        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parser_deleteCommand_unsuccessful() {
        Command command = null;

        try {
            command = parser.parseCommand("delete number", new InventoryList());
        } catch (NotANumberException | ParseException | NoSuchSortException | NoSuchItemException
                | OnCashierModeException | InvalidNumberException e) {
            assertEquals(InventoryMessages.MESSAGE_NOT_A_NUMBER, e.getMessage());
        }
    }

    @Test
    public void parser_sortCommand_successful() {
        Command command = null;

        assertThrows(Exception.class, () -> parser.parseCommand("sort", new InventoryList()));

        try {
            command = parser.parseCommand("sort description", new InventoryList());
        } catch (Exception e) {
            fail();
        }

        assertTrue(command instanceof SortDescriptionCommand);

        try {
            command = parser.parseCommand("sort category", new InventoryList());
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof SortCategoryCommand);

        try {
            command = parser.parseCommand("sort quantity", new InventoryList());
        } catch (Exception e) {
            fail();
        }
        assertTrue(command instanceof SortQuantityCommand);
    }

    @Test
    public void parser_sortCommand_unsuccessful() {
        Command command = null;

        try {
            command = parser.parseCommand("sort person", new InventoryList());
        } catch (NoSuchSortException | ParseException | NotANumberException | NoSuchItemException
                | OnCashierModeException | InvalidNumberException e) {
            assertEquals(InventoryMessages.MESSAGE_NO_SUCH_SORT_COMMAND, e.toString());
        }
    }

    @Test
    public void parser_invalidCommandExceptionTest() {
        try {
            parser.parseCommand("command", new InventoryList());
        } catch (NoSuchSortException | NoSuchItemException | NotANumberException | ParseException
                | OnCashierModeException | InvalidNumberException e) {
            assertEquals(InventoryMessages.MESSAGE_NO_COMMAND, e.getMessage());
        }
    }
}
