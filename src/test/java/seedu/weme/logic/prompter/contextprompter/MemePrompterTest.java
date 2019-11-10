package seedu.weme.logic.prompter.contextprompter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.logic.commands.generalcommand.ExitCommand;
import seedu.weme.logic.commands.generalcommand.HelpCommand;
import seedu.weme.logic.commands.generalcommand.RedoCommand;
import seedu.weme.logic.commands.generalcommand.TabCommand;
import seedu.weme.logic.commands.generalcommand.UndoCommand;
import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeArchivesCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeDislikeCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.commands.memecommand.MemeUnarchiveCommand;
import seedu.weme.logic.commands.memecommand.MemeViewCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class MemePrompterTest extends ApplicationTest {

    private static final String INVALID_INPUT = "this is an invalid command";
    private static final String VALID_PARTIAL_INPUT = "ed";

    private final WemePrompter prompter = new MemePrompter();
    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void promptCommand_emptyString() throws PromptException {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(
                MemeAddCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeArchiveCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeArchivesCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeClearCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeDeleteCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeDislikeCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeEditCommand.MESSAGE_DESCRIPTION + '\n'
                        + ExitCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeFindCommand.MESSAGE_DESCRIPTION + '\n'
                        + HelpCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeLikeCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeListCommand.MESSAGE_DESCRIPTION + '\n'
                        + RedoCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeStageCommand.MESSAGE_DESCRIPTION + '\n'
                        + TabCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeUnarchiveCommand.MESSAGE_DESCRIPTION + '\n'
                        + UndoCommand.MESSAGE_DESCRIPTION + '\n'
                        + MemeViewCommand.MESSAGE_DESCRIPTION ,
                MemeAddCommand.COMMAND_WORD + COMMAND_DELIMITER);
        assertEquals(expectedCommandPrompt, prompter.promptCommand(model, ""));
    }

    @Test
    public void promptCommand_partialInput() throws PromptException {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(
                MemeEditCommand.MESSAGE_DESCRIPTION + '\n'
                + MemeAddCommand.MESSAGE_DESCRIPTION + '\n'
                + RedoCommand.MESSAGE_DESCRIPTION,
                MemeEditCommand.COMMAND_WORD + COMMAND_DELIMITER);
        assertEquals(expectedCommandPrompt, prompter.promptCommand(model, VALID_PARTIAL_INPUT));
    }

    @Test
    public void promptCommand_archives() throws PromptException {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(MemeArchivesCommand.MESSAGE_USAGE,
                MemeArchivesCommand.COMMAND_WORD);
        assertEquals(expectedCommandPrompt, prompter.promptCommand(model, MemeArchivesCommand.COMMAND_WORD));
    }

    @Test
    public void promptCommand_clear() throws PromptException {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(MemeClearCommand.MESSAGE_USAGE,
                MemeClearCommand.COMMAND_WORD);
        assertEquals(expectedCommandPrompt, prompter.promptCommand(model, MemeClearCommand.COMMAND_WORD));
    }

    @Test
    public void promptCommand_list() throws PromptException {
        CommandPrompt expectedCommandPrompt = new CommandPrompt(MemeLikeCommand.MESSAGE_USAGE,
                MemeLikeCommand.COMMAND_WORD);
        assertEquals(expectedCommandPrompt, prompter.promptCommand(model, MemeLikeCommand.COMMAND_WORD));
    }

    @Test
    public void promptCommand_invalidInput_throwsPrompterException() {
        assertThrows(PromptException.class, () -> prompter.promptCommand(model, INVALID_INPUT));
    }
}
