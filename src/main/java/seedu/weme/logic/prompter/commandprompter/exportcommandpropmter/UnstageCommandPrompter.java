package seedu.weme.logic.prompter.commandprompter.exportcommandpropmter;

import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_STAGED_MEME;

import java.util.Comparator;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for UnstageCommand.
 */
public class UnstageCommandPrompter implements Prompter {

    /**
     * Suggests the indices of memes in the {@code FilteredMemeList} to be unstaged,
     * in ascending order of the number of likes the meme has received.
     */
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        return new CommandPrompt(model
                .getFilteredStagedMemeList()
                .stream()
                .sorted(Comparator.comparingInt(meme -> model.getLikesByMeme(meme)))
                .map(meme -> String.valueOf(model.getFilteredStagedMemeList().indexOf(meme) + 1))
                .limit(MAX_RESULTS_DISPLAY)
                .reduce((x, y) -> x + '\n' + y)
                .orElse(NO_STAGED_MEME));
    }
}
