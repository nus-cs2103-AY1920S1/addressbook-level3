package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.note.NoteCommandParserTest.INVALID_NOTE_SYNTAX_COMMAND;
import static seedu.address.logic.parser.note.NoteCommandParserTest.VALID_COMMAND_DEFAULT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.event.EventCommand;
import seedu.address.logic.commands.group.GroupCommand;
import seedu.address.logic.commands.mark.MarkCommand;
import seedu.address.logic.commands.note.NoteCommand;
import seedu.address.logic.commands.question.QuestionCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.commands.quiz.QuizCommand;
import seedu.address.logic.commands.statistics.StatisticsCommand;
import seedu.address.logic.commands.student.StudentCommand;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.statistics.StatisticsCommandParserTest;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionUtil;

public class NjoyParserTest {

    private final NjoyParser parser = new NjoyParser();
    private final String invalidCommand = "Invalid Text";

    @Test
    public void parseCommand_statistics_success() throws Exception {
        assertTrue(parser.parseCommand(StatisticsCommand.COMMAND_WORD
                + StatisticsCommandParserTest.VALID_COMMAND)
                instanceof StatisticsCommand);
    }

    @Test
    public void parseCommand_student_success() throws Exception {
        assertTrue(parser.parseCommand("student name/Student Testing")
                instanceof StudentCommand);
    }

    @Test
    public void parseCommand_tag_success() throws Exception {
        assertTrue(parser.parseCommand("tag index/1 tag/TestTag")
                instanceof TagCommand);
    }

    @Test
    public void parseCommand_mark_success() throws Exception {
        assertTrue(parser.parseCommand("mark index/1")
                instanceof MarkCommand);
    }

    @Test
    public void parseCommand_unmmark_success() throws Exception {
        assertTrue(parser.parseCommand("mark unmark index/1")
                instanceof MarkCommand);
    }

    @Test
    public void parseCommand_group_success() throws Exception {
        assertTrue(parser.parseCommand("group groupID/G03")
                instanceof GroupCommand);
    }

    @Test
    public void parseCommand_questionOpenEnded_success() throws Exception {
        assertTrue(parser.parseCommand("question question/test answer/test type/open")
                instanceof QuestionCommand);
    }

    @Test
    public void parseCommand_questionMcq_success() throws Exception {
        assertTrue(parser.parseCommand("question question/test answer/1 type/mcq a/1 b/2 c/3 d/4")
                instanceof QuestionCommand);
    }

    @Test
    public void parseCommand_quiz_success() throws Exception {
        assertTrue(parser.parseCommand("quiz manual quizID/test questionNumber/1 2 3")
                instanceof QuizCommand);
    }

    @Test
    public void parseCommand_invalidStatistics_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(StatisticsCommand.COMMAND_WORD
                        + StatisticsCommandParserTest.INVALID_COMMAND));
    }


    @Test
    public void parseCommand_invalidTag_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(TagCommand.COMMAND_WORD
                        + invalidCommand));
    }


    @Test
    public void parseCommand_invalidStudent_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(StudentCommand.COMMAND_WORD
                        + invalidCommand));
    }

    @Test
    public void parseCommand_invalidMark_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(MarkCommand.COMMAND_WORD
                        + invalidCommand));
    }

    @Test
    public void parseCommand_invalidUnmark_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(MarkCommand.COMMAND_WORD + " unmark "
                        + invalidCommand));
    }

    @Test
    public void parseCommand_invalidGroup_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(GroupCommand.COMMAND_WORD
                        + invalidCommand));
    }

    @Test
    public void parseCommand_invalidQuestion_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(QuestionCommand.COMMAND_WORD
                        + invalidCommand));
    }

    @Test
    public void parseCommand_invalidQuiz_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(QuizCommand.COMMAND_WORD
                        + invalidCommand));
    }

    @Test
    public void parseCommand_note_success() throws Exception {
        assertTrue(parser.parseCommand(NoteCommand.COMMAND_WORD + VALID_COMMAND_DEFAULT)
                instanceof NoteCommand);
    }

    @Test
    public void parseCommand_invalidNote_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(NoteCommand.COMMAND_WORD + INVALID_NOTE_SYNTAX_COMMAND));
    }

    @Test
    public void parseCommand_invalidEvent_throwsException() {
        assertThrows(ParseException.class, () ->
                parser.parseCommand(EventCommand.COMMAND_WORD + invalidCommand));
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
        assertEquals(new QuestionEditCommand(INDEX_ONE, fields), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
