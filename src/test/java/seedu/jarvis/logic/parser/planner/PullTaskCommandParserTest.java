package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_MULTIPLE_SAME_PREFIX;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.logic.commands.planner.PullTaskCommand;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.model.planner.predicates.TaskFrequencyMatchesFrequencyPredicate;
import seedu.jarvis.model.planner.predicates.TaskPriorityMatchesPriorityPredicate;
import seedu.jarvis.model.planner.predicates.TaskTagMatchesTagPredicate;
import seedu.jarvis.model.planner.predicates.TaskTypeMatchesTypePredicate;

class PullTaskCommandParserTest {

    @Test
    void parse_emptyArg_throwsParseException() {
        PullTaskCommandParser parser = new PullTaskCommandParser();
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                PullTaskCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_multipleArg_throwsParseException() {
        PullTaskCommandParser parser = new PullTaskCommandParser();
        assertParseFailure(parser, "p/low #school ",
                String.format(MESSAGE_MULTIPLE_SAME_PREFIX, PullTaskCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsPullTaskCommand() {
        PullTaskCommandParser parser = new PullTaskCommandParser();

        //task type
        PullTaskCommand expected =
                new PullTaskCommand(new TaskTypeMatchesTypePredicate(TaskType.TODO));
        assertParseSuccess(parser, "t/todo ", expected);

        //priority
        expected = new PullTaskCommand(new TaskPriorityMatchesPriorityPredicate(Priority.HIGH));
        assertParseSuccess(parser, "p/high ", expected);

        //frequency
        expected = new PullTaskCommand(new TaskFrequencyMatchesFrequencyPredicate(Frequency.MONTHLY));
        assertParseSuccess(parser, "f/monthly ", expected);

        //tags
        expected = new PullTaskCommand(new TaskTagMatchesTagPredicate(new Tag("school")));
        assertParseSuccess(parser, "#school ", expected);
    }
}
