package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionDeleteCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionUtil;

public class NjoyParserTest {

    private final NjoyParser parser = new NjoyParser();

    @Test
    public void parseCommand_question_add() throws Exception {
        Question question = new OpenEndedQuestion("Test", "Test Answer");
        QuestionAddCommand command = (QuestionAddCommand) parser
            .parseCommand(QuestionUtil.getAddCommand(question));
        assertEquals(new QuestionAddCommand("Test", "Test Answer", "open"), command);
    }

    @Test
    public void parseCommand_question_delete() throws Exception {
        QuestionDeleteCommand command = (QuestionDeleteCommand) parser.parseCommand(
            QuestionUtil.getDeleteCommand(INDEX_FIRST_PERSON.getOneBased()));
        assertEquals(new QuestionDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_question_edit() throws Exception {
        Question question = new OpenEndedQuestion("Test", "Test Answer");
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "mcq");
        QuestionEditCommand command = (QuestionEditCommand) parser
            .parseCommand(QuestionUtil.getEditCommand(question));
        assertEquals(new QuestionEditCommand(INDEX_FIRST_PERSON, fields), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
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
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
