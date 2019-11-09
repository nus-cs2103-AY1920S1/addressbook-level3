package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static calofit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.DeleteCommand;
import calofit.logic.commands.EditCommand;
import calofit.logic.commands.ExitCommand;
import calofit.logic.commands.FindCommand;
import calofit.logic.commands.HelpCommand;
import calofit.logic.commands.ListCommand;
import calofit.logic.commands.ReportCommand;
import calofit.logic.commands.SetBudgetCommand;
import calofit.logic.commands.SuggestCommand;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.dish.Dish;
import calofit.model.dish.NameContainsKeywordsPredicate;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.DishUtil;
import calofit.testutil.EditDishDescriptorBuilder;
import calofit.testutil.TypicalIndexes;

public class CommandParserTest {

    private final CommandParser parser = new CommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Dish dish = new DishBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(DishUtil.getAddCommand(dish));
        assertEquals(new AddCommand(dish), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_MEAL.getOneBased());
        List<Integer> mealIndexList = new ArrayList<Integer>();
        mealIndexList.add(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
        assertEquals(new DeleteCommand(mealIndexList), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Dish dish = new DishBuilder().build();
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder(dish).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_MEAL.getOneBased() + " "
                + DishUtil.getEditDishDescriptorDetails(descriptor)
        );
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
    public void parseCommand_report() throws ParseException {
        assertTrue(parser.parseCommand(ReportCommand.COMMAND_WORD) instanceof ReportCommand);
    }

    @Test
    public void parseCommand_suggest() throws ParseException {
        assertTrue(parser.parseCommand(SuggestCommand.COMMAND_WORD) instanceof SuggestCommand);
    }

    @Test
    public void parseCommand_set() throws ParseException {
        assertTrue(parser.parseCommand(SetBudgetCommand.COMMMAND_WORD + " 10")
            instanceof SetBudgetCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
            parser.parseCommand("unknownCommand"));
    }
}
