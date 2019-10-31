package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_TEST_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashCardDescriptor;
import seedu.address.logic.commands.EndTestCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCategoryCommand;
import seedu.address.logic.commands.RateQuestionCommand;
import seedu.address.logic.commands.SearchAnswerCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SearchQuestionCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.AnswerContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.QuestionOrAnswerContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.Rating;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;
import seedu.address.testutil.FlashCardBuilder;
import seedu.address.testutil.FlashCardUtil;

public class KeyboardFlashCardsParserTest {

    private final KeyboardFlashCardsParser parser = new KeyboardFlashCardsParser();

    @Test
    public void parseCommand_add() throws Exception {
        FlashCard flashCard = new FlashCardBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(FlashCardUtil.getAddCommand(flashCard));
        assertEquals(new AddCommand(flashCard), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_FLASHCARD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_FLASHCARD), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        FlashCard flashCard = new FlashCardBuilder().build();
        EditFlashCardDescriptor descriptor = new EditFlashCardDescriptorBuilder(flashCard).build();
        EditCommand command =
                (EditCommand) parser.parseCommand(
                        EditCommand.COMMAND_WORD + " "
                                + INDEX_FIRST_FLASHCARD.getOneBased() + " "
                                + FlashCardUtil.getEditFlashCardDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_FLASHCARD, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new QuestionOrAnswerContainsAnyKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findCategory() throws Exception {
        List<String> keywords = Arrays.asList("C", "cs2101");
        ListCategoryCommand command = (ListCategoryCommand) parser.parseCommand(
                ListCategoryCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new ListCategoryCommand(new CategoryContainsAnyKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_findQuestion() throws Exception {
        List<String> keywords = Arrays.asList("what", "cs2101");
        SearchQuestionCommand command = (SearchQuestionCommand) parser.parseCommand(
            SearchQuestionCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchQuestionCommand(new QuestionContainsAnyKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_findAnswer() throws Exception {
        List<String> keywords = Arrays.asList("C", "cs2101");
        SearchAnswerCommand command = (SearchAnswerCommand) parser.parseCommand(
                SearchAnswerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchAnswerCommand(new AnswerContainsAnyKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListAllCommand.COMMAND_WORD) instanceof ListAllCommand);
        assertTrue(parser.parseCommand(ListAllCommand.COMMAND_WORD + " 3") instanceof ListAllCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    //@@author keiteo
    @Test
    public void parseCommand_startNoParameter_success() throws Exception {
        StartCommand command = (StartCommand) parser.parseCommand("start");
        assertEquals(new StartCommand(parser), command);
    }

    @Test
    public void parseCommand_startAlreadyInTestMode_throwsParseException() {
        parser.startTestMode();
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_TEST_COMMAND, () -> parser.parseCommand("start"));
    }

    @Test
    public void parseCommand_rateQuestionInTestMode_success() throws Exception {
        parser.startTestMode();
        RateQuestionCommand command = (RateQuestionCommand) parser.parseCommand("rate good");
        assertEquals(new RateQuestionCommand(parser, new Rating("good")), command);
    }

    @Test
    public void parseCommand_rateQuestionNotInTestMode_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("rate good"));
    }

    @Test
    public void parseCommand_endInTestMode_throwsParseException() throws Exception {
        parser.startTestMode();
        EndTestCommand command = (EndTestCommand) parser.parseCommand("end");
        assertEquals(new EndTestCommand(parser), command);
    }

    @Test
    public void parseCommand_endNotInTestMode_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("ans"));
    }
}
