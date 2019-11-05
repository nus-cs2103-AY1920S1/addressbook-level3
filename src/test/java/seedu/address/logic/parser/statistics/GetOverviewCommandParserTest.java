package seedu.address.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.statistics.GetOverviewCommand;
import seedu.address.model.quiz.QuizResultFilter;
import seedu.address.testutil.QuizResultFilterBuilder;

class GetOverviewCommandParserTest {

    private GetOverviewCommandParser parser = new GetOverviewCommandParser();

    @Test
    void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, " dt/worfm", String.format(MESSAGE_INVALID_DATE_FORMAT,
                GetOverviewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " dt/worfm dt/wpck, dt/w[rd", String.format(MESSAGE_INVALID_DATE_FORMAT,
                GetOverviewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidDate_failure() {
        assertParseFailure(parser, " dt/worfm dt/", String.format(MESSAGE_INVALID_DATE_FORMAT,
                GetOverviewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " dt/worfm dt/09/10/2019", String.format(MESSAGE_INVALID_DATE_FORMAT,
                GetOverviewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " dt/09-10-2019 dt/09-02-2019", String.format(MESSAGE_INVALID_DATE_FORMAT,
                GetOverviewCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_quizResultFilterWithDate_success() {
        try {
            QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                    .withStartDate("09/10/2019")
                    .withEndDate("10/10/2019")
                    .buildWithDates();
            GetOverviewCommand expectedCommand = new GetOverviewCommand(quizResultFilter,
                    "\n(09/10/2019 to 10/10/2019)");

            assertParseSuccess(parser, " dt/09/10/2019 dt/10/10/2019", expectedCommand);
        } catch (ParseException e) {
            fail(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    @Test
    void parse_quizResultFilterWithoutDate_success() {
        QuizResultFilter quizResultFilter = new QuizResultFilterBuilder()
                .build();
        GetOverviewCommand expectedCommand = new GetOverviewCommand(quizResultFilter, "");
        assertParseSuccess(parser, "", expectedCommand);
    }
}
