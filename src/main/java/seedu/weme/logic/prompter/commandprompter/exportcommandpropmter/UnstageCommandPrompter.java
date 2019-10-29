package seedu.weme.logic.prompter.commandprompter.exportcommandpropmter;

import static seedu.weme.logic.commands.exportcommand.UnstageCommand.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_STAGED_MEME;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for UnstageCommand.
 */
public class UnstageCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;

    /**
     * Suggests the indices of memes in the {@code FilteredMemeList} to be unstaged,
     * in ascending order of the number of likes the meme has received.
     */
    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        List<String> possibleArguments = model
                .getFilteredStagedMemeList()
                .stream()
                .sorted(Comparator.comparingInt(meme -> model.getLikesByMeme(meme)))
                .map(meme -> String.valueOf(model.getFilteredStagedMemeList().indexOf(meme) + 1))
                .collect(Collectors.toList());

        return new CommandPrompt(
                possibleArguments
                        .stream()
                        .limit(MAX_RESULTS_DISPLAY)
                        .reduce((x, y) -> x + '\n' + y)
                        .orElse(NO_STAGED_MEME),
                PREAMBLE + possibleArguments
                        .stream()
                        .findFirst()
                        .orElse(NO_STAGED_MEME)
        );
    }
}
