package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
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
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAnswerCommand;
import seedu.address.logic.commands.FindCategoryCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindQuestionCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.AnswerContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;
import seedu.address.testutil.EditFlashCardDescriptorBuilder;
import seedu.address.testutil.FlashCardBuilder;
import seedu.address.testutil.FlashCardUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

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
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new QuestionContainsAnyKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findCategory() throws Exception {
        List<String> keywords = Arrays.asList("C", "cs2101");
        FindCategoryCommand command = (FindCategoryCommand) parser.parseCommand(
                FindCategoryCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCategoryCommand(new CategoryContainsAnyKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_findQuestion() throws Exception {
        List<String> keywords = Arrays.asList("what", "cs2101");
        FindQuestionCommand command = (FindQuestionCommand) parser.parseCommand(
            FindQuestionCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindQuestionCommand(new QuestionContainsAnyKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_findAnswer() throws Exception {
        List<String> keywords = Arrays.asList("C", "cs2101");
        FindAnswerCommand command = (FindAnswerCommand) parser.parseCommand(
                FindAnswerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindAnswerCommand(new AnswerContainsAnyKeywordsPredicate(keywords)), command);

    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
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
}
