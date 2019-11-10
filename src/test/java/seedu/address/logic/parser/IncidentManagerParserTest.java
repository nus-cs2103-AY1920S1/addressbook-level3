package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.IncidentManagerParser.ACCESS_CONTROL_MESSAGE;
import static seedu.address.logic.parser.IncidentManagerParser.GUI_SWAP_MESSAGE;
import static seedu.address.model.Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS;
import static seedu.address.model.Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
// import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditIncidentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FillCommand;
import seedu.address.logic.commands.FindPersonsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListIncidentsCommand;
import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SubmitCommand;
import seedu.address.logic.commands.SwapCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditIncidentBuilder;
import seedu.address.testutil.IncidentUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalIncidents;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class IncidentManagerParserTest {

    private final IncidentManagerParser parser = new IncidentManagerParser(true, true);

    //@@author madanalogy
    @BeforeEach
    public void setUp() {
        parser.setPersonView(true);
        parser.setLoggedIn(true);
    }

    @Test
    public void parseCommand_notLoggedIn_access() throws ParseException {
        parser.setLoggedIn(false);

        // Valid access
        assertDoesNotThrow(() -> parser.parseCommand(HelpCommand.COMMAND_WORD));

        // Invalid access
        assertThrows(ParseException.class, ACCESS_CONTROL_MESSAGE, () -> parser.parseCommand(SwapCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_notPersonView_access() throws ParseException {
        parser.setPersonView(false);

        // Valid access
        assertDoesNotThrow(() -> parser.parseCommand(ListIncidentsCommand.COMMAND_WORD));

        // Invalid access
        assertThrows(ParseException.class,
                GUI_SWAP_MESSAGE, () -> parser.parseCommand(ListPersonsCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_update() throws Exception {
        Person person = new PersonBuilder().build();
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(person).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENTITY.getOneBased() + " " + PersonUtil.getUpdatePersonDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_ENTITY, descriptor), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        RegisterCommand command = (RegisterCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new RegisterCommand(person), command);
    }

    /*
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }
    */

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTITY.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ENTITY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        parser.setPersonView(false);
        EditIncidentCommand.EditIncident editor = new EditIncidentBuilder().build();
        EditIncidentCommand command = (EditIncidentCommand) parser.parseCommand(EditIncidentCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENTITY.getOneBased() + " " + IncidentUtil.getEditIncidentDetails(editor));
        assertEquals(new EditIncidentCommand(INDEX_FIRST_ENTITY, editor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonsCommand command = (FindPersonsCommand) parser.parseCommand(
                FindPersonsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonsCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListPersonsCommand.COMMAND_WORD) instanceof ListPersonsCommand);
        assertTrue(parser.parseCommand(ListPersonsCommand.COMMAND_WORD + " 3") instanceof ListPersonsCommand);
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

    //@@author atharvjoshi
    @Test
    public void parseCommand_fillNoParams() throws Exception {
        parser.setPersonView(false);
        ListIncidentsCommand command = (ListIncidentsCommand) parser.parseCommand(
                FillCommand.COMMAND_WORD);
        assertEquals(new ListIncidentsCommand(PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS), command);

    }

    @Test
    public void parseCommand_fillWithParams() throws Exception {
        parser.setPersonView(false);
        FillCommand command = (FillCommand) parser.parseCommand(
                FillCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTITY.getOneBased()
                + " " + CliSyntax.PREFIX_PHONE + TypicalIncidents.getTypicalCallerNumbers().get(0).toString()
                + " " + CliSyntax.PREFIX_DESCRIPTION + TypicalIncidents.getTypicalDescriptions().get(0).toString());
        assertEquals(new FillCommand(INDEX_FIRST_ENTITY, TypicalIncidents.getTypicalCallerNumbers().get(0),
                TypicalIncidents.getTypicalDescriptions().get(0)), command);
    }

    @Test
    public void parseCommand_submitNoParams() throws Exception {
        parser.setPersonView(false);
        ListIncidentsCommand command = (ListIncidentsCommand) parser.parseCommand(
                SubmitCommand.COMMAND_WORD);
        assertEquals(new ListIncidentsCommand(PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS), command);
    }

    @Test
    public void parseCommand_submitWithParams() throws Exception {
        parser.setPersonView(false);
        SubmitCommand command = (SubmitCommand) parser.parseCommand(
                SubmitCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTITY.getOneBased());
        assertEquals(new SubmitCommand(INDEX_FIRST_ENTITY), command);
    }
}
