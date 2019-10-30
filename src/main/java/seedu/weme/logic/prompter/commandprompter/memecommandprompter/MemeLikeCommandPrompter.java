package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.logic.commands.memecommand.MemeLikeCommand.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.COMMAND_DELIMITER;
import static seedu.weme.logic.prompter.util.PrompterUtil.MAX_RESULTS_DISPLAY;
import static seedu.weme.logic.prompter.util.PrompterUtil.NO_LISTED_MEME;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt arguments for MemeLikeCommand.
 */
public class MemeLikeCommandPrompter implements Prompter {
    private static final String PREAMBLE = COMMAND_WORD + COMMAND_DELIMITER;

    /**
     * Suggests the indices of memes in the {@code FilteredMemeList} to be liked,
     * in descending order of the number of likes the meme has received.
     */
    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        String displayText;
        List<String> possibleArguments = model
                .getFilteredMemeList()
                .stream()
                .sorted(Comparator.comparingInt(meme -> -model.getLikesByMeme(meme)))
                .map(meme -> String.valueOf(model.getFilteredMemeList().indexOf(meme) + 1))
                .collect(Collectors.toList());

        String pattern = PREAMBLE + "+\\d+$";
        if (arguments.matches(pattern)) {
            displayText = "You can now use UP arrow key to spam likes!";
        } else {
            displayText = possibleArguments
                    .stream()
                    .limit(MAX_RESULTS_DISPLAY)
                    .reduce((x, y) -> x + '\n' + y)
                    .orElse(NO_LISTED_MEME);
        }

        return new CommandPrompt(displayText,
                PREAMBLE + possibleArguments
                        .stream()
                        .findFirst()
                        .orElse(NO_LISTED_MEME)
        );
    }
}
