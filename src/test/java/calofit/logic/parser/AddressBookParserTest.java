package calofit.logic.parser;

import calofit.logic.commands.*;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.meal.Meal;
import calofit.model.meal.NameContainsKeywordsPredicate;
import calofit.testutil.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static calofit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Meal meal = new MealBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(MealUtil.getAddCommand(meal));
        assertEquals(new AddCommand(meal), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_MEAL.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_MEAL), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Meal meal = new MealBuilder().build();
        EditCommand.EditMealDescriptor descriptor = new EditMealDescriptorBuilder(meal).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_MEAL.getOneBased() + " " + MealUtil.getEditMealDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
        Assert.assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
