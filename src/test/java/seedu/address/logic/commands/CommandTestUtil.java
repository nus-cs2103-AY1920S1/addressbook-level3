package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.TitleContainsKeywordsPredicate;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardTitleContainsKeywordsPredicate;
import seedu.address.model.flashcard.ScheduleIncrement;
import seedu.address.model.flashcard.Statistics;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteTitleContainsKeywordsPredicate;
import seedu.address.testutil.EditCheatSheetDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String SPACE = " ";

    public static final Statistics DUE_STATISTICS = new Statistics(LocalDate.now().minusDays(1),
            LocalDate.now(), ScheduleIncrement.FIRST);
    public static final Statistics OVERDUE_STATISICS = new Statistics(LocalDate.of(2010, 6, 12),
            LocalDate.of(2010, 6, 13), ScheduleIncrement.FIRST);
    public static final String VALID_QUESTION_ONE = "What is the IntelliJ keyboard shortcut to find a class, file or "
            + "symbol?";
    public static final String VALID_ANSWER_ONE = "Ctrl + Shift + N";
    public static final String VALID_TITLE_ONE = "IntelliJ Question 1";
    public static final String VALID_QUESTION_TWO = "What is the IntelliJ keyboard shortcut to highlight all "
            + "occurrences of the selected fragment in the current file?";
    public static final String VALID_ANSWER_TWO = "Ctrl + Shift + F7";
    public static final String VALID_TITLE_TWO = "IntelliJ Question 2";
    public static final String VALID_TAG_INTELLIJ = "IntelliJ";
    public static final String VALID_TAG_SHORTCUTS = "Shortcuts";
    public static final String VALID_TAG_CHEATSHEET = "cheatsheet";
    public static final String VALID_TAG_FORMULA = "formula";
    public static final String VALID_TAG_IMPORTANT = "important";

    public static final String VALID_TITLE_MATH = "maths";
    public static final String VALID_TITLE_GEM = "gem module";
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "Title&"; // '&' not allowed in titles

    public static final String VALID_CONTENT_MATH = "math";
    public static final String VALID_CONTENT_SCIENCE = "science";
    public static final String VALID_CONTENT_CHINESE = "chinese";
    public static final String VALID_CONTENT_ENGLISH = "english";
    public static final String VALID_CONTENT_PE = "physical education";

    public static final String TITLE_DESC_CS1 = " " + PREFIX_TITLE + VALID_TITLE_MATH;
    public static final String TITLE_DESC_CS2 = " " + PREFIX_TITLE + VALID_TITLE_GEM;
    public static final String TAG_DESC_CHEATSHEET = " " + PREFIX_TAG + VALID_TAG_CHEATSHEET;
    public static final String TAG_DESC_IMPORTANT = " " + PREFIX_TAG + VALID_TAG_IMPORTANT;

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_MODULE = "cs2100";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_MODULE = " " + PREFIX_TAG + VALID_TAG_MODULE;

    public static final String VALID_TITLE_SAMPLE = "Sample title";
    public static final String VALID_TITLE_PIPELINE = "Pipelining Definition.";
    public static final String VALID_CONTENT_SAMPLE = "Sample Content";
    public static final String VALID_CONTENT_PIPELINE = "Pipelining is the process of making a single processor run "
            + "multiple instructions simultaneously.";
    public static final String VALID_TITLE_FRAGMENT = "Fragment Title?";
    public static final String VALID_CONTENT_FRAGMENT = "/* C/This TAG/test */is a /* C/Note Fragment TAG/test */. "
            + "It discusses all the possible /* C/probabilities TAG/test */??? of things that you might /* C/do. "
            + "TAG/test */";
    public static final String VALID_TAG_CS2100 = "CS2100";
    public static final String VALID_TAG_MIDTERMS = "Midterms";

    public static final String VALID_NOTE_TITLE_PIPELINE = SPACE + PREFIX_TITLE + VALID_TITLE_PIPELINE;
    public static final String VALID_NOTE_CONTENT_PIPELINE = SPACE + PREFIX_CONTENT + VALID_CONTENT_PIPELINE;
    public static final String VALID_NOTE_TITLE_FRAGMENT = SPACE + PREFIX_TITLE + VALID_TITLE_FRAGMENT;
    public static final String VALID_NOTE_CONTENT_FRAGMENT = SPACE + PREFIX_CONTENT + VALID_CONTENT_FRAGMENT;
    public static final String VALID_NOTE_TAG_1_PIPELINE = SPACE + PREFIX_TAG + VALID_TAG_CS2100;
    public static final String VALID_NOTE_TAG_2_PIPELINE = SPACE + PREFIX_TAG + VALID_TAG_MIDTERMS;

    public static final String INVALID_NOTE_TITLE = " \t\r\n";
    public static final String INVALID_NOTE_CONTENT = " \t\r\n";
    public static final String INVALID_NOTE_TAG = "Two words";

    public static final String INVALID_NOTE_TITLE_PIPELINE = SPACE + PREFIX_TITLE + INVALID_NOTE_TITLE;
    public static final String INVALID_NOTE_CONTENT_PIPELINE = SPACE + PREFIX_CONTENT + INVALID_NOTE_CONTENT;
    public static final String INVALID_NOTE_TAG_PIPELINE = SPACE + PREFIX_TAG + INVALID_NOTE_TAG;

    public static final String EXPECTED_VIEW_SAMPLE = "Viewing note: \n\tTitle: Sample Title\n\tContent: Sample "
            + "Content\n\tTags: [sampletag2][sampletag1]";
    public static final String EXPECTED_VIEW_FRAGMENT = "Viewing note: \n\tTitle: Fragment Title?\n\tContent: This"
            + "is a Note Fragment. It discusses all the possible probabilities??? of things that you might do."
            + "\n\tTags: [cs2100][midterms]";
    public static final String EXPECTED_RAW_VIEW_FRAGMENT = "Viewing raw note: \n\tTitle: Fragment Title?\n\tContent: "
            + "/* C/This TAG/test */is a /* C/Note Fragment TAG/test */. It discusses all the possible /* "
            + "C/probabilities TAG/test */??? of things that you might /* C/do. TAG/test */\n\tTags: "
            + "[cs2100][midterms]";
    public static final String EXPECTED_LIST_RESULT = "1. Sample Title - Sample Content\n2. Pipelining Definition - "
            + "Pipelining is the process of making a single processor run multiple instructions simultaneously.\n3. "
            + "Potatoes - I really like potatoes.";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCheatSheetCommand.EditCheatSheetDescriptor DESC_CS6;
    public static final EditCheatSheetCommand.EditCheatSheetDescriptor DESC_CS7;

    static {

        DESC_CS6 = new EditCheatSheetDescriptorBuilder()
                .withTitle(VALID_TITLE_MATH).withTags(VALID_TAG_CHEATSHEET).build();

        DESC_CS7 = new EditCheatSheetDescriptorBuilder()
                .withTitle(VALID_TITLE_GEM).withTags(VALID_TAG_CHEATSHEET, VALID_TAG_FORMULA).build();
    }

    private static Logger logger = Logger.getLogger(CommandTestUtil.class.getName());

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            logger.info("expectedCommandResult\n" + expectedCommandResult.getFeedbackToUser());
            logger.info("result\n" + result.getFeedbackToUser());
            logger.info("Are the CommandResults equal: " + expectedCommandResult.equals(result));
            logger.info("Are the CommandResult feedbacks equal: " + expectedCommandResult.getFeedbackToUser()
                    .equals(result.getFeedbackToUser()));
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result.getFeedbackToUser());
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        //StudyBuddyPro expectedStudyBuddyPro = new StudyBuddyPro(actualModel.getStudyBuddyPro());
        List<Flashcard> expectedFilteredFlashcardList = new ArrayList<>(actualModel.getFilteredFlashcardList());
        List<CheatSheet> expectedFilteredCheatSheetList = new ArrayList<>(actualModel.getFilteredCheatSheetList());
        List<Note> expectedFilteredNoteList = new ArrayList<>(actualModel.getFilteredNoteList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        //assertEquals(expectedStudyBuddyPro, actualModel.getStudyBuddyPro());
        assertEquals(expectedFilteredFlashcardList, actualModel.getFilteredFlashcardList());
        assertEquals(expectedFilteredCheatSheetList, actualModel.getFilteredCheatSheetList());
        assertEquals(expectedFilteredNoteList, actualModel.getFilteredNoteList());
        //assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the cheatsheet at the given {@code targetIndex} in the
     * {@code model}'s StudyBuddyPro.
     */
    public static void showCheatSheetAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCheatSheetList().size());

        CheatSheet cheatSheet = model.getFilteredCheatSheetList().get(targetIndex.getZeroBased());
        final String[] splitTitle = cheatSheet.getTitle().fullTitle.split("\\s+");
        model.updateFilteredCheatSheetList(new TitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assertEquals(1, model.getFilteredCheatSheetList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the note at the given {@code targetIndex} in the
     * {@code model}'s StudyBuddyPro.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitTitle = flashcard.getTitle().fullTitle.split("\\s+");
        model.updateFilteredFlashcardList(new FlashcardTitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the note at the given {@code targetIndex} in the
     * {@code model}'s StudyBuddyPro.
     */
    public static void showNoteAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredNoteList().size());

        Note note = model.getFilteredNoteList().get(targetIndex.getZeroBased());
        final String[] splitTitle = note.getTitle().fullTitle.split("\\s+");
        model.updateFilteredNoteList(new NoteTitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        logger.info("Number of Notes: " + model.getFilteredNoteList().size());

        assertEquals(1, model.getFilteredNoteList().size());
    }

}
