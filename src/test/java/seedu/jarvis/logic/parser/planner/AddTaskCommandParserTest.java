package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

class AddTaskCommandParserTest {

    @Test
    void parse_allFieldsPresent_success() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/todo des/borrow book f/weekly p/high #school";

        Task expectedTask = new Todo("borrow book");
        expectedTask.setFrequency(Frequency.WEEKLY);
        expectedTask.setPriority(Priority.HIGH);
        expectedTask.addTag(new Tag("school"));

        Command expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_essentialFieldsTodoPresent_success() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/todo des/borrow book";

        Task expectedTask = new Todo("borrow book");

        Command expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_essentialFieldsDeadlinePresent_success() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/deadline des/borrow book d/20/10/2019";

        Task expectedTask = new Deadline("borrow book", LocalDate.parse("20/10/2019",
                                            Task.getDateFormat()));

        Command expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_essentialFieldsEventPresent_success() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/event des/bday d/20/10/2019//21/10/2019";

        Task expectedTask = new Event("bday", LocalDate.parse("20/10/2019", Task.getDateFormat()),
                                        LocalDate.parse("21/10/2019", Task.getDateFormat()));

        Command expectedCommand = new AddTaskCommand(expectedTask);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_wrongTaskType_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/task des/borrow book";

        String expectedMessage = ParserUtil.MESSAGE_INVALID_TASK_TYPE;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    void parse_missingDateDeadline_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/deadline des/homework";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE_DEADLINE);

        assertParseFailure(parser, userInput, expectedMessage);

    }

    @Test
    void parse_missingDateEvent_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/event des/homework d/18/10/2019";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE_EVENT);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    void parse_invalidDate_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/deadline des/homework d/hello there";

        String expectedMessage = ParserUtil.MESSAGE_INVALID_DATE;

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    void parse_invalidPriority_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/todo des/borrow book p/highest";

        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);

    }

    @Test
    void parse_invalidFrequency_exceptionThrown() {
        AddTaskCommandParser parser = new AddTaskCommandParser();
        String userInput = " t/todo des/borrow book f/highest";

        String expectedMessage = Frequency.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
