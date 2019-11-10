package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteIncomeCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortReverseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.ContactUtil;
import seedu.address.testutil.EditIncomeDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.IncomeBuilder;
import seedu.address.testutil.IncomeUtil;

public class FinSecParserTest {

    private final FinSecParser parser = new FinSecParser();

    //=========== Contacts ================================================================================
    @Test
    public void parseCommand_addContact() throws Exception {
        Contact contact = new ContactBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(ContactUtil.getAddCommand(contact));
        assertEquals(new AddContactCommand(contact), command);
    }


    @Test
    public void parseCommand_deleteContact() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editContact() throws Exception {
        Contact contact = new ContactBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(contact).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(
                EditContactCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ContactUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //=========== Income ================================================================================
    @Test
    public void parseCommand_addIncome() throws Exception {
        Income income = new IncomeBuilder().build();
        AddIncomeCommand command = (AddIncomeCommand) parser.parseCommand(IncomeUtil.getAddCommand(income));
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_deleteIncome() throws Exception {
        DeleteIncomeCommand command = (DeleteIncomeCommand) parser.parseCommand(
                DeleteIncomeCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteIncomeCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editIncome() throws Exception {
        Income income = new IncomeBuilder().build();
        EditIncomeDescriptor descriptor = new EditIncomeDescriptorBuilder(income).build();
        EditIncomeCommand command = (EditIncomeCommand) parser.parseCommand(
                EditIncomeCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + IncomeUtil.getEditIncomeDescriptorDetails(descriptor));
        assertEquals(new EditIncomeCommand(INDEX_FIRST_PERSON, descriptor), command);
    }


    //=========== Shortcut ================================================================================




    //=========== FinSec ================================================================================


    //@@author{lawncegoh}
    //=========== Goto ==================================================================================
    @Test
    public void parseCommand_goto() throws ParseException {
        assertThrows(ParseException.class, () -> parser.parseCommand(GotoCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(GotoCommand.COMMAND_WORD + " contacts") instanceof GotoCommand);
    }

    //@@author{lawncegoh}
    //=========== Check ==================================================================================
    @Test
    public void parseCommand_check() throws ParseException {
        assertThrows(ParseException.class, () -> parser.parseCommand(CheckCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(CheckCommand.COMMAND_WORD + " 1") instanceof CheckCommand);
    }

    //@@author{lawncegoh}
    //=========== Sort ==================================================================================

    @Test
    public void parseCommand_sort() throws ParseException {
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " name") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " date") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " status") instanceof SortCommand);
    }

    //@@author{lawncegoh}
    //=========== Reverse ==================================================================================

    @Test
    public void parseCommand_reverse() throws ParseException {
        assertThrows(ParseException.class, () -> parser.parseCommand(SortReverseCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(SortReverseCommand.COMMAND_WORD + " name") instanceof SortReverseCommand);
        assertTrue(parser.parseCommand(SortReverseCommand.COMMAND_WORD + " date") instanceof SortReverseCommand);
        assertTrue(parser.parseCommand(SortReverseCommand.COMMAND_WORD + " status") instanceof SortReverseCommand);
    }

    //@@author{lawncegoh}
    //=========== Clear ==================================================================================
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

}
