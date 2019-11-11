package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseFailure;
import static seedu.revision.logic.parser.CommandParserTestUtil.assertQuizParseSuccess;
import static seedu.revision.testutil.TypicalMcqs.MCQ_A;
import static seedu.revision.testutil.TypicalTrueFalse.TF_A;
import static seedu.revision.testutil.TypicalTrueFalse.TF_A_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.quiz.TfInputCommand;
import seedu.revision.logic.parser.quiz.TfInputCommandParser;

public class TfInputCommandParserTest {

    private QuizParser<TfInputCommand> parser = new TfInputCommandParser();

    @Test
    public void parse_validInput_returnsTfInputCommand() {
        assertQuizParseSuccess(parser, "True", TF_A, TF_A_COMMAND);
    }

    @Test
    public void parse_invalidInput_throwsParseException() {
        assertQuizParseFailure(parser, "not true", MCQ_A,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TfInputCommand.MESSAGE_USAGE));
    }
}
