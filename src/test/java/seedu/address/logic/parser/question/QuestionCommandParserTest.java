package seedu.address.logic.parser.question;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_ANSWER;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_EDIT_FIELDS;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_QUESTION;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_QUESTION_OPTIONS;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_TEXT_SEARCH;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionDeleteCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.commands.question.QuestionFindCommand;
import seedu.address.logic.commands.question.QuestionListCommand;
import seedu.address.logic.commands.question.QuestionSlideshowCommand;
import seedu.address.logic.parser.CliSyntax;

public class QuestionCommandParserTest {

    public static final String PREFIX_FIND = " " + CliSyntax.PREFIX_FIND.getPrefix();
    public static final String PREFIX_QUESTION = " " + CliSyntax.PREFIX_QUESTION.getPrefix();
    public static final String PREFIX_ANSWER = " " + CliSyntax.PREFIX_ANSWER.getPrefix();
    public static final String PREFIX_TYPE = " " + CliSyntax.PREFIX_TYPE.getPrefix();
    public static final String PREFIX_OPTIONA = " " + CliSyntax.PREFIX_OPTIONA.getPrefix();
    public static final String PREFIX_OPTIONB = " " + CliSyntax.PREFIX_OPTIONB.getPrefix();
    public static final String PREFIX_OPTIONC = " " + CliSyntax.PREFIX_OPTIONC.getPrefix();
    public static final String PREFIX_OPTIOND = " " + CliSyntax.PREFIX_OPTIOND.getPrefix();
    public static final String PREFIX_DELETE = " " + CliSyntax.PREFIX_DELETE.getPrefix();
    public static final String PREFIX_LIST = " " + CliSyntax.PREFIX_LIST.getPrefix();
    public static final String PREFIX_SLIDESHOW = " " + CliSyntax.PREFIX_SLIDESHOW.getPrefix();

    public static final String EXAMPLE_QUESTION = "What is 2+2?";
    public static final String EXAMPLE_ANSWER = "4";
    public static final String EXAMPLE_TYPE_OPEN = "open";
    public static final String EXAMPLE_TYPE_MCQ = "mcq";
    public static final String EXAMPLE_OPTIONA = "1";
    public static final String EXAMPLE_OPTIONB = "2";
    public static final String EXAMPLE_OPTIONC = "3";
    public static final String EXAMPLE_OPTIOND = "4";

    private QuestionCommandParser parser = new QuestionCommandParser();

    @Test
    public void parseCommand_withNoPrefix_throwsParseException() {
        assertParseFailure(parser, "", String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionCommandParser.HELP_MESSAGE));
    }

    @Test
    public void parseCommand_addQuestion_success() {
        // Open Ended
        QuestionAddCommand expectedAddCommand = new QuestionAddCommand(EXAMPLE_QUESTION,
            EXAMPLE_ANSWER, EXAMPLE_TYPE_OPEN);

        String input =
            PREFIX_QUESTION + EXAMPLE_QUESTION + PREFIX_ANSWER + EXAMPLE_ANSWER + PREFIX_TYPE
                + EXAMPLE_TYPE_OPEN;
        assertParseSuccess(parser, input, expectedAddCommand);

        // Mcq
        expectedAddCommand = new QuestionAddCommand(EXAMPLE_QUESTION,
            EXAMPLE_ANSWER, EXAMPLE_TYPE_OPEN, EXAMPLE_OPTIONA, EXAMPLE_OPTIONB, EXAMPLE_OPTIONC,
            EXAMPLE_OPTIOND);

        input =
            PREFIX_QUESTION + EXAMPLE_QUESTION + PREFIX_ANSWER + EXAMPLE_ANSWER + PREFIX_TYPE
                + EXAMPLE_TYPE_OPEN + PREFIX_OPTIONA + EXAMPLE_OPTIONA + PREFIX_OPTIONB
                + EXAMPLE_OPTIONB + PREFIX_OPTIONC + EXAMPLE_OPTIONC + PREFIX_OPTIOND
                + EXAMPLE_OPTIOND;
        assertParseSuccess(parser, input, expectedAddCommand);
    }

    @Test
    public void parseCommand_addQuestionWithEmptyFields_throwsParseException() {
        String input = PREFIX_QUESTION + PREFIX_ANSWER + PREFIX_TYPE;
        assertParseFailure(parser, input, MESSAGE_MISSING_QUESTION);

        input = PREFIX_QUESTION + EXAMPLE_QUESTION + PREFIX_ANSWER + PREFIX_TYPE;
        assertParseFailure(parser, input, MESSAGE_MISSING_ANSWER);

        input = PREFIX_QUESTION + EXAMPLE_QUESTION + PREFIX_ANSWER + EXAMPLE_ANSWER + PREFIX_TYPE;
        assertParseFailure(parser, input, MESSAGE_MISSING_TYPE);

        // Missing mcq options
        input = PREFIX_QUESTION + EXAMPLE_QUESTION + PREFIX_ANSWER + EXAMPLE_ANSWER + PREFIX_TYPE
            + EXAMPLE_TYPE_MCQ;
        assertParseFailure(parser, input, MESSAGE_MISSING_QUESTION_OPTIONS);
    }

    @Test
    public void parseCommand_editValidIndex_success() {
        // Open Ended
        Index index = Index.fromOneBased(1);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("question", "Test Edit");
        fields.put("answer", "Test Answer");
        fields.put("type", "open");
        QuestionEditCommand expectedEditCommand = new QuestionEditCommand(index, fields);

        String input =
            " 1" + PREFIX_QUESTION + fields.get("question") + PREFIX_ANSWER + fields.get("answer")
                + PREFIX_TYPE + fields.get("type");
        assertParseSuccess(parser, input, expectedEditCommand);

        // Mcq
        index = Index.fromOneBased(2);
        HashMap<String, String> options = new HashMap<>();
        options.put("optionA", "1");
        options.put("optionB", "2");
        options.put("optionC", "3");
        options.put("optionD", "4");
        expectedEditCommand = new QuestionEditCommand(index, fields, options);

        input =
            " 2" + PREFIX_QUESTION + fields.get("question") + PREFIX_ANSWER + fields.get("answer")
                + PREFIX_TYPE + fields.get("type") + PREFIX_OPTIONA + fields.get("optionA")
                + PREFIX_OPTIONB + fields.get("optionB") + PREFIX_OPTIONC + fields.get("optionC")
                + PREFIX_OPTIOND + fields.get("optionD");
        assertParseSuccess(parser, input, expectedEditCommand);
    }

    @Test
    public void parseCommand_editFieldWithEmptyInput_throwsParseException() {
        String input = " 1" + PREFIX_QUESTION;
        assertParseFailure(parser, input, MESSAGE_MISSING_QUESTION);

        input = " 1" + PREFIX_ANSWER;
        assertParseFailure(parser, input, MESSAGE_MISSING_ANSWER);

        input = " 1" + PREFIX_TYPE;
        assertParseFailure(parser, input, MESSAGE_MISSING_TYPE);
    }

    @Test
    public void parseCommand_editEmptyFields_throwsParseException() {
        String input = " 1";
        assertParseFailure(parser, input, MESSAGE_MISSING_EDIT_FIELDS);
    }

    @Test
    public void parseCommand_editInvalidIndex_throwsParseException() {
        String input = " -1";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = " 0";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = " a";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void parseCommand_deleteQuestion_success() {
        QuestionDeleteCommand expectedDeleteCommand = new QuestionDeleteCommand(
            Index.fromOneBased(1));

        String input = PREFIX_DELETE + "1";
        assertParseSuccess(parser, input, expectedDeleteCommand);
    }

    @Test
    public void parseCommand_deleteInvalidIndex_throwsParseException() {
        String input = PREFIX_DELETE + "-1";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = PREFIX_DELETE + "0";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = PREFIX_DELETE + "a";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void parseCommand_deleteEmptyIndex_throwsParseException() {
        String input = PREFIX_DELETE;
        assertParseFailure(parser, input, MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void parseCommand_listQuestion_success() {
        QuestionListCommand expectedListCommand = new QuestionListCommand();

        String input = PREFIX_LIST;
        assertParseSuccess(parser, input, expectedListCommand);
    }

    @Test
    public void parseCommand_slideshowWithValidQuestions_success() {
        String indexes = " 1 2 3 4";
        QuestionSlideshowCommand expectedSlideshowCommand = new QuestionSlideshowCommand(indexes);

        String input = PREFIX_SLIDESHOW + indexes;
        assertParseSuccess(parser, input, expectedSlideshowCommand);
    }

    @Test
    public void parseCommand_slideshowInvalidIndex_throwsParseException() {
        String input = PREFIX_SLIDESHOW + "-1";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = PREFIX_SLIDESHOW + "0";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);

        input = PREFIX_SLIDESHOW + "a";
        assertParseFailure(parser, input, MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void parseCommand_slideshowEmptyIndex_throwsParseException() {
        String input = PREFIX_SLIDESHOW;
        assertParseFailure(parser, input, MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void parseCommand_findEmptySearchText_throwsParseException() {
        assertParseFailure(parser, PREFIX_FIND, MESSAGE_MISSING_TEXT_SEARCH);
    }

    @Test
    public void parseCommand_findValidSearchText_success() {
        // no leading and trailing whitespaces
        String searchText = "What is 1+1?";
        String command = PREFIX_FIND + searchText;
        QuestionFindCommand expectedFindCommand =
            new QuestionFindCommand(searchText);
        assertParseSuccess(parser, command, expectedFindCommand);
    }
}
