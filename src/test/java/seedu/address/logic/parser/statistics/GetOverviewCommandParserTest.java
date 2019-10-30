package seedu.address.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.logic.commands.statistics.GetOverviewCommand.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.statistics.GetOverviewCommand.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.statistics.GetOverviewCommand;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

public class GetOverviewCommandParserTest {

    private GetOverviewCommandParser parser = new GetOverviewCommandParser();

    @Test
    public void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, " dt/worfm", MESSAGE_INVALID_COMMAND_FORMAT);
        assertParseFailure(parser, " dt/worfm dt/wpck, dt/w[rd", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser, " dt/worfm dt/", MESSAGE_INVALID_DATE_FORMAT);
        assertParseFailure(parser, " dt/worfm dt/09/10/2019", MESSAGE_INVALID_DATE_FORMAT);
        assertParseFailure(parser, " dt/09-10-2019 dt/09-02-2019", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_quizResultFilterWithDate_success() {
        try {
            QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                    .withStartDate("09/10/2019")
                    .withEndDate("10/10/2019")
                    .buildWithDates();
            GetOverviewCommand expectedCommand = new GetOverviewCommand(quizResultFilter);

            assertParseSuccess(parser, " dt/09/10/2019 dt/10/10/2019", expectedCommand);
        } catch (ParseException e) {
            fail(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    @Test
    public void parse_quizResultFilterWithoutDate_succcess() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .build();
        GetOverviewCommand expectedCommand = new GetOverviewCommand(quizResultFilter);
        assertParseSuccess(parser, "", expectedCommand);
    }
}
