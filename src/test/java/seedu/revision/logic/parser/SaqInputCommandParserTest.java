package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseSuccess;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A;
import static seedu.revision.testutil.TypicalSaqs.SAQ_B;
import static seedu.revision.testutil.TypicalSaqs.SAQ_B_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.quiz.SaqInputCommand;
import seedu.revision.logic.parser.quiz.SaqInputCommandParser;

public class SaqInputCommandParserTest {

    private QuizParser<SaqInputCommand> parser = new SaqInputCommandParser();

    @Test
    public void parse_validInput_returnsMcqInputCommand() {
        assertQuizParseSuccess(parser, "UML Diagram", SAQ_B, SAQ_B_COMMAND);
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        assertQuizParseFailure(parser, " ", MCQ_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaqInputCommand.MESSAGE_USAGE));
    }
}
