package seedu.revision.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalMcqs.MCQ_STUB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.commands.main.DeleteCommand;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.main.FindCommand;
import seedu.revision.logic.commands.main.HelpCommand;
import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.StartCommand;
import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.logic.parser.main.ParserManager;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.predicates.QuestionContainsKeywordsPredicate;
import seedu.revision.testutil.AnswerableUtil;
import seedu.revision.testutil.EditAnswerableDescriptorBuilder;
import seedu.revision.testutil.McqBuilder;

public class ParserManagerTest {

    private final ParserManager parser = new ParserManager();

    @Test
    public void parseCommand_add() throws Exception {
        Answerable answerable = new McqBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AnswerableUtil.getAddCommand(answerable));
        assertEquals(new AddCommand(answerable), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ANSWERABLE.getOneBased());
        ArrayList<Index> indexToDelete = new ArrayList<>();
        indexToDelete.add(INDEX_FIRST_ANSWERABLE);
        assertEquals(new DeleteCommand(indexToDelete), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Answerable answerable = new McqBuilder().build();
        EditCommand.EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder(answerable).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ANSWERABLE.getOneBased() + " " + AnswerableUtil
                .getEditAnswerableDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ANSWERABLE, descriptor), command);
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
        assertEquals(new FindCommand(new QuestionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " diff/1") instanceof ListCommand);
    }

    @Test
    public void parseCommand_start() throws Exception {
        assertTrue(parser.parseCommand(StartCommand.COMMAND_WORD + " mode/normal") instanceof StartCommand);
        assertTrue(parser.parseCommand(StartCommand.COMMAND_WORD + " mode/custom") instanceof StartCommand);
        assertTrue(parser.parseCommand(StartCommand.COMMAND_WORD + " mode/arcade") instanceof StartCommand);
    }

    @Test
    public void parseCommand_mcqInput() throws Exception {
        assertTrue(parser.parseCommand("A", MCQ_STUB) instanceof McqInputCommand);
        assertTrue(parser.parseCommand("d", MCQ_STUB) instanceof McqInputCommand);
    }

    //TODO: Add TrueFalse and SAQ

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
            parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_unknownQuizCommand_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            McqInputCommand.MESSAGE_USAGE), () -> parser.parseCommand("unknownCommand", MCQ_STUB));
    }
}
