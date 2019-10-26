package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_LISTED_MEME;

import java.util.Comparator;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt arguments for MemeStageCommand.
 */
public class MemeStageCommandPrompter implements Prompter {

    /**
     * Suggests the indices of memes in the {@code FilteredMemeList} to be staged,
     * in descending order of the number of likes the meme has received.
     */
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        return new CommandPrompt(model
                .getFilteredMemeList()
                .stream()
                .sorted(Comparator.comparingInt(meme -> -model.getLikesByMeme(meme)))
                .map(meme -> String.valueOf(model.getFilteredMemeList().indexOf(meme) + 1))
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse(NO_LISTED_MEME));
    }
}
