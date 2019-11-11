package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseSuccess;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.logic.parser.quiz.McqInputCommandParser;

public class McqInputCommandParserTest {

    private QuizParser<McqInputCommand> parser = new McqInputCommandParser();

    @Test
    public void parse_validInput_returnsMcqInputCommand() {
        assertQuizParseSuccess(parser, "a", MCQ_A, MCQ_A_COMMAND);
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        assertQuizParseFailure(parser, "z", MCQ_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, McqInputCommand.MESSAGE_USAGE));
    }
}
