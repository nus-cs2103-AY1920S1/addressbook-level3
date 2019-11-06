package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.logic.commands.note.ClearNoteCommand;
import seedu.address.logic.commands.note.DeleteNoteCommand;
import seedu.address.logic.commands.note.EditNoteCommand;
import seedu.address.logic.commands.note.FindNoteCommand;
import seedu.address.logic.commands.note.ListNoteCommand;
import seedu.address.logic.commands.questioncommands.AddQuestionCommand;
import seedu.address.logic.commands.questioncommands.DeleteQuestionCommand;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand;
import seedu.address.logic.commands.questioncommands.FindQuestionCommand;
import seedu.address.logic.commands.questioncommands.ListQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionContainsKeywordsPredicate;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.EditQuestionDescriptorBuilder;
import seedu.address.testutil.NoteBuilder;
import seedu.address.testutil.NoteUtil;
import seedu.address.testutil.QuestionBuilder;
import seedu.address.testutil.QuestionUtil;

class AppDataParserTest {
    private final AppDataParser parser = new AppDataParser();

    @Test
    void parseCommand_add() throws Exception {
        Note note = new NoteBuilder().build();
        AddNoteCommand command = (AddNoteCommand) parser.parseCommand(NoteUtil.getAddCommand(note));
        assertEquals(new AddNoteCommand(note), command);
    }

    @Test
    void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearNoteCommand.COMMAND_WORD) instanceof ClearNoteCommand);
        assertTrue(parser.parseCommand(ClearNoteCommand.COMMAND_WORD + " 3") instanceof ClearNoteCommand);
    }

    @Test
    void parseCommand_delete() throws Exception {
        DeleteNoteCommand command = (DeleteNoteCommand) parser.parseCommand(
                DeleteNoteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteNoteCommand(INDEX_FIRST), command);
    }

    @Test
    void parseCommand_edit() throws Exception {
        Note note = new NoteBuilder().build();
        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(note).build();
        EditNoteCommand command = (EditNoteCommand) parser.parseCommand(EditNoteCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + NoteUtil.getEditNoteDescriptorDetails(descriptor));
        assertEquals(new EditNoteCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNoteCommand command = (FindNoteCommand) parser.parseCommand(
                FindNoteCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindNoteCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListNoteCommand.COMMAND_WORD) instanceof ListNoteCommand);
        assertTrue(parser.parseCommand(ListNoteCommand.COMMAND_WORD + " 3") instanceof ListNoteCommand);
    }

    @Test
    void parseCommand_addq() throws Exception {
        Question note = new QuestionBuilder().build();
        AddQuestionCommand command = (AddQuestionCommand) parser.parseCommand(QuestionUtil.getAddCommand(note));
        assertEquals(new AddQuestionCommand(note), command);
    }

    @Test
    void parseCommand_deleteq() throws Exception {
        DeleteQuestionCommand command = (DeleteQuestionCommand) parser.parseCommand(
                DeleteQuestionCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteQuestionCommand(INDEX_FIRST), command);
    }

    @Test
    void parseCommand_editq() throws Exception {
        Question note = new QuestionBuilder().build();
        EditQuestionCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(note).build();
        EditQuestionCommand command = (EditQuestionCommand) parser.parseCommand(EditQuestionCommand
                .COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + QuestionUtil.getEditQuestionDescriptorDetails(descriptor));
        assertEquals(new EditQuestionCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    void parseCommand_findq() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindQuestionCommand command = (FindQuestionCommand) parser.parseCommand(
                FindQuestionCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindQuestionCommand(new QuestionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    void parseCommand_listq() throws Exception {
        assertTrue(parser.parseCommand(ListQuestionCommand.COMMAND_WORD) instanceof ListQuestionCommand);
        assertTrue(parser.parseCommand(ListQuestionCommand.COMMAND_WORD + " 3") instanceof ListQuestionCommand);
    }

    @Test
    void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
