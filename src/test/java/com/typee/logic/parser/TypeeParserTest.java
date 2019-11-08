package com.typee.logic.parser;

import static com.typee.testutil.Assert.assertThrows;
import static com.typee.testutil.TypicalIndexes.INDEX_FIRST_ENGAGEMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.ClearCommand;
import com.typee.logic.commands.DeleteCommand;
import com.typee.logic.commands.ExitCommand;
import com.typee.logic.commands.FindCommand;
import com.typee.logic.commands.HelpCommand;
import com.typee.logic.commands.ListCommand;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.EngagementPredicate;

public class TypeeParserTest {

    private final TypeeParser parser = new TypeeParser();

    /*
    @Test
    public void parseCommand_add() throws Exception {
        Engagement engagement = new EngagementBuilder().buildAsAppointment();
        AddCommand command = (AddCommand) parser.parseInput(PersonUtil.getAddCommand(engagement));
        assertEquals(new AddCommand(engagement), command);
    }
     */

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ENGAGEMENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ENGAGEMENT), command);
    }

    /*
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseInput(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENGAGEMENT.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ENGAGEMENT, descriptor), command);
    }
     */

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("a/Mr Foo", "l/bar at level 2", "d/Meeting with baz", "p/high");
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD
                + " " + keywords.stream().collect(Collectors.joining(" ")));
        EngagementPredicate predicate = new EngagementPredicate();
        predicate.setAttendees("Mr Foo");
        predicate.setLocation("bar at level 2");
        predicate.setDescription("Meeting with baz");
        predicate.setPriority("HIGH");
        FindCommand expected = new FindCommand(predicate);
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

}
