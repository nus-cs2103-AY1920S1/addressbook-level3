package seedu.address.logic.parser.statistics;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.statistics.GetStatisticsCommand;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

public class GetStatisticsCommandParserTest {

    private GetStatisticsCommandParser parser = new GetStatisticsCommandParser();

    @Test
    public void parse_invalidCommandFormat_failure() {
        // multiple difficulty
        assertParseFailure(parser, " d/d1 d/d2", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_quizResultFilterWithoutFilter_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder().buildEmptySubjectList();
        GetStatisticsCommand expectedCommand = new GetStatisticsCommand(quizResultFilter);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_quizResultFilterWithSubject_success() {
        // one subject
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(Arrays.asList("CS2103T"))
                .buildWithSubjects();
        GetStatisticsCommand expectedCommand = new GetStatisticsCommand(quizResultFilter);
        assertParseSuccess(parser, " s/CS2103T", expectedCommand);

        // multiple subjects
        quizResultFilter = new QuizResultFilterBuilder()
                .withSubjects(Arrays.asList("CS2103T", "CS2101", "CS2103"))
                .buildWithSubjects();

        expectedCommand = new GetStatisticsCommand(quizResultFilter);
        assertParseSuccess(parser, " s/CS2103T s/CS2101 s/CS2103", expectedCommand);
    }

    @Test
    public void parse_quizResultFilterWithDifficulty_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withDifficulty("easy")
                .withSubjects(new ArrayList<>())
                .buildWithSubjectsAndDifficulty();
        GetStatisticsCommand expectedCommand = new GetStatisticsCommand(quizResultFilter);
        assertParseSuccess(parser, " d/easy", expectedCommand);
    }

    @Test
    public void parse_quizResultFilterWithDifficultyAndSubject_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .withDifficulty("easy")
                .withSubjects(new ArrayList<>(Arrays.asList("CS2103T", "CS2101")))
                .buildWithSubjectsAndDifficulty();
        GetStatisticsCommand expectedCommand =
                new GetStatisticsCommand(quizResultFilter);
        assertParseSuccess(parser, " d/easy s/CS2103T s/CS2101", expectedCommand);
    }
}
