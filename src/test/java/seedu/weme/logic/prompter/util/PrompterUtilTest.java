package seedu.weme.logic.prompter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalRecords.NAME_CAT;
import static seedu.weme.testutil.TypicalRecords.NAME_GIRL;
import static seedu.weme.testutil.TypicalRecords.NAME_SPONGE_BOB;
import static seedu.weme.testutil.TypicalRecords.PATH_CAT;
import static seedu.weme.testutil.TypicalRecords.PATH_GIRL;
import static seedu.weme.testutil.TypicalRecords.PATH_WEME;
import static seedu.weme.testutil.TypicalRecords.TAG_COMPUTING;
import static seedu.weme.testutil.TypicalRecords.TAG_CS2103;
import static seedu.weme.testutil.TypicalRecords.TAG_PROGRAMMING;
import static seedu.weme.testutil.TypicalRecords.TEXT_LOL;
import static seedu.weme.testutil.TypicalRecords.TEXT_NAY;
import static seedu.weme.testutil.TypicalRecords.TEXT_SU;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.prompter.contextprompter.MemePrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

/**
 * Contains integration test (interaction with Model) and unit tests for PrompterUtil.
 */
public class PrompterUtilTest extends ApplicationTest {
    private static final String VALID_START_STRING = "start string";
    private static final String VALID_NOT_START_STRING = "string";
    private static final String VALID_FULL_STRING = "start string from here";
    private static final String VALID_FULL_STRING_UPPER = "Start String FROM hERe";
    private static final String VALID_SIMILAR_TO_FULL_STRING = "sdart sting fron hare";
    private static final String VALID_STRING_STARTING_WITH_A = "a normal string";
    private static final String VALID_STRING_STARTING_WITH_B = "best normal string";
    private static final String BLANK_STRING = "     ";

    private static final Set<String> VALID_STRING_SET = Set.of(
            VALID_START_STRING,
            VALID_NOT_START_STRING,
            VALID_SIMILAR_TO_FULL_STRING,
            VALID_STRING_STARTING_WITH_A,
            VALID_STRING_STARTING_WITH_B
    );

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void forContext_success() {
        assertEquals(MemePrompter.class, PrompterUtil.forContext(CONTEXT_MEMES).getClass());
    }

    @Test
    public void startsWith_validInput_success() {
        // The second string starts with the first string.
        assertTrue(PrompterUtil.startsWith(VALID_START_STRING, VALID_FULL_STRING));

        // The second string contains the first string but does not starts with it.
        assertFalse(PrompterUtil.startsWith(VALID_NOT_START_STRING, VALID_FULL_STRING));

        // The first string is longer than the second string.
        assertFalse(PrompterUtil.startsWith(VALID_FULL_STRING, VALID_START_STRING));

        // The second string contains upper case letters, the first string does not.
        // The second string starts with the first string if case is ignored.
        assertFalse(PrompterUtil.startsWith(VALID_START_STRING, VALID_FULL_STRING_UPPER));
    }

    @Test
    public void findSimilarStrings_validInput_success() {
        String similarStringsToBlankArgument = VALID_STRING_STARTING_WITH_A + '\n'
                + VALID_STRING_STARTING_WITH_B + '\n' + VALID_SIMILAR_TO_FULL_STRING;

        String similarStringsToValidFullString = VALID_SIMILAR_TO_FULL_STRING + '\n'
                + VALID_START_STRING + '\n' + VALID_NOT_START_STRING;

        // Find similar strings based on blank argument.
        assertEquals(similarStringsToBlankArgument,
                PrompterUtil.findSimilarStrings(VALID_STRING_SET, BLANK_STRING));

        // Find similar strings based on valid full string.
        assertEquals(similarStringsToValidFullString,
                PrompterUtil.findSimilarStrings(VALID_STRING_SET, VALID_FULL_STRING));
    }

    @Test
    public void findMostSimilarString_validInput_success() {
        // Find similar strings based on blank argument.
        assertEquals(VALID_STRING_STARTING_WITH_A,
                PrompterUtil.findMostSimilarString(VALID_STRING_SET, BLANK_STRING));

        // Find similar strings based on valid full string.
        assertEquals(VALID_SIMILAR_TO_FULL_STRING,
                PrompterUtil.findMostSimilarString(VALID_STRING_SET, VALID_FULL_STRING));
    }

    @Test
    public void promptCommandWord_validInput_success() {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(MemeAddCommand.MESSAGE_DESCRIPTION + '\n'
                + MemeArchiveCommand.MESSAGE_DESCRIPTION + '\n' + MemeArchivesCommand.MESSAGE_DESCRIPTION,
                MemeAddCommand.COMMAND_WORD + PrompterUtil.COMMAND_DELIMITER);
        CommandPrompt actualCommandPrompt = PrompterUtil.promptCommandWord(PrompterUtil.MEME_COMMANDS, "a",
                PrompterUtil.MEME_COMMANDS_DESCRIPTION_MAP);
        assertEquals(expectedCommandPrompt, actualCommandPrompt);
    }

    @Test
    public void promptSimilarMemeArguments_validInput_success() throws PromptException {
        String lastArgumentValue = "c";
        String inputWithoutLastArgument = "add p/path/to/meme d/this is a meme t/";
        LastArgument lastArgument = new LastArgument(PREFIX_TAG, lastArgumentValue);
        CommandPrompt expectedCommandPrompt = new CommandPrompt(TAG_CS2103 + '\n' + TAG_COMPUTING
                + '\n' + TAG_PROGRAMMING, inputWithoutLastArgument + TAG_CS2103);
        CommandPrompt actualCommandPrompt = PrompterUtil.promptSimilarMemeArguments(model, inputWithoutLastArgument,
                lastArgument);
        assertEquals(expectedCommandPrompt, actualCommandPrompt);
    }

    @Test
    public void promptSimilarMemeArguments_invalidInput_throwsPrompterException() {
        String lastArgumentValue = "cat";
        String inputWithoutLastArgument = "add n/";
        LastArgument lastArgument = new LastArgument(PREFIX_NAME, lastArgumentValue);
        assertThrows(PromptException.class, () -> PrompterUtil.promptSimilarMemeArguments(model,
                inputWithoutLastArgument, lastArgument));
    }

    @Test
    public void promptSimilarTemplateArguments_validInput_success() throws PromptException {
        String lastArgumentValue = "cute girl";
        String inputWithoutLastArgument = "add p/path/to/template n/";
        LastArgument lastArgument = new LastArgument(PREFIX_NAME, lastArgumentValue);
        CommandPrompt expectedCommandPrompt = new CommandPrompt(NAME_CAT + '\n' + NAME_GIRL
                + '\n' + NAME_SPONGE_BOB, inputWithoutLastArgument + NAME_CAT);
        CommandPrompt actualCommandPrompt = PrompterUtil.promptSimilarTemplateArguments(model, inputWithoutLastArgument,
                lastArgument);
        assertEquals(expectedCommandPrompt, actualCommandPrompt);
    }

    @Test
    public void promptSimilarTemplateArguments_invalidInput_throwsPrompterException() {
        String lastArgumentValue = "new template";
        String inputWithoutLastArgument = "add d/";
        LastArgument lastArgument = new LastArgument(PREFIX_DESCRIPTION, lastArgumentValue);
        assertThrows(PromptException.class, () -> PrompterUtil.promptSimilarTemplateArguments(model,
                inputWithoutLastArgument, lastArgument));
    }

    @Test
    public void promptSimilarCreateArguments_validInput_success() throws PromptException {
        String lastArgumentValue = "lolll";
        String inputWithoutLastArgument = "edit 1 t/";
        LastArgument lastArgument = new LastArgument(PREFIX_TEXT, lastArgumentValue);
        CommandPrompt expectedCommandPrompt = new CommandPrompt(TEXT_LOL + '\n' + TEXT_NAY
                + '\n' + TEXT_SU, inputWithoutLastArgument + TEXT_LOL);
        CommandPrompt actualCommandPrompt = PrompterUtil.promptSimilarCreateArguments(model, inputWithoutLastArgument,
                lastArgument);
        assertEquals(expectedCommandPrompt, actualCommandPrompt);
    }

    @Test
    public void promptSimilarCreateArguments_invalidInput_throwsPrompterException() {
        String lastArgumentValue = "This is a text";
        String inputWithoutLastArgument = "edit 1 d/";
        LastArgument lastArgument = new LastArgument(PREFIX_DESCRIPTION, lastArgumentValue);
        assertThrows(PromptException.class, () -> PrompterUtil.promptSimilarCreateArguments(model,
                inputWithoutLastArgument, lastArgument));
    }


    @Test
    public void promptSimilarImportExportArguments_validInput_success() throws PromptException {
        String lastArgumentValue = "/home/me/Pictures/smilecat.png";
        String inputWithoutLastArgument = "load p/";
        LastArgument lastArgument = new LastArgument(PREFIX_FILEPATH, lastArgumentValue);
        CommandPrompt expectedCommandPrompt = new CommandPrompt(PATH_CAT + '\n' + PATH_WEME
                + '\n' + PATH_GIRL, inputWithoutLastArgument + PATH_CAT);
        CommandPrompt actualCommandPrompt = PrompterUtil.promptSimilarImportExportArguments(model,
                inputWithoutLastArgument, lastArgument);
        assertEquals(expectedCommandPrompt, actualCommandPrompt);
    }

    @Test
    public void promptSimilarImportExportArguments_invalidInput_throwsPrompterException() {
        String lastArgumentValue = "This is a meme";
        String inputWithoutLastArgument = "edit 1 d/";
        LastArgument lastArgument = new LastArgument(PREFIX_DESCRIPTION, lastArgumentValue);
        assertThrows(PromptException.class, () -> PrompterUtil.promptSimilarImportExportArguments(model,
                inputWithoutLastArgument, lastArgument));
    }
}
