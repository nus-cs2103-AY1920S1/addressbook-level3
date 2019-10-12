package com.dukeacademy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.logic.commands.AddCommand;
import com.dukeacademy.logic.commands.ClearCommand;
import com.dukeacademy.logic.commands.DeleteCommand;
import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.logic.commands.ExitCommand;
import com.dukeacademy.logic.commands.FindCommand;
import com.dukeacademy.logic.commands.HelpCommand;
import com.dukeacademy.logic.commands.ListCommand;
import com.dukeacademy.logic.parser.exceptions.ParseException;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.TitleContainsKeywordsPredicate;
import com.dukeacademy.testutil.Assert;
import com.dukeacademy.testutil.EditQuestionDescriptorBuilder;
import com.dukeacademy.testutil.QuestionBuilder;
import com.dukeacademy.testutil.QuestionUtil;
import com.dukeacademy.testutil.TypicalIndexes;

public class QuestionBankParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Question question = new QuestionBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(QuestionUtil.getAddCommand(
            question));
        assertEquals(new AddCommand(question), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_QUESTION.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_QUESTION), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Question question = new QuestionBuilder().build();
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(
            question).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + TypicalIndexes.INDEX_FIRST_QUESTION.getOneBased() + " "
                + QuestionUtil.getEditQuestionDescriptorDetails(descriptor));
        assertEquals(new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION, descriptor), command);
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
        assertEquals(new FindCommand(new TitleContainsKeywordsPredicate(keywords)), command);
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
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class,
                Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
