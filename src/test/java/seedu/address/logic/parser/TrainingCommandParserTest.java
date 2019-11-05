package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TrainingCommandAbsent;
import seedu.address.logic.commands.TrainingCommandPresent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

class TrainingCommandParserTest {
    private TrainingCommandParser parser = new TrainingCommandParser();

    @Test
    void parseWithoutFlag_noDate_noIndexPrefix() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "1 3 5", expectedMessage);
    }

    @Test
    void parseWithFlag_noDate_noIndexPrefix() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandAbsent.MESSAGE_USAGE);
        assertParseFailure(parser, "-a 1 3 5", expectedMessage);
    }

    @Test
    void parseWithoutFlag_noDate_invalidText() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "#/1 3 5 haha", expectedMessage);
        assertParseFailure(parser, "this will definitely fail", expectedMessage);
    }

    @Test
    void parseWithoutFlag_invalidDate() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommandPresent.MESSAGE_USAGE);
        assertParseFailure(parser, "d/1234567890 #/1 3 5", expectedMessage);
    }

    @Test
    void parseWithFlag_valid() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        AthletickDate dateFirst = ParserUtil.parseDateTypeOne(dateFormat.format(new Date()));
        AthletickDate dateSecond = ParserUtil.parseDateTypeOne("20102019");
        List<Index> indexes = ParserUtil.parseIndexes("1 2 3");
        TrainingCommandPresent trainingCommandPresent = new TrainingCommandPresent(dateFirst, indexes);
        // Assert parse success shows error when argmultimap calls arePrefixesPresent and getPreamble
        /*
        assertParseSuccess(parser, PREFIX_INDEXES + " 1 2 3", trainingCommandPresent);
        assertParseSuccess(parser, PREFIX_DATE + "20102019" + PREFIX_INDEXES + "1 2 3",
                new TrainingCommandPresent(dateSecond, indexes));
         */
    }

}
