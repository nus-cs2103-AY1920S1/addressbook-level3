package seedu.address.logic.parser.statistics;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.statistics.GetQnsCommand;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

public class GetQnsCommandParserTest {

    private GetQnsCommandParser parser = new GetQnsCommandParser();

    @Test
    public void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetQnsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " -c -i", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetQnsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_quizResultFilterWithoutSubject_success() {
        // get correct qns
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndResult();
        GetQnsCommand expectedCommand = new GetQnsCommand(quizResultFilter);

        assertParseSuccess(parser, " -c", expectedCommand);

        // get incorrect qns
        quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("false")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndResult();
        expectedCommand = new GetQnsCommand(quizResultFilter);

        assertParseSuccess(parser, " -i", expectedCommand);
    }

    @Test
    public void parse_quizResultFilterWithSubject_success() {
        // one subject
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>(Arrays.asList("CS2103T")))
                .buildWithSubjectsAndResult();
        GetQnsCommand expectedCommand = new GetQnsCommand(quizResultFilter);

        assertParseSuccess(parser, " -c s/CS2103T", expectedCommand);

        // multiple subjects
        quizResultFilter = new QuizResultFilterBuilder()
                .withIsCorrectQns("true")
                .withSubjects(new ArrayList<>(Arrays.asList("CS2103T", "CS2101")))
                .buildWithSubjectsAndResult();
        expectedCommand = new GetQnsCommand(quizResultFilter);

        assertParseSuccess(parser, " -c s/CS2103T s/CS2101", expectedCommand);

    }
}
