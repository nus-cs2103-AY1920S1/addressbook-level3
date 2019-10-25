package seedu.address.cashier.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_INDEX;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_QUANTITY_1;
import static seedu.address.cashier.commands.CommandTestUtil.DESC_QUANTITY_2;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_DESCRIPTION_FISH_BURGER;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_QUANTITY_1;
import static seedu.address.cashier.commands.CommandTestUtil.VALID_QUANTITY_2;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.logic.commands.DeleteCommand;
import seedu.address.cashier.logic.commands.EditCommand;
import seedu.address.cashier.logic.parser.CashierTabParser;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class CashierTabParserTest {

    private final CashierTabParser parser = new CashierTabParser();
    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parseCommand_add() throws Exception {
        AddCommand addCommand = (AddCommand) parser.parseCommand(AddCommand.COMMAND_WORD +
                DESC_DESCRIPTION_FISH_BURGER + DESC_QUANTITY_1, model, personModel);
        assertEquals(new AddCommand(VALID_DESCRIPTION_FISH_BURGER, VALID_QUANTITY_1), addCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + VALID_INDEX, model, personModel);
        assertEquals(new DeleteCommand(1), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        model.addItem(TypicalItem.FISH_BURGER);
        EditCommand editCommand = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + DESC_INDEX
                + DESC_QUANTITY_2, model, personModel);
        assertEquals(new EditCommand(VALID_INDEX, VALID_QUANTITY_2), editCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                personModel);
        assertEquals(new FindCommand(new TransactionContainsKeywordsPredicate(keywords)), command);
    }

//    @Test
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, personModel) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", personModel) instanceof ExitCommand);
//    }

    
//    @Test
//    public void parseCommand_setCashier() throws Exception {
//        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD, personModel) instanceof BackCommand);
//        assertTrue(parser.parseCommand(BackCommand.COMMAND_WORD + " 3", personModel) instanceof BackCommand);
//    }
//
//    @Test
//    public void parseCommand_checkout() throws Exception {
//        SortNameCommand command = (SortNameCommand) parser.parseCommand(
//                SortCommand.COMMAND_WORD + " name", personModel);
//        assertEquals(new SortNameCommand(), command);
//    }
//
//    @Test
//    public void parseCommand_unrecognisedInput_throwsParseException() {
//        assertThrows(Exception.class, TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT, () ->
//                parser.parseCommand("", personModel));
//    }
//
//    @Test
//    public void parseCommand_unknownCommand_throwsParseException() {
//        assertThrows(Exception.class, TransactionMessages.MESSAGE_NO_SUCH_COMMAND, () ->
//                parser.parseCommand("unknownCommand", personModel));
//    }
}
