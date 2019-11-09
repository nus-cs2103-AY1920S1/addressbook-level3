package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseSuccess;
import static seedu.revision.testutil.TypicalMcq.MCQ_C;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.main.ExitCommand;
import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.logic.parser.quiz.QuizCommandParser;

public class QuizCommandParserTest {
    private static final String VALID_MCQ_ANSWER = "A";
    private static final String VALID_EXIT = "exit";
    private QuizCommandParser parser = new QuizCommandParser();

    @Test
    public void parseCommand_validInput_success() {
        //Boundary Value Analysis. Valid EP for MCQ: [a-b]
        assertQuizParseSuccess(parser, VALID_MCQ_ANSWER, MCQ_C, new McqInputCommand(VALID_MCQ_ANSWER, MCQ_C));
        //TODO: Add TrueFalse and Saq
    }

    @Test
    public void parseCommand_inValidInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, McqInputCommand.MESSAGE_USAGE);
        //Boundary Value Analysis. Invalid EPs for MCQ: [whitespace],[characters other than a-d],[2 or more characters]
        assertQuizParseFailure(parser, " ", MCQ_C, MESSAGE_UNKNOWN_COMMAND); //Whitespace character
        assertQuizParseFailure(parser, "e", MCQ_C, expectedMessage); // character after d
        assertQuizParseFailure(parser, "aa", MCQ_C, expectedMessage); // two valid characters
        assertQuizParseFailure(parser, "1", MCQ_C, expectedMessage); // Number
        //TODO: Add TrueFalse and Saq
    }

    @Test
    public void parse_exitCommand_success() {
        assertQuizParseSuccess(parser, VALID_EXIT, MCQ_C, new ExitCommand());
    }
}
