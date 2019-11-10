package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.common.Command;
import seedu.address.logic.commands.common.CommandHistory;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.patients.EditPatientDetailsCommand;
import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.logic.commands.patients.RegisterPatientCommand;
import seedu.address.logic.commands.patients.UnregisterPatientCommand;
import seedu.address.logic.commands.utils.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonContainsKeywordPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TestUtil;

public class SystemCommandParserTest {

    private Model model = TestUtil.getTypicalModelManager();
    private final CommandHistory history = new CommandHistory();
    private final SystemCommandParser parser = new SystemCommandParser(history);

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        Command command = parser.parseCommand(PersonUtil.getAddCommand(person), model);
        assertEquals(new ReversibleActionPairCommand(
            new RegisterPatientCommand(person),
            new UnregisterPatientCommand(person)
            ), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person personToEdit = model.getFilteredPatientList().get(0);
        Person editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToEdit)
                .withName(VALID_NAME_BOB).build();
        Command command = parser.parseCommand(EditPatientDetailsCommand.COMMAND_WORD
                + " " + PREFIX_ENTRY.toString() + INDEX_FIRST_PERSON.getOneBased()
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor),
                model);

        assertEquals(new ReversibleActionPairCommand(
            new EditPatientDetailsCommand(personToEdit, editedPerson),
            new EditPatientDetailsCommand(editedPerson, personToEdit)
        ), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, model) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", model) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        ListPatientCommand command = (ListPatientCommand) parser.parseCommand(
                ListPatientCommand.COMMAND_WORD + " foo",
                model);
        assertEquals(new ListPatientCommand(new PersonContainsKeywordPredicate("foo")), command);

        command = (ListPatientCommand) parser.parseCommand(
                ListPatientCommand.COMMAND_WORD + " foo bar",
                model);
        assertEquals(new ListPatientCommand(new PersonContainsKeywordPredicate("foo bar")), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, model) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", model) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", model));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
            "unknownCommand", model));
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, model) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3", model) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, model) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3", model) instanceof RedoCommand);
    }
}
