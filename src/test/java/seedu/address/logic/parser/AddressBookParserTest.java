package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.ActivityCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisinviteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExpenseCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.person.Person;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.EditActivityDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_activity() throws Exception {
        Activity activity = new ActivityBuilder().build();
        ActivityCommand command = (ActivityCommand) parser.parseCommand(ActivityCommand.COMMAND_WORD + " "
                + PREFIX_TITLE + activity.getTitle().toString());
        assertEquals(new ActivityCommand(activity.getTitle(), new ArrayList<>()), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_disinvite() throws Exception {
        Person person = new PersonBuilder().build();
        DisinviteCommand command = (DisinviteCommand) parser.parseCommand(
                DisinviteCommand.COMMAND_WORD + " " + PREFIX_PARTICIPANT + person.getName());
        assertEquals(new DisinviteCommand(List.of(person.getNameStr())), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor pd = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PersonUtil.getEditPersonDescriptorDetails(pd));
        assertEquals(new EditCommand(pd, new EditActivityDescriptor()), command);

        Activity activity = new ActivityBuilder().build();
        EditActivityDescriptor ad = new EditActivityDescriptorBuilder(activity).build();
        command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PREFIX_TITLE + activity.getTitle().toString());
        assertEquals(new EditCommand(new EditPersonDescriptor(), ad), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_expense() throws Exception {
        Person person = new PersonBuilder().build();
        Amount amount = new Amount(1);
        ExpenseCommand command = (ExpenseCommand) parser.parseCommand(ExpenseCommand.COMMAND_WORD + " "
                + PREFIX_EXPENSE + amount.toString() + " " + PREFIX_PARTICIPANT + person.getName());
        List<String> arr = List.of(person.getNameStr());
        assertEquals(command, new ExpenseCommand(arr, amount, ""));
    }

    @Test
    public void parseCommand_find() throws Exception {
        String searchTerm = "foo bar baz";
        String[] keywords = searchTerm.split("\\s+");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + searchTerm);
        assertEquals(new FindCommand(keywords, searchTerm), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_invite() throws Exception {
        Person person = new PersonBuilder().build();
        InviteCommand command = (InviteCommand) parser.parseCommand(
                InviteCommand.COMMAND_WORD + " " + PREFIX_PARTICIPANT + person.getName());
        assertEquals(new InviteCommand(List.of(person.getNameStr())), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        ListCommand command = (ListCommand) parser.parseCommand(ListCommand.COMMAND_WORD + " " + PREFIX_ACTIVITY);
        assertEquals(new ListCommand(CommandSubType.ACTIVITY), command);
    }

    @Test
    public void parseCommand_settle() throws Exception {
        Person p1 = new PersonBuilder().build();
        Person p2 = new PersonBuilder().withName("name").build();
        SettleCommand command = (SettleCommand) parser.parseCommand(SettleCommand.COMMAND_WORD + " "
                + PREFIX_PARTICIPANT + p1.getName() + " " + PREFIX_PARTICIPANT + p2.getName());
        assertEquals(new SettleCommand(List.of(p1.getNameStr(), p2.getNameStr()), new Amount(0)), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD + " "
                + PREFIX_ACTIVITY + "1");
        assertEquals(new ViewCommand(CommandSubType.ACTIVITY, INDEX_FIRST), command);
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
}
